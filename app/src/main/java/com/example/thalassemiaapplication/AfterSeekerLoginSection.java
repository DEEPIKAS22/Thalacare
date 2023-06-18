package com.example.thalassemiaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class AfterSeekerLoginSection extends AppCompatActivity {

    TextView Sheadname,Ssmallname,SRname, SRuname, SRemail, SRphone;
    String SREmail, SRPass;
    CardView STest, Ssche;
    Button logout ;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference dbr;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener Authlistner;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_seeker_login_section);

        logout = findViewById(R.id.logOutSeeker) ;
        Sheadname = findViewById(R.id.Sname);
        Ssmallname = findViewById(R.id.SUname);
        SRname = findViewById(R.id.SnameRetrive);
        SRuname = findViewById(R.id.SUnameRetrive);
        SRemail = findViewById(R.id.SemailRetrive);
        SRphone = findViewById(R.id.SPhoneRetrive);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        dbr = db.getReference("SEEKER");

        UserID = user.getUid();


        Intent intent = getIntent();
        SREmail = intent.getStringExtra("EmailId");

        STest = findViewById(R.id.Test);
        STest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SeekerTest.class));
            }
        });

        Ssche = findViewById(R.id.Sschedule);
        Ssche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SchedulePageSeeker.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),SeekerLogin.class));
            }
        });

        //listener for database reference

        dbr.orderByChild("EmailId").equalTo(SREmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Sheadname.setText(ds.child("FullName").getValue().toString());
                    Ssmallname.setText(ds.child("UserName").getValue().toString());
                    SRname.setText(ds.child("FullName").getValue().toString());
                    SRuname.setText(ds.child("UserName").getValue().toString());
                    SRemail.setText(ds.child("EmailId").getValue().toString());
                    SRphone.setText(ds.child("PhoneNo").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }
}