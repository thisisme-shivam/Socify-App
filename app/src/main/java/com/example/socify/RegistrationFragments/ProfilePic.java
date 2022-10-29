package com.example.socify.RegistrationFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socify.Activities.CropperActivity;
import com.example.socify.Activities.Registration;
import com.example.socify.HomeFragments.NewsFeedFragment;
import com.example.socify.R;
import com.example.socify.databinding.FragmentProfilePicBinding;

import java.util.ArrayList;
import java.util.Calendar;


public class ProfilePic extends Fragment {

    FragmentProfilePicBinding binding;
    Uri imgUrl;
    ActivityResultLauncher<String> mTakePhoto;
    String name, passing_year;
    Registration regActivity;
    UserNameFragment userNameFragment;
    public boolean FieldValidation() {
        //Field Validation
        name = binding.nametext.getText().toString().trim();
        passing_year = binding.graduationYear.getText().toString();
       if(name.isEmpty())
           binding.nametextlayout.setError("Name cannot be empty");
       else if (name.length()<3)
           binding.nametextlayout.setError("Name should contain atleast 2 characters");
       else if (passing_year.isEmpty())
           binding.graduationYear.setError("Select your graduation year");
       else
           return true;

        return false;
    }

    public void onclicklisteners() {
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTakePhoto.launch("image/*");
            }
        });
        binding.addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTakePhoto.launch("image/*");
            }
        });
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(FieldValidation()) {

                   setData();
                   //Switching to next fragment
                   getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, userNameFragment).commitNowAllowingStateLoss();
                   regActivity.findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
               }
            }
        });



    }


    private void setData(){
        regActivity.profiledetails.put("Name",name);
        regActivity.profiledetails.put("Passing Year",passing_year);

        if(imgUrl != null) {
            regActivity.setImgUri(imgUrl);
        }
        regActivity.putImage();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActivity = (Registration) getActivity();
        userNameFragment = new UserNameFragment();
        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    Intent intent = new Intent(getActivity(), CropperActivity.class);
                    if(result !=null) {
                        intent.putExtra("DATA", result.toString());
                        startActivityForResult(intent, 101);
                    }

                }
        );



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==-1 && requestCode==101 && data!= null ) {
            String result = data.getStringExtra("RESULT");
            Log.i("Result", result);
            if(result!=null) {
                imgUrl = Uri.parse(result);
            }
            binding.profileImage.setImageURI(imgUrl);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onclicklisteners();

        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        binding.profileImage.setImageURI(imgUrl);
        bar.setProgress(20);
        ArrayList<String> years = new ArrayList<>();
        int curr_year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i <= 6; i++) {
            years.add(String.valueOf(curr_year + i));
        }
        binding.graduationYear.setItems(years);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilePicBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


}