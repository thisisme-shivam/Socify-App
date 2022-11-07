package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socify.Classes.Person;
import com.example.socify.HomeFragments.SearchAllDirections;
import com.example.socify.HomeFragments.VisitProfile;
import com.example.socify.HomeFragments.VisitProfileDirections;
import com.example.socify.R;
import com.example.socify.databinding.DisplaypersonrecyclerviewBinding;

import java.util.ArrayList;

public class SearchPersonAdapter extends RecyclerView.Adapter<SearchPersonAdapter.PersonViewHolder> {
    Context context;
    ArrayList<Person> persons;
    public SearchPersonAdapter(Context context , ArrayList<Person> persons){
         this.context =  context;
         this.persons = persons;
    }
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.displaypersonrecyclerview,parent,false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = persons.get(position);
        holder.binding.personusername.setText("@" + person.getUsername());
        Glide.with(context).load(person.getUri()).placeholder(R.drawable.person_login).into(holder.binding.personimage);
        holder.binding.personname.setText(person.getName());
        if(person.getFollow_status())
            holder.binding.following.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            NavDirections directions = SearchAllDirections.actionSearchAll2ToVisitProfile(person.getUid());
            navController.navigate(directions);
        });
    }


    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void filterlist(ArrayList<Person> filteredlist) {
        persons = filteredlist;
        notifyDataSetChanged();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        DisplaypersonrecyclerviewBinding binding;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DisplaypersonrecyclerviewBinding.bind(itemView);
        }

    }
}
