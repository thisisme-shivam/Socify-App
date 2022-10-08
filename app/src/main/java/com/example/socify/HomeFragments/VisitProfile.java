package com.example.socify.HomeFragments;

import android.app.VoiceInteractor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Activities.Registration;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentVisitProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class VisitProfile extends Fragment {

    GetUserData getUserData;
    FragmentVisitProfileBinding binding;
    public VisitProfile(String uid) {
        getUserData = new GetUserData(uid);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CircleImageView imageView = getView().findViewById(R.id.profile_pic);
        imageView.setImageURI(Uri.parse(getUserData.imgurl));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}