package com.example.thalassemiaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Diet extends AppCompatActivity implements View.OnClickListener {

    public CardView dietPlan1 , dietPlan2, dietPlan3, dietPlan4, dietPlan5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        dietPlan1 = (CardView)findViewById(R.id.diet1);
        dietPlan2 = (CardView)findViewById(R.id.diet2);
        dietPlan3 = (CardView)findViewById(R.id.diet3);
        dietPlan4 = (CardView)findViewById(R.id.diet4);
        dietPlan5 = (CardView)findViewById(R.id.diet5);


        //setting OnClickListner for every card

        dietPlan1.setOnClickListener(this);
        dietPlan2.setOnClickListener(this);
        dietPlan3.setOnClickListener(this);
        dietPlan4.setOnClickListener(this);
        dietPlan5.setOnClickListener(this);
    }

// @overrride
    public void onClick(View v)
    {
        Intent i;
        switch (v.getId())
        {
            case R.id.diet1:
                i = new Intent(this,Diet1.class);
                startActivity(i);
                break;

            case R.id.diet2:
                i = new Intent(this,Diet2.class);
                startActivity(i);
                break;

            case R.id.diet3:
                i = new Intent(this,Diet3.class);
                startActivity(i);
                break;

            case R.id.diet4:
                i = new Intent(this,Diet4.class);
                startActivity(i);
                break;

            case R.id.diet5:
                i = new Intent(this,Diet5.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}