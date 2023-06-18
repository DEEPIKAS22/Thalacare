package com.example.thalassemiaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QueryFeedback extends AppCompatActivity {

    public Button submitFeedback;
    EditText queryEmail, query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_feedback);

        queryEmail = findViewById(R.id.editTextQueryMail);
        query = findViewById(R.id.editTextQuery);

        submitFeedback = findViewById(R.id.submitQuery) ;

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND) ;
                i.setType("message/html") ;
                String[] receiver = {"blood.donation.application22@gmail.com"} ;    //only contain string present inside array.
                i.putExtra(Intent.EXTRA_EMAIL, receiver) ;
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback from app: ") ;
                i.putExtra(Intent.EXTRA_TEXT, "Email: "+queryEmail.getText()+"\n\n Message: "+query.getText()) ;

                try {
                    startActivity(Intent.createChooser(i, "please select email"));
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(QueryFeedback.this, "There is no such email ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}