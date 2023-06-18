package com.example.thalassemiaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DBView extends AppCompatActivity implements View.OnClickListener {

    public CardView donorDB, seekerDB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbview);

        seekerDB = (CardView) findViewById(R.id.card1);
        donorDB = (CardView) findViewById(R.id.card2);
        //mapOrg = findViewById(R.id.card3) ;

        seekerDB.setOnClickListener(this);
        donorDB.setOnClickListener(this);

        //mapOrg.setOnClickListener(this);

    }


    public void onClick(View v)
    {
        Intent i;
        switch (v.getId())
        {
            case R.id.card1:
                i = new Intent(this,DonorDB.class);
                startActivity(i);
                break;

            case R.id.card2:
                i = new Intent(this,SeekerDB.class);
                startActivity(i);
                break;

//            case R.id.card3:
//                i = new Intent(this, MapButton.class);
//                startActivity(i);
//                break;

        }
    }
}