package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Classes.College;
import com.example.socify.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class GetCollegeAdapter extends RecyclerView.Adapter<GetCollegeAdapter.CollegeViewHolder> {

    Context context;
    ArrayList<College> collegesNames;

    public GetCollegeAdapter(Context context , ArrayList<College> collegesNames){
        this.context = context;
        this.collegesNames = collegesNames;
    }
    @NonNull
    @Override
    public CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.college_name_list,parent,false);

        return new CollegeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeViewHolder holder, int position) {
        College college = collegesNames.get(position);
        holder.collegeNameTextview.setText(college.getCollege_name());
    }

    @Override
    public int getItemCount() {
        return collegesNames.size();
    }

    public static class CollegeViewHolder extends  RecyclerView.ViewHolder{

        MaterialTextView collegeNameTextview;
        public CollegeViewHolder(@NonNull View itemView) {
            super(itemView);
            collegeNameTextview = itemView.findViewById(R.id.college_names);

        }
    }
}
