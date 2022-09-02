package com.example.socify.HomeFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.socify.Activities.CropperActivity;
import com.example.socify.Activities.Home;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentEditUserDetailsBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EditUserDetails extends Fragment {

    FragmentEditUserDetailsBinding binding;
    String currentUID  = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;


    public void updateProfile() {

        final DocumentReference sfDoc = db.collection("Profiles").document(currentUID);

        db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(sfDoc);
                        //Code to be update more (BIO and DOB to be added while registration)
                        transaction.update(sfDoc, "Name", binding.nameEditEt.getText().toString());
                        transaction.update(sfDoc, "Passing Year", binding.passyearEt.getText().toString());
                        transaction.update(sfDoc, "Bio", binding.bioEt.getText().toString());
                        transaction.update(sfDoc, "Age", binding.ageEt.getText().toString());
                        // Success
                        return null;
                    }
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
                    binding.ageEt.setText(task.getResult().getString("Age"));
                    binding.bioEt.setText(task.getResult().getString("Bio"));
                }
                else{
                    Toast.makeText(requireActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void setonclicklisteners() {

        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                Toast.makeText(requireActivity(), "Restarting the app will reflect the new changes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });

    }

}