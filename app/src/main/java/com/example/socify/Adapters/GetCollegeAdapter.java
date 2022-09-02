package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.Classes.College;
import com.example.socify.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class GetCollegeAdapter extends RecyclerView.Adapter<GetCollegeAdapter.CollegeViewHolder> {

    Context context;

    CollegeViewHolder.Onitemclicked onitemclicked;
    ArrayList<College> collegesNames;


    public GetCollegeAdapter(Context context , ArrayList<College> collegesNames, CollegeViewHolder.Onitemclicked onitemclicked){
        this.context = context;
        this.collegesNames = collegesNames;
        this.onitemclicked = onitemclicked;
    }


    @NonNull
    @Override
    public CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.college_name_list,parent,false);

        return new CollegeViewHolder(view,onitemclicked);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeViewHolder holder, int position) {
        College college = collegesNames.get(position);
        holder.collegeNameTextview.setText(college.getCollege_name());
        ;
    }

    @Override
    public int getItemCount() {
        return collegesNames.size();
    }

    public void filterlist(ArrayList<College> filteredlist) {
        collegesNames = filteredlist;
        notifyDataSetChanged();
    }

    public static class CollegeViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        MaterialTextView collegeNameTextview;
        Onitemclicked onitemclicked;
        public CollegeViewHolder(@NonNull View itemView,Onitemclicked onitemclicked) {
            super(itemView);
            this.onitemclicked = onitemclicked;

            collegeNameTextview = itemView.findViewById(R.id.college_names);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            onitemclicked.onclick(getBindingAdapterPosition());
        }

        public interface Onitemclicked{
            void onclick(int position);
        }

    }
}
