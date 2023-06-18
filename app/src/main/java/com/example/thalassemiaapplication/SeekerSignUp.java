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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SeekerSignUp extends AppCompatActivity {

    private EditText Sname, Suser, Semail,Sphone,Spass;
    RadioButton Smale,Sfemale;
    String Sgender,Sitem;
    Button Sgo;
    String Rn,Ru,Re,Rp,Rpa;
    FirebaseAuth fAuthentic;
    FirebaseUser user;
    String uid;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("SEEKER");
    FirebaseAuth fAuthS = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_sign_up);

        Spinner spinn = (Spinner)findViewById(R.id.spinner1);
//ArrayAdapter holds the value and put them in spinner item list
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SeekerSignUp.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.groups));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinn.setAdapter(adapter1);

        user = FirebaseAuth.getInstance().getCurrentUser();
//        uid = user.getUid();
        Sname = findViewById(R.id.editTextSignupSeek);
        Suser = findViewById(R.id.editTextSignupSeekId);
        Semail = findViewById(R.id.editTextSignupSeekMailId);
        Sphone = findViewById(R.id.editTextSignupSeekPhoneNo);
        Spass= findViewById(R.id.editTextSignupSeekPassword);
        //RadioButton
        Smale = findViewById(R.id.SeekerMale);
        Sfemale = findViewById(R.id.SeekerFemale);
        Sgo = findViewById(R.id.SeekerGo);

        Sgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validatemail()!=true | validateName()!=true | validatepass()!=true | validatephone()!=true | validateUser()!=true)
                {
                    return;
                }

                String Rfullname = Sname.getText().toString();
                String Rusername = Suser.getText().toString();
                String Reqemail = Semail.getText().toString();
                String Reqphone = Sphone.getText().toString();
                String Rpassword = Spass.getText().toString();
                if(Smale.isChecked())
                {
                    Sgender = Smale.getText().toString();
                }
                else if(Sfemale.isChecked())
                {
                    Sgender = Sfemale.getText().toString();
                }

                Sitem = spinn.getSelectedItem().toString();

                //if user already exist
                fAuthS.fetchSignInMethodsForEmail(Reqemail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                        if(isNewUser)
                        {
                            Log.e("TAG"," ");

                            HashMap<String,String> userMap = new HashMap<>();
                            userMap.put("FullName",Rfullname);
                            userMap.put("UserName",Rusername);
                            userMap.put("EmailId",Reqemail);
                            userMap.put("PhoneNo",Reqphone);
                            userMap.put("Password",Rpassword);
                            userMap.put("Gender",Sgender);
                            userMap.put("Blood group",Sitem);

                            //root.push().setValue(userMap);


                            //Authenticate user
//                            fAuthS.createUserWithEmailAndPassword(Reqemail, Rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(SeekerSignUp.this, "Registered!", Toast.LENGTH_SHORT).show();
//                                        fAuthS.signInWithEmailAndPassword(Reqemail,Rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                if(task.isSuccessful())
//                                                {
//                                                    user = FirebaseAuth.getInstance().getCurrentUser();
//                                                    uid = user.getUid();
//                                                    root.child(uid).setValue(userMap);
//                                                }
//                                            }
//                                        });
//                                    }
//                                }
//                            });

                            fAuthS.createUserWithEmailAndPassword(Reqemail, Rpassword).
                                    addOnCompleteListener(SeekerSignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SeekerSignUp.this, "Registered!", Toast.LENGTH_LONG).show();
                                            fAuthS.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        String userId = fAuthS.getCurrentUser().getUid();
                                                        root.child(userId).setValue(userMap);
                                                        Sname.setText("");
                                                        Suser.setText("");
                                                        Semail.setText("");
                                                        Spass.setText("");
                                                        Sphone.setText("");
                                                    }else{
                                                        Toast.makeText(SeekerSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    }
                                }
                            });

//                            Toast.makeText(SeekerSignUp.this, "Data registered successfully!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Log.e("TAG","User is already register please login!");
                            Toast.makeText(SeekerSignUp.this,"User is already registered please login again!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AfterSeekerLoginSection.class));
                        }

                    }
                });
            }
        });


    }

    public boolean validateName()
    {
        String Rn = Sname.getText().toString();
        if (Rn.isEmpty()) {
            Sname.setError("Invalid name");
            return false;
        }
        else if (Rn.length() > 45) {
            Sname.setError("name is too large");
            return false;
        }
        else
        {
            Sname.setError(null);
            return true;
        }
    }

    public boolean validateUser()
    {
        String space = "\\A\\w{1,20}\\z";
        String Ru = Suser.getText().toString();
        if (Ru.isEmpty() || Ru.length() < 8) {
            Suser.setError("invalid username");
            return false;
        }
        else if(Ru.length()>20)
        {
            Suser.setError("username is too large");
            return false;
        }
        else if(!Ru.matches(space))
        {
            Suser.setError("no white space are allowed");
            return false;
        }
        else
        {
            Suser.setError(null);
            return true;
        }
    }

    public boolean validatemail()
    {
        String Re = Semail.getText().toString();
        if (Re.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Re).matches()) {
            Semail.setError("Invalid Email Id");
            return false;
        }
        else
        {
            Semail.setError(null);
            return true;
        }
    }

    public boolean validatephone()
    {
        String nopattern = "[0-9]{10}";
        String Rp = Sphone.getText().toString();
        if (Rp.isEmpty() || !Rp.matches(nopattern)) {
            Sphone.setError("invalid number");
            return false;
        }
        else
        {
            Sphone.setError(null);
            return true;
        }
    }

    public boolean validatepass()
    {
        String Rpa = Spass.getText().toString();
        if (Rpa.isEmpty()) {
            Spass.setError("Enter a valid password");
            return false;
        }
        else if (Rpa.length() < 8) {
            Spass.setError("password is too short");
            return false;
        }
        else
        {
            Spass.setError(null);
            return true;
        }
    }

}