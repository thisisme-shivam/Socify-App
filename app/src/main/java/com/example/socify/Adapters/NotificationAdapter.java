package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socify.Classes.NotificationMember;
import com.example.socify.R;
import com.example.socify.databinding.NotificationcontainerBinding;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Context context;
    ArrayList<NotificationMember> notifications;
    public NotificationAdapter(Context requireContext, ArrayList<NotificationMember> notifications) {
        this.context = requireContext;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificationcontainer,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        NotificationMember member = notifications.get(position);

        holder.binding.info.setText(member.getInfo());
        Glide.with(context).load(member.getImgurl()).into(holder.binding.personimage);
        holder.binding.personusername.setText(member.getUsername());
        holder.binding.time.setText(member.getTime());
        holder.binding.date.setText(member.getDate());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        NotificationcontainerBinding binding;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationcontainerBinding.bind(itemView);
        }
    }
}
