package com.example.thalassemiaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OrganizationAct extends AppCompatActivity {

    Button Abutton;
    EditText Aname,Apass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        Aname = findViewById(R.id.editTextOrg);
        Apass = findViewById(R.id.passwordOrg);
        Abutton = findViewById(R.id.admindone);
        Abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateemail()!=true | validatepass()!= true)
                {
                    return;
                }
                startActivity(new Intent(getApplicationContext(),DBView.class));
            }
        });
    }

    public boolean validateemail()
    {

        String adminemail = Aname.getText().toString();

        if( (Patterns.EMAIL_ADDRESS.matcher(adminemail).matches()) && (adminemail.matches("deepikasahu@kccemsr.edu.in") || adminemail.matches("tejasvimayekar@kccemsr.edu.in") || adminemail.matches("vaishnavinalawade@kccemsr.edu.in") || adminemail.matches("pranavtanna@kccemsr.edu.in")) )
        {
            Aname.setError(null);
            return true;
        }
        else
        {
            Aname.setError("Invalid email!");
            return false;
        }
    }

    String pass = "kccemsrproject";
    public boolean validatepass()
    {
        String adminpass = Apass.getText().toString();
        if(adminpass.isEmpty() || !adminpass.matches(pass) )
        {
            Apass.setError("Invalid password!");
            return false;
        }
        else
        {
            Apass.setError(null);
            return true;
        }
    }


}