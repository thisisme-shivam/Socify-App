package com.example.socify.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socify.Classes.Person;
import com.example.socify.HomeFragments.VisitProfile;
import com.example.socify.R;
import com.example.socify.databinding.DisplaypersonrecyclerviewBinding;

import java.util.ArrayList;

public class PersonAdapter  extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    Context context;
    ArrayList<Person> persons;
    public PersonAdapter(Context context , ArrayList<Person> persons){
         this.context =context;
         this.persons = persons;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.displaypersonrecyclerview,parent,false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = persons.get(position);
        holder.binding.personusername.setText(person.getUsername());
        Glide.with(context).load(person.getUri()).placeholder(R.drawable.person_login).into(holder.binding.personimgage);
        holder.binding.personname.setText(person.getName());
        if(person.getFollow_status().equals("yes"))
            holder.binding.following.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, VisitProfile.class)));
    }


    @Override
    public int getItemCount() {
        return persons.size();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        DisplaypersonrecyclerviewBinding binding;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DisplaypersonrecyclerviewBinding.bind(itemView);
        }

    }
}
