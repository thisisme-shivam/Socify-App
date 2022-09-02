package com.example.socify.RegistrationFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Registration;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentNameFragementBinding;

import java.util.Objects;


public class UserNameFragment extends Fragment {

    FragmentNameFragementBinding binding;
    String Username, Password;
    SendProfileData sendProfileData = new SendProfileData();
    public Registration registration = (Registration) getActivity();


    public void onclicklisteners() {
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FieldValidation();
                if(Username!=null && Password!=null) {
                    Registration.details.setUsername(Username);
                    Registration.details.setPassword(Password);
                    Log.i("Name", Registration.details.getName());
                    Log.i("YOP", Registration.details.getPassyear());
                    Log.e("Username", Username);

                    //Uploading Username & Password
                    sendProfileData.sendUsername();
                    sendProfileData.sendPassword();

                    com.example.socify.Fragement_registration.GetCollegeFragment getCollegeFragment = new com.example.socify.Fragement_registration.GetCollegeFragment();
                    getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, getCollegeFragment).commit();
                }
            }
        });
    }

    public void FieldValidation() {

        if(binding.usernametext.getText().toString().isEmpty()){
            binding.usernametextlayout.setError("cannot be empty");
        }
        else{
            Username = binding.usernametext.getText().toString();
        }

        if(binding.passwordtext.getText().toString().isEmpty()){
            binding.passwordtextlayout.setError("cannot be empty");
        }
        else{
            Password = binding.passwordtext.getText().toString();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(40);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNameFragementBinding.inflate(inflater, container, false);
        onclicklisteners();
        if(!Objects.equals(Registration.details.getImgUri(), "No Image")) {
            binding.ProfilePic.setImageURI(Uri.parse(Registration.details.getImgUri()));
        }
        else{
            binding.picadded.setText("No Image Selected");
        }

        return binding.getRoot();
    }

}