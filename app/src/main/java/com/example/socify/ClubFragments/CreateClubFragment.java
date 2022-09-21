package com.example.socify.ClubFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.example.socify.Activities.CropperActivity;
import com.example.socify.Activities.Registration;
import com.example.socify.R;
import com.example.socify.databinding.FragmentCreateClubBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class CreateClubFragment extends Fragment {

    public static int cropperFlag;
    FragmentCreateClubBinding binding;
    Uri image;

    ActivityResultLauncher<String> takePhoto;


    public void validation()
    {
        if(binding.clubSubject.getText().toString().isEmpty())
            binding.clubSubjectLayout.setError("cannot be empty");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        takePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    Intent intent = new Intent(getActivity(), CropperActivity.class);
                    intent.putExtra("DATA", result.toString());
                    cropperFlag = 1;
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
                image = resultUri;
            }
            binding.clubProfile.setImageURI(image);
        }
    }

    public void onClickListeners()
    {
        binding.clubProfile.setOnClickListener(view -> takePhoto.launch("image/*"));

        binding.btnCreateClub.setOnClickListener(view -> takePhoto.launch("image/*"));

        binding.btnCreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
                String clubSubject = binding.clubSubject.getText().toString();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateClubBinding.inflate(inflater, container, false);

        onClickListeners();


        return binding.getRoot();
    }
}