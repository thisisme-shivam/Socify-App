package com.example.socify.ViewHolders;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.R;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Load_Answers extends RecyclerView.ViewHolder {

    public MaterialTextView namereplier, Answer, timestamp, approvaltv, approvalcount;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    int votecount;

    public Load_Answers(@NonNull View itemView) {
        super(itemView);
    }

    public void setAnswer(Application application, String name, String uid, String answer, String time) {
        namereplier = itemView.findViewById(R.id.usernametv);
        Answer = itemView.findViewById(R.id.commenttv);
        timestamp = itemView.findViewById(R.id.timestamp);

        namereplier.setText("Answered by: "+name);
        Answer.setText(answer);
        timestamp.setText(time);

    }



}
