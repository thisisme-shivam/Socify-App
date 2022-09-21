package com.example.socify.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Models.UserClubsModel;
import com.example.socify.R;
import com.example.socify.databinding.ClubListLayoutBinding;

import java.util.ArrayList;

public class UserClubsRecyclerAdapter extends RecyclerView.Adapter<UserClubsRecyclerAdapter.ViewHolder> {


    Context context;
    ArrayList<UserClubsModel> arrUserClubs;

    public UserClubsRecyclerAdapter(Context context, ArrayList<UserClubsModel> arrUserClubs)
    {
        this.context = context;
        this.arrUserClubs = arrUserClubs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.club_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.club_prof.setImageResource(arrUserClubs.get(position).clubprof);
        holder.subject.setText(arrUserClubs.get(position).clubSubject);
        holder.lastMssge.setText(arrUserClubs.get(position).lastMessage);
        holder.time.setText(arrUserClubs.get(position).time);

        
    }


    @Override
    public int getItemCount() {
        return arrUserClubs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView club_prof;
        TextView subject, lastMssge, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            club_prof = itemView.findViewById(R.id.club_profile);
            subject = itemView.findViewById(R.id.club_name);
            lastMssge = itemView.findViewById(R.id.club_last_message);
            time = itemView.findViewById(R.id.message_time);

        }
    }
}
