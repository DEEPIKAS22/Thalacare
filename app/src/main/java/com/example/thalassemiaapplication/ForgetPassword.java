package com.example.thalassemiaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    Button forB;
    EditText foremail;
    String femail;
    FirebaseAuth ffAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        ffAuth = FirebaseAuth.getInstance();
        forB = findViewById(R.id.fbutton);
        foremail = findViewById(R.id.editTextFormail);

        forB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validmail()!= true)
                {
                    return;
                }

                ffAuth.sendPasswordResetEmail(femail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPassword.this, "Check your email!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPassword.this,Dashboard.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ForgetPassword.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private boolean validmail()
    {
        femail = foremail.getText().toString();
        if(femail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(femail).matches())
        {
            foremail.setError("Invalid mail");
            return false;
        }
        else
        {
            foremail.setError(null);
            return true;
        }

    }
}