package com.example.thalassemiaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AfterDonorLoginSection extends AppCompatActivity {

    TextView Dheadname,Dsmallname,DRname, DRuname, DRemail, DRphone;
    String DREmail, DRPass;
    CardView DTest, Dschedule;
    Button home ;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dbr;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener Authlistner;
    String UserID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_donor_login_section);

        home = findViewById(R.id.homeButton) ;
        Dheadname = findViewById(R.id.Dname);
        Dsmallname = findViewById(R.id.DUname);
        DRname = findViewById(R.id.DnameRetrive);
        DRuname = findViewById(R.id.DUnameRetrive);
        DRemail = findViewById(R.id.DemailRetrive);
        DRphone = findViewById(R.id.DPhoneRetrive);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        dbr = db.getReference("DONOR");

        UserID = user.getUid();


        Intent intent = getIntent();
        DREmail = intent.getStringExtra("EmailId");

        DTest = findViewById(R.id.Test);
        DTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DonorTest.class));
            }
        });

        Dschedule = findViewById(R.id.scheduleCard);
        Dschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SchedulePage.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });

        //listener for database reference

        dbr.orderByChild("EmailId").equalTo(DREmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Dheadname.setText(ds.child("FullName").getValue().toString());
                    Dsmallname.setText(ds.child("UserName").getValue().toString());
                    DRname.setText(ds.child("FullName").getValue().toString());
                    DRuname.setText(ds.child("UserName").getValue().toString());
                    DRemail.setText(ds.child("EmailId").getValue().toString());
                    DRphone.setText(ds.child("PhoneNo").getValue().toString());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}