package com.example.socify.RegistrationFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.Registration;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentInterestsBinding;

import java.util.ArrayList;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.SelectAnimation;
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class InterestsFragment extends Fragment {

    SendProfileData sendProfileData = new SendProfileData();
    ArrayList<String> tags;
    FragmentInterestsBinding binding;

    public void onclicklisteners() {
        binding.finishbtn.setOnClickListener(v -> {
            //Getting text from tapped tags
            List<ThemedButton> str = binding.groupedtags.getSelectedButtons();
            for(ThemedButton but : str){
                String newstr = but.getText().replaceAll("[^A-Za-z]+", "");
                tags.add(newstr);
                Registration.details.setTags(tags);
                //Uploading Tags
                sendProfileData.sendTags();
                Log.i("string",newstr);

                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(100);
        tags = new ArrayList<>();
        binding.groupedtags.setSelectAnimation(SelectAnimation.CIRCULAR_REVEAL);
        binding.groupedtags.setSelectableAmount(5);
        onclicklisteners();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInterestsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}