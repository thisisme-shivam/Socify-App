package com.example.socify.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

//Creating a viewholder to load the questions in the recyclerView

public class Load_Questions extends RecyclerView.ViewHolder {

    MaterialTextView timeres,nameres,questionsres;
    Chip tagres;

    public Load_Questions(@NonNull View itemView) {
        super(itemView);
    }

    public void setitem(FragmentActivity activity, String name, String url, String userid, String key, String question, String time, String tag) {
        timeres = itemView.findViewById(R.id.timestamp);
        nameres = itemView.findViewById(R.id.usernametv);
        questionsres = itemView.findViewById(R.id.questiontv);
        tagres = itemView.findViewById(R.id.questiontag);

        tagres.setText(tag);
        timeres.setText(time);
        nameres.setText("Posted by: "+name);
        questionsres.setText(question);
    }

}
