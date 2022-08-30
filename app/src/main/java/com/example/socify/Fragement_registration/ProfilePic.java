package com.example.socify.Fragement_registration;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.socify.Activities.Home;
import com.example.socify.Activities.Registration;
import com.example.socify.R;
import com.example.socify.databinding.FragmentProfilePicBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.local.BundleCache;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;


public class ProfilePic extends Fragment {

    FragmentProfilePicBinding binding;
    Uri imgUrl;
    final int PICK_IMAGE = 1;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db;
    DocumentReference documentReference;
    ActivityResultLauncher<String> mTakePhoto;
    String Name, Username, Password, Yop;

    public void FieldValidation() {
        //Field Validation
       if(binding.nametext.getText().toString().isEmpty()){
           binding.nametextlayout.setError("cannot be empty");
       }
       else{
           Name = binding.nametext.getText().toString();
       }

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

        if(binding.passingyeartext.getText().toString().isEmpty()){
            binding.yoptextlayout.setError("cannot be empty");
        }
        else{
            Yop = binding.passingyeartext.getText().toString();
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
                if(Name!=null && Username!=null && Password!=null && Yop!=null) {
                    GetCollegeFragment getCollegeFragment = new GetCollegeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frame_registration, getCollegeFragment).commit();
                    Log.e("Username", Username);
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