package com.example.thalassemiaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SeekerLogin extends AppCompatActivity {

    public Button SignUpS, Slogin;
    EditText SLemail, SLpass ;
    ProgressBar progress;
    FirebaseAuth fAuth;
    TextView Sfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_login);

        SLemail = findViewById(R.id.editTextDonor);
        SLpass = findViewById(R.id.editTextPassword);
        Slogin = findViewById(R.id.Slog);
        fAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.progressBarSeekerL);
        SignUpS = (Button) findViewById(R.id.SignUpSeekerButton);
        Sfor = findViewById(R.id.Sfor);

        Slogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateemail()!=true | validatepass()!= true)
                {
                    return;
                }
                String donorlemail = SLemail.getText().toString();
                String donorlpass = SLpass.getText().toString();
                progress.setVisibility(View.VISIBLE);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(donorlemail,donorlpass).addOnCompleteListener(new  OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(fAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(SeekerLogin.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SeekerLogin.this, AfterSeekerLoginSection.class);
                                intent.putExtra("EmailId", donorlemail);
                                startActivity(intent);
                                progress.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(SeekerLogin.this,"You are not a verified user, please verify email!",Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            }
                        }
                        else
                        {
                            Toast.makeText(SeekerLogin.this,"email or password is incorrect!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



        Sfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SeekerLogin.this,ForgetPassword.class));
            }
        });

        SignUpS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), SeekerSignUp.class);
                startActivity(i);
            }
        });

    }

    public boolean validateemail()
    {
        String donorlemail = SLemail.getText().toString();

        if(donorlemail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(donorlemail).matches())
        {
            SLemail.setError("Invalid email!");
            return false;
        }
        else
        {
            SLemail.setError(null);
            return true;
        }
    }

    public boolean validatepass()
    {
        String donorlpass = SLpass.getText().toString();
        if(donorlpass.isEmpty())
        {
            SLpass.setError("Invalid password!");
            return false;
        }
        else
        {
            SLpass.setError(null);
            return true;
        }
    }


}