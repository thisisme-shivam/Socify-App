package com.example.socify.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.Classes.Message;
import com.example.socify.HomeFragments.FullScreenImgFragment;
import com.example.socify.R;
import com.example.socify.databinding.MsgreceivedlayoutBinding;
import com.example.socify.databinding.MsgsenderlayoutBinding;
import com.example.socify.databinding.ReceivemsgimglayoutBinding;
import com.example.socify.databinding.SendmsgimglayoutBinding;

import java.util.ArrayList;

public class MessagesLoaderAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Message> messages;
    final int ITEM_SENT = 1;
    final int ITEM_RECEIVED = 2;
    final int ITEM_IMG_SENT = 3;
    final int ITEM_IMG_RECEIVED = 4;


    public MessagesLoaderAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(Home.getUserData.uid.equals(message.getSenderID())) {
            if(message.getImgUri().equals("No Image")) {
                return ITEM_SENT;
            }
            return ITEM_IMG_SENT;
        }
        else {
            if(message.getImgUri().equals("No Image")) {
                return ITEM_RECEIVED;
            }
            return ITEM_IMG_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.msgsenderlayout, parent, false);
            return new SentViewHolder(view);
        }
        else if(viewType==ITEM_RECEIVED) {
            View view = LayoutInflater.from(context).inflate(R.layout.msgreceivedlayout, parent, false);
            return new ReceiverViewHolder(view);
        }
        else if(viewType==ITEM_IMG_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.sendmsgimglayout, parent, false);
            return new ImgSentViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receivemsgimglayout, parent, false);
            return new ImgReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if(holder.getClass().equals(SentViewHolder.class)) {
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.binding.sendermsg.setText(message.getMessage());
        }
        else if(holder.getClass().equals(ReceiverViewHolder.class)){
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.binding.receivermsg.setText(message.getMessage());
            Glide.with(context).load(message.getReceiverUri()).placeholder(R.drawable.user).into(viewHolder.binding.recevierimg);

        }
        else if(holder.getClass().equals(ImgSentViewHolder.class)) {
            ImgSentViewHolder viewHolder = (ImgSentViewHolder) holder;
            Glide.with(context).load(message.getImgUri()).placeholder(R.drawable.imgplaceholder).into(viewHolder.binding.chatimgsent);
            viewHolder.binding.chatimgsent.setOnClickListener(v -> {
                FullScreenImgFragment fragment = new FullScreenImgFragment();
                Bundle img = new Bundle();
                img.putString("imgurl", message.getImgUri());
                fragment.setArguments(img);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FragmentView, fragment).commit();
            });
        }
        else{
            ImgReceivedViewHolder viewHolder = (ImgReceivedViewHolder) holder;
            Glide.with(context).load(message.getImgUri()).placeholder(R.drawable.imgplaceholder).into(viewHolder.binding.chatimgreceived);
            viewHolder.binding.chatimgreceived.setOnClickListener(v -> {
                FullScreenImgFragment fragment = new FullScreenImgFragment();
                Bundle img = new Bundle();
                img.putString("imgurl", message.getImgUri());
                fragment.setArguments(img);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FragmentView, fragment).commit();
            });
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        MsgsenderlayoutBinding binding;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MsgsenderlayoutBinding.bind(itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        MsgreceivedlayoutBinding binding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MsgreceivedlayoutBinding.bind(itemView);
        }
    }

    public class ImgSentViewHolder extends RecyclerView.ViewHolder {

        SendmsgimglayoutBinding binding;

        public ImgSentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SendmsgimglayoutBinding.bind(itemView);
        }
    }

    public class ImgReceivedViewHolder extends RecyclerView.ViewHolder {
        ReceivemsgimglayoutBinding binding;

        public ImgReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReceivemsgimglayoutBinding.bind(itemView);
        }
    }


}
