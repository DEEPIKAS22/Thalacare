package com.example.thalassemiaapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase ;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class myAdapter extends FirebaseRecyclerAdapter<model, myAdapter.myviewholder> {
    public myAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull model model)
    {
        holder.name.setText(model.getFullName());
        holder.mail.setText(model.getEmailId());
        holder.phoneNo.setText(model.getPhoneNo());
        holder.blood.setText(model.getBloodGroup());
    }

    //will display an empty card
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return  new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView name, mail, phoneNo, blood;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.FullName);
            mail = itemView.findViewById(R.id.EmailId);
            phoneNo = itemView.findViewById(R.id.PhoneNo);
            blood= itemView.findViewById(R.id.BloodGroup);

        }
    }

}
