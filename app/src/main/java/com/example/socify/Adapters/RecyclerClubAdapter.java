package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Models.DiscoverClubModel;
import com.example.socify.R;

import java.util.ArrayList;

public class RecyclerClubAdapter extends RecyclerView.Adapter<RecyclerClubAdapter.ViewHolder> {

    Context context;
    ArrayList<DiscoverClubModel> arrClubModel;

    public RecyclerClubAdapter(Context context, ArrayList<DiscoverClubModel> arrClubModel)
    {
        this.context = context;
        this.arrClubModel = arrClubModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.discover_clubs, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(arrClubModel.get(position).clubImg);
        holder.name.setText(arrClubModel.get(position).clubName);
    }

    @Override
    public int getItemCount() {
        return arrClubModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;

        public ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.club_name);
            img = itemView.findViewById(R.id.club_img);

        }
    }
}
