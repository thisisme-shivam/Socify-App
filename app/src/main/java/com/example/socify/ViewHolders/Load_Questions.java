package com.example.socify.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

//Creating a viewholder to load the questions in the recyclerView

public class Load_Questions extends RecyclerView.ViewHolder {

    public MaterialTextView timeres,nameres,questionsres;
    public Chip tagres;
    public MaterialButton delbtn, replybtn, userReplyBtn;

    public Load_Questions(@NonNull View itemView) {
        super(itemView);
    }

    public void setallitem(FragmentActivity activity, String name, String url, String userid, String key, String question, String time, String tag) {
        timeres = itemView.findViewById(R.id.timestamp);
        nameres = itemView.findViewById(R.id.usernametv);
        questionsres = itemView.findViewById(R.id.questiontv);
        tagres = itemView.findViewById(R.id.questiontag);
        tagres.setText(tag);
        timeres.setText(time);
        nameres.setText("Posted by: "+name);
        questionsres.setText(question);
        replybtn = itemView.findViewById(R.id.repliesbtn);
    }

    public void deleteitem(FragmentActivity activity, String name, String url, String userid, String key, String question, String time, String tag) {
        timeres = itemView.findViewById(R.id.timestamp);
        nameres = itemView.findViewById(R.id.usernametv);
        questionsres = itemView.findViewById(R.id.questiontv);
        tagres = itemView.findViewById(R.id.questiontag);
        tagres.setText(tag);
        timeres.setText(time);
        nameres.setText("Posted by: "+name);
        questionsres.setText(question);
        delbtn = itemView.findViewById(R.id.deletebtn);
        userReplyBtn = itemView.findViewById(R.id.repliesbtn);
    }

}
