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
import android.widget.Toast;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.Registration;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentInterestsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import nl.bryanderidder.themedtogglebuttongroup.SelectAnimation;
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class InterestsFragment extends Fragment {

    SendProfileData sendProfileData = new SendProfileData();
    ArrayList<String> tags;
    FragmentInterestsBinding binding;
    List<ThemedButton> str;


    public void onclicklisteners() {
        binding.finishbtn.setOnClickListener(v -> {
            //Getting text from tapped tags
            if(str.isEmpty()){
                Toast.makeText(getContext(),"Select atleast 1 tag " , Toast.LENGTH_SHORT).show();
            }else {
                for (ThemedButton but : str) {
                    String newstr = but.getText().replaceAll("[^A-Za-z]+", "");
                    tags.add(newstr);

                    Log.i("string", newstr);
                }
                Registration.details.setTags(tags);
                //Uploading Tags
                sendProfileData.sendTags();
                startActivity(new Intent(requireActivity(), Home.class));
                requireActivity().finish();
            }
        });

        binding.groupedtags.setOnSelectListener(themedButton -> {
            str.add(themedButton);
            Log.i("button selected", themedButton.getText());
            return null;
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        str = new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(100);
        tags = new ArrayList<>();
        binding.groupedtags.setSelectAnimation(SelectAnimation.CIRCULAR_REVEAL);
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