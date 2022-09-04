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
import com.example.socify.Classes.Course;
import com.example.socify.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class GetCourseAdapter extends RecyclerView.Adapter<GetCourseAdapter.CourseViewHolder> {

    Context context;

    CourseViewHolder.Onitemclicked onitemclicked;
    ArrayList<Course> coursesNames;


    public GetCourseAdapter(Context context , ArrayList<Course> coursesNames, CourseViewHolder.Onitemclicked onitemclicked){
        this.context = context;
        this.coursesNames = coursesNames;
        this.onitemclicked = onitemclicked;
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.courses_name_list,parent,false);

        return new CourseViewHolder(view,onitemclicked);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = coursesNames.get(position);
        holder.coursename.setText(course.getcoursename());

    }

    @Override
    public int getItemCount() {
        return coursesNames.size();
    }

    public void filterlist(ArrayList<Course> filteredlist) {
        coursesNames= filteredlist;
        notifyDataSetChanged();
    }

    public static class CourseViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        MaterialTextView coursename;
        Onitemclicked onitemclicked;
        public CourseViewHolder(@NonNull View itemView,Onitemclicked onitemclicked) {
            super(itemView);
            this.onitemclicked = onitemclicked;

            coursename = itemView.findViewById(R.id.coursename);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            onitemclicked.onclick(getBindingAdapterPosition());
        }

        public interface  Onitemclicked{
            void onclick(int position);
        }

    }
}
