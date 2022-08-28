package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Models.DiscoverCommunityModel;
import com.example.socify.R;

import java.util.ArrayList;

public class RecyclerCommunityAdapter extends RecyclerView.Adapter<RecyclerCommunityAdapter.ViewHolder> {

    Context context;
    ArrayList<DiscoverCommunityModel> arrCommunities;

    public RecyclerCommunityAdapter(Context context, ArrayList<DiscoverCommunityModel> arrCommunities)
    {
        this.context = context;
        this.arrCommunities = arrCommunities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(context).inflate(R.layout.discover_communities, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.comImg.setImageResource(arrCommunities.get(position).communityImg);
        holder.comName.setText(arrCommunities.get(position).communityName);

    }

    @Override
    public int getItemCount() {
        return arrCommunities.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView comImg;
        TextView comName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comImg = itemView.findViewById(R.id.community_img);
            comName = itemView.findViewById(R.id.community_name);

        }

    }


}
