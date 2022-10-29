package com.example.socify.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.Activities.Home;
import com.example.socify.R;
import com.example.socify.databinding.FragmentEditUserDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

public class EditUserDetails extends Fragment {

    FragmentEditUserDetailsBinding binding;
    ActivityResultLauncher<String> mTakePhoto;
    String currentUID  = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NavController navController;


    public void updateProfile() {

        final DocumentReference sfDoc = db.collection("Profiles").document(currentUID);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            //Code to be update more (BIO and DOB to be added while registration)
            transaction.update(sfDoc, "Name", binding.nameEditEt.getText().toString());
            transaction.update(sfDoc, "Passing Year", binding.passyearEt.getText().toString());

            // Success
            return null;
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditUserDetailsBinding.inflate(inflater, container, false);
        setonclicklisteners();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.getResult().exists()) {
                    binding.nameEditEt.setText(task.getResult().getString("Name"));
                    binding.passyearEt.setText(task.getResult().getString("Passing Year"));
                }
                else{
                    Toast.makeText(requireActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public void setonclicklisteners() {

        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                Toast.makeText(getActivity(), "Restarting the app will reflect the changes", Toast.LENGTH_LONG).show();
                NavDirections action = EditUserDetailsDirections.actionEditUserDetailsToProfileFragment();
                navController.navigate(action);
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = EditUserDetailsDirections.actionEditUserDetailsToProfileFragment();
                navController.navigate(action);
            }
        });

    }


}