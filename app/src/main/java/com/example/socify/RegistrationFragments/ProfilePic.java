package com.example.socify.RegistrationFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socify.Activities.CropperActivity;
import com.example.socify.Activities.Registration;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentProfilePicBinding;

import java.util.ArrayList;
import java.util.Calendar;


public class ProfilePic extends Fragment {

    FragmentProfilePicBinding binding;
    Uri imgUrl;

    ActivityResultLauncher<String> mTakePhoto;
    String name, passing_year;
    static SendProfileData sendProfileData = new SendProfileData();

    public boolean FieldValidation() {
        //Field Validation
        name = binding.nametext.getText().toString().trim();
        passing_year = binding.graduationYear.getText().toString();
       if(name.isEmpty())
           binding.nametextlayout.setError("Name cannot be empty");
       else if (name.length()<3)
           binding.nametextlayout.setError("Name should be atleast 3 characters");
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
                   Registration.details.setName(name);
                   Registration.details.setPassyear(passing_year);
                   //Sending Data
                   if(imgUrl == null)
                       Registration.details.setImgUri("");
                   sendProfileData.sendImg();
                   sendProfileData.sendName();
                   sendProfileData.sendpassyear();
                   sendProfileData.sendCurrentUID();

                   //Switching to new fragment
                   getActivity().findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
                   Registration.fragment_curr_pos++;
                   getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, Registration.userNameFragment).commit();
                   getActivity().findViewById(R.id.back_icon).setVisibility(View.VISIBLE);
               }

            }
        });



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            assert data != null;
            String result = data.getStringExtra("RESULT");
            Log.i("Result", result);
            Uri resultUri = null;
            if(result!=null) {
                resultUri = Uri.parse(result);
                imgUrl = resultUri;
                Registration.details.setImgUri(String.valueOf(Uri.parse(String.valueOf(imgUrl))));
            }
            binding.profileImage.setImageURI(imgUrl);
        }
    }

    int i=1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onclicklisteners();
        Log.i("YEs", "Entering");
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        binding.profileImage.setImageURI(imgUrl);
        bar.setProgress(20);
        ArrayList<String> years = new ArrayList<>();
        int curr_year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i <= 6; i++) {
            years.add(String.valueOf(curr_year + i));
        }
        binding.graduationYear.setItems(years);
        i=0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilePicBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


}