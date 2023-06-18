package com.example.thalassemiaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DonorLogin extends AppCompatActivity {

    public Button SignUpD, Dlogin;
    EditText DLemail, DLpass ;
    ProgressBar progress;
    FirebaseAuth fAuth;
    TextView Dfor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_donor_login);

        DLemail = findViewById(R.id.editTextDonor);
        DLpass = findViewById(R.id.editTextPassword);
        Dlogin = findViewById(R.id.Dlog);
        fAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.progressBarDonorL);
        SignUpD = (Button) findViewById(R.id.SignUpDonorButton);
        Dfor = findViewById(R.id.Dfor);

        Dlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateemail()!=true | validatepass()!= true)
                {
                    return;
                }
                String donorlemail = DLemail.getText().toString();
                String donorlpass = DLpass.getText().toString();
                progress.setVisibility(View.VISIBLE);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(donorlemail,donorlpass).addOnCompleteListener(new  OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DonorLogin.this,"logged in successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DonorLogin.this,AfterDonorLoginSection.class);
                            intent.putExtra("EmailId",donorlemail);
                            startActivity(intent);
                            progress.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(DonorLogin.this,"email or password is incorrect!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        Dfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonorLogin.this,ForgetPassword.class));
            }
        });

        SignUpD.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), donor_signup.class);
                startActivity(i);
            }
        });
    }

    public boolean validateemail()
    {
        String donorlemail = DLemail.getText().toString();

        if(donorlemail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(donorlemail).matches())
        {
            DLemail.setError("Invalid email!");
            return false;
        }
        else
        {
            DLemail.setError(null);
            return true;
        }
    }

    public boolean validatepass()
    {
        String donorlpass = DLpass.getText().toString();
        if(donorlpass.isEmpty())
        {
            DLpass.setError("Invalid password!");
            return false;
        }
        else
        {
            DLpass.setError(null);
            return true;
        }
    }

}