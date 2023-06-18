package com.example.thalassemiaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    public CardView seeker,donor,organization,diet, about, query;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        seeker = (CardView) findViewById(R.id.card1);
        donor = (CardView) findViewById(R.id.card3);
        organization = (CardView) findViewById(R.id.card2);
        about = (CardView) findViewById(R.id.card4);
        diet = (CardView) findViewById(R.id.card5);
        query = (CardView) findViewById(R.id.card6);

        seeker.setOnClickListener(this);
        donor.setOnClickListener(this);
        organization.setOnClickListener(this);
        about.setOnClickListener(this);
        diet.setOnClickListener(this);
        query.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {
        Intent i;
        switch (v.getId())
        {
            case R.id.card1:
                i = new Intent(this,SeekerLogin.class);
                startActivity(i);
                break;

            case R.id.card2:
                i = new Intent(this,OrganizationAct.class);
                startActivity(i);
                break;

            case R.id.card3:
                i = new Intent(this,DonorLogin.class);
                startActivity(i);
                break;

            case R.id.card4:
                i = new Intent(this,Contact.class);
                startActivity(i);
                break;

            case R.id.card5:
                i = new Intent(this,Diet.class);
                startActivity(i);
                break;

            case R.id.card6:
                i = new Intent(this,QueryFeedback.class);
                startActivity(i);
                break;
        }
    }
}