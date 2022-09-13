package com.example.socify.ViewHolders;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

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
        Answer = itemView.findViewById(R.id.answertv);
        timestamp = itemView.findViewById(R.id.timestamp);

        namereplier.setText("Answered by: "+name);
        Answer.setText(answer);
        timestamp.setText(time);

    }

    public void approvalchecker(String postKey) {

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Questions").child("Approvals");
        approvaltv = itemView.findViewById(R.id.approveTV);
        approvalcount = itemView.findViewById(R.id.ApprovecountTV);

        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postKey).hasChild(currentUID)) {
                    approvaltv.setText("Approved");
                    votecount = (int) snapshot.child(postKey).getChildrenCount();
                    approvalcount.setText(Integer.toString(votecount) + " - Approvals");
                }
                else{
                    approvaltv.setText("Approve");
                    votecount = (int) snapshot.child(postKey).getChildrenCount();
                    approvalcount.setText(Integer.toString(votecount) + " - Approvals");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
