package com.example.socify.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.Activities.Home;
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




//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==-1 && requestCode==101) {
//            assert data != null;
//            String result = data.getStringExtra("RESULT");
//            Log.i("Result", result);
//            Uri resultUri = null;
//            if(result!=null) {
//                resultUri = Uri.parse(result);
////                imgUrl = resultUri;
////                Registration.details.setImgUri(String.valueOf(Uri.parse(String.valueOf(imgUrl))));
//            }
////            binding.profileImage.setImageURI(imgUrl);
//        }
//
//
//        public String getFileExt(Uri uri) {
//            ContentResolver contentResolver = requireContext().getContentResolver();
//            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//        }
//
//    }





}