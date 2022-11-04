package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Classes.CommentMember;
import com.example.socify.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    Context context;
    ArrayList<CommentMember> commentMembers;

    public CommentsAdapter(Context context, ArrayList<CommentMember> commentMembers) {
        this.commentMembers = commentMembers;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commentlayout, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        CommentMember commentMember = commentMembers.get(position);
        holder.comment.setText(commentMember.getComment());
        holder.username.setText(commentMember.getUsername());
        long time = Long.valueOf(commentMember.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

        holder.time.setText(dateFormat.format(new Date(time)));

    }

    @Override
    public int getItemCount() {
        return commentMembers.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView comment, username, time;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.commenttv);
            username = itemView.findViewById(R.id.usernametv);
            time = itemView.findViewById(R.id.timestamp);

        }
    }

}
