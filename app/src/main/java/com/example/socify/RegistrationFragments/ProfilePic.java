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
import com.example.socify.Classes.College;
import com.example.socify.R;
import com.example.socify.databinding.FragmentProfilePicBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class ProfilePic extends Fragment {

    FragmentProfilePicBinding binding;
    Uri imgUrl;
    public Registration registration;
    final int PICK_IMAGE = 1;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db;
    DocumentReference documentReference;
    ActivityResultLauncher<String> mTakePhoto;
    String Name, Yop, age, bio;
    static SendProfileData sendProfileData = new SendProfileData();
    public void FieldValidation() {
        //Field Validation
       if(binding.nametext.getText().toString().isEmpty()){
           binding.nametextlayout.setError("cannot be empty");
       }
       else{
           Name = binding.nametext.getText().toString();
       }

        if(binding.passingyeartext.getText().toString().isEmpty()){
            binding.yoptextlayout.setError("cannot be empty");
        }
        else{
            Yop = binding.passingyeartext.getText().toString();
        }

        if(binding.biotext.getText().toString().isEmpty()){
            binding.biolayout.setError("cannot be empty");
        }
        else{
            bio = binding.biotext.getText().toString();
        }

        if(binding.agetext.getText().toString().isEmpty()){
            binding.agelayout.setError("cannot be empty");
        }
        else{
            age = binding.agetext.getText().toString();
        }

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
                FieldValidation();
                if(Name!=null && Yop!=null) {
                    registration = (Registration) getActivity();
                    registration.details.setName(Name);
                    registration.details.setPassyear(Yop);
                    registration.details.setAge(age);
                    registration.details.setBio(bio);

                    //To be deleted Later
                    registration.details.setCollege_name("SISTEC");

                    //Sending Data
                    sendProfileData.sendImg();
                    sendProfileData.sendName();
                    sendProfileData.sendpassyear();
                    sendProfileData.sendCurrentUID();
                    sendProfileData.sendDOB();
                    sendProfileData.sendBio();

                    //Switching to new fragment
                    UserNameFragment userNameFragment = new UserNameFragment();
                    getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, userNameFragment).commit();
                    Log.e("Yop", Yop);
                    Log.e("Age", age);
                    Log.e("bio", bio);
                    Log.e("ImgURL", imgUrl.toString());
                    Log.e("Yop", Yop);
                }
            }
        });

    }

    public String getFileExt(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    Intent intent = new Intent(getActivity(), CropperActivity.class);
                    intent.putExtra("DATA", result.toString());
                    startActivityForResult(intent, 101);
                }
        );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1 && requestCode==101) {
            assert data != null;
            String result = data.getStringExtra("RESULT");
            Log.i("Result", result);
            Uri resultUri = null;
            if(result!=null) {
                resultUri = Uri.parse(result);
                imgUrl = resultUri;
                registration.details.setImgUri(String.valueOf(Uri.parse(String.valueOf(imgUrl))));
            }
            binding.profileImage.setImageURI(imgUrl);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("YEs","Entering");
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(20);
        DatabaseReference ref ;
        ArrayList<College> colleges = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("CollegeNames");

        ArrayList<College> finalColleges = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {


            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilePicBinding.inflate(inflater, container, false);
        onclicklisteners();
        return binding.getRoot();
    }


}