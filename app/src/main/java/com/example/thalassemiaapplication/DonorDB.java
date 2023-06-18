package com.example.thalassemiaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonorDB extends AppCompatActivity {

    RecyclerView review;
    myAdapter adapter;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_db);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("DONOR");
        review = findViewById(R.id.recview);
        review.setHasFixedSize(true);
        review.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model>options = new FirebaseRecyclerOptions.Builder<model>().setQuery(FirebaseDatabase.getInstance().getReference().child("DONOR"), model.class).build();
        adapter = new myAdapter(options);
        review.setAdapter(adapter);
    }

    @Override
    protected  void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected  void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}