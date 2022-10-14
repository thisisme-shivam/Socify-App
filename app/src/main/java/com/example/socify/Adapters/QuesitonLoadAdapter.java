package com.example.socify.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.socify.Activities.Home;
import com.example.socify.Classes.QuestionsMember;
import com.example.socify.QueryFragments.ReplyFragment;
import com.example.socify.R;
import com.example.socify.databinding.ListAllQueryLayoutBinding;
import com.example.socify.databinding.ListUserQueryLayoutBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuesitonLoadAdapter extends RecyclerView.Adapter {

    AppCompatActivity activity;
    ArrayList<QuestionsMember> questionlist;
    public Boolean userFragent = false;
    public QuesitonLoadAdapter(Context context , ArrayList<QuestionsMember> questionlist){
        activity = (AppCompatActivity) context;
        this.questionlist = questionlist;
    }

    @Override
    public int getItemViewType(int position) {
        if(userFragent){
            return 0;
        }

        return 1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 1) {
           view = LayoutInflater.from(activity).inflate(R.layout.list_all_query_layout, parent, false);
           return  new QuestionViewHolder(view);
        }
        view   = LayoutInflater.from(activity).inflate(R.layout.list_user_query_layout, parent, false);
        return new  UserQuestionViewHolder(view);
    }





    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        QuestionsMember member = questionlist.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uid",member.getUserid());
        bundle.putString("question",member.getQuestion());
        bundle.putString("postkey",member.getKey());
        bundle.putString("username",member.getUsername());
        bundle.putString("tag",member.getTag());
        bundle.putString("time",member.getTime());
        ReplyFragment replyFragment = new ReplyFragment();
        replyFragment.setArguments(bundle);


        if(userFragent){
            UserQuestionViewHolder userViewHolder = (UserQuestionViewHolder) holder;

            Log.i("ues","fjdsklfj");
            userViewHolder.binding.questiontag.setText(member.getTag());
            userViewHolder.binding.questiontv.setText(member.getQuestion());
            userViewHolder.binding.timestamp.setText(member.getTime());
            userViewHolder.binding.usernametv.setText(member.getUsername());
            userViewHolder.binding.repliesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.queryFragmentLoader,replyFragment).commit();
                }
            });

            userViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ys","yes");
                    FirebaseDatabase.getInstance()
                            .getReference("College")
                            .child(Home.getUserData.college_name)
                            .child("Questions")
                            .child(member.getTag())
                            .child(Home.getUserData.uid)
                            .child(member.getKey())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().removeValue();
                                        notifyDataSetChanged();
                                        Log.i("Question Deleted", "YES");
                                        Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            });
        }else{
            QuestionViewHolder userViewHolder = (QuestionViewHolder) holder;
            Log.i("ues","fjdsklfj");

            userViewHolder.binding.questiontag.setText(member.getTag());
            userViewHolder.binding.questiontv.setText(member.getQuestion());
            userViewHolder.binding.timestamp.setText(member.getTime());
            userViewHolder.binding.usernametv.setText(member.getUsername());
            userViewHolder.binding.repliesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.queryFragmentLoader,replyFragment).commit();

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return questionlist.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        ListAllQueryLayoutBinding binding;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListAllQueryLayoutBinding.bind(itemView);
        }
    }
    public static class UserQuestionViewHolder extends RecyclerView.ViewHolder {

        ListUserQueryLayoutBinding binding;
        MaterialButton button;

        public UserQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListUserQueryLayoutBinding.bind(itemView);
            button = itemView.findViewById(R.id.deletebtn);
        }
    }

}
