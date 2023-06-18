package com.example.thalassemiaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class donor_signup extends AppCompatActivity {

    //variables
    private EditText Dname, Duser, Demail,Dphone,Dpass;
    RadioButton male,female;
    String gender,item;
    Button Dgo;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("DONOR");
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_signup);


        //hooks to all the xml elements in activity_sign_up_donor.xml
        Dname = findViewById(R.id.editTextSignupDonorName);
        Duser = findViewById(R.id.editTextSignupDonorId);
        Demail = findViewById(R.id.editTextSignupDonorMailId);
        Dphone = findViewById(R.id.editTextSignupDonorPhoneNo);
        Dpass= findViewById(R.id.editTextSignupDonorPassword);
        //RadioButton
        male = findViewById(R.id.donorMale);
        female = findViewById(R.id.donorFemale);
        Dgo = findViewById(R.id.donorGo);

        //Spinner
        Spinner spinnD = (Spinner)findViewById(R.id.spinnerD);
        //ArrayAdapter holds the value and put them in spinner item list
        ArrayAdapter<String> adapterD = new ArrayAdapter<String>(donor_signup.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.groups));
        adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnD.setAdapter(adapterD);

        Dgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validatemail() != true | validateName() != true | validatepass() != true | validatephone() != true | validateUser() != true) {
                    return;
                }

                String fullname = Dname.getText().toString();
                String username = Duser.getText().toString();
                String email = Demail.getText().toString();
                String phone = Dphone.getText().toString();
                String password = Dpass.getText().toString();

                if (male.isChecked())
                {
                    gender = male.getText().toString();
                }
                else if (female.isChecked())
                {
                    gender = female.getText().toString();
                }
                item = spinnD.getSelectedItem().toString();

                //if user already exist
                fAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                        if(isNewUser)
                        {
                            Log.e("TAG"," ");

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("FullName", fullname);
                            userMap.put("UserName", username);
                            userMap.put("EmailId", email);
                            userMap.put("PhoneNo", phone);
                            userMap.put("Password", password);
                            userMap.put("Gender", gender);
                            userMap.put("Blood group", item);

                            //root.push().setValue(userMap);

                            //Authenticate user

//                            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(donor_signup.this, "Registered!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                            Toast.makeText(donor_signup.this, "Data registered successfully!", Toast.LENGTH_LONG).show();

                            fAuth.createUserWithEmailAndPassword(email, password).
                                    addOnCompleteListener(donor_signup.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(donor_signup.this, "Registered!", Toast.LENGTH_LONG).show();
                                                fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            String userId = fAuth.getCurrentUser().getUid();

                                                            root.child(userId).setValue(userMap);
                                                            Dname.setText("");
                                                            Duser.setText("");
                                                            Demail.setText("");
                                                            Dpass.setText("");
                                                            Dphone.setText("");
                                                        }else{
                                                            Toast.makeText(donor_signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Log.e("TAG","User is already register please login!");
                            Toast.makeText(donor_signup.this,"User is already registered please login!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),DonorLogin.class));
                        }

                    }
                });


            }
        });
    }

    public boolean validateName()
    {
        String Rn = Dname.getText().toString();
        if (Rn.isEmpty()) {
            Dname.setError("Invalid name");
            return false;
        }
        else if (Rn.length() > 45) {
            Dname.setError("name is too large");
            return false;
        }
        else
        {
            Dname.setError(null);
            return true;
        }
    }

    public boolean validateUser()
    {
        String space = "\\A\\w{1,20}\\z";
        String Ru = Duser.getText().toString();
        if (Ru.isEmpty() || Ru.length() < 8) {
            Duser.setError("invalid username");
            return false;
        }
        else if(Ru.length()>20)
        {
            Duser.setError("username is too large");
            return false;
        }
        else if(!Ru.matches(space))
        {
            Duser.setError("no white space are allowed");
            return false;
        }
        else
        {
            Duser.setError(null);
            return true;
        }
    }

    public boolean validatemail()
    {
        String Re = Demail.getText().toString();
        if (Re.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Re).matches()) {
            Demail.setError("Invalid Email Id");
            return false;
        }
        else
        {
            Demail.setError(null);
            return true;
        }
    }

    public boolean validatephone()
    {
        String nopattern = "[0-9]{10}";
        String Rp = Dphone.getText().toString();
        if (Rp.isEmpty() || !Rp.matches(nopattern)) {
            Dphone.setError("invalid number");
            return false;
        }
        else
        {
            Dphone.setError(null);
            return true;
        }
    }

    public boolean validatepass()
    {
        String Rpa = Dpass.getText().toString();
        if (Rpa.isEmpty()) {
            Dpass.setError("Enter a valid password");
            return false;
        }
        else if (Rpa.length() < 8) {
            Dpass.setError("password is too short");
            return false;
        }
        else
        {
            Dpass.setError(null);
            return true;
        }
    }
}