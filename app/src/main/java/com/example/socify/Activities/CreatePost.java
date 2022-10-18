package com.example.socify.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socify.Classes.PostMember;
import com.example.socify.R;
import com.example.socify.databinding.ActivityCreatePostBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePost extends AppCompatActivity {

    ActivityCreatePostBinding binding;
    private static final int PICK_FILE=1;
    private Uri selectedUri = null;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userimagesref,uservideosref;
    MediaController mediaController;
    String type;
    PostMember postMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaController = new MediaController(this);
        postMember = new PostMember();
        binding.name.setText(Home.getUserData.name);
        Glide.with(this).load(Home.getUserData.imgurl).into(binding.profilepic);
        userimagesref = database.getReference("College").child(Home.getUserData.college_name).child("Posts").child(Home.getUserData.uid).child("All Images");
        uservideosref = database.getReference("College").child(Home.getUserData.college_name).child("Posts").child(Home.getUserData.uid).child("All Videos");
        storageReference = FirebaseStorage.getInstance().getReference("Posts").child("User's Posts").child(Home.getUserData.uid);
        setonclicklisteners();
    }

    private void setonclicklisteners() {
        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dopost();

            }
        });

        binding.selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePost.this, Home.class));
            }
        });

    }

    @SuppressLint("IntentReset")
    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(intent, PICK_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == PICK_FILE && data != null) {
            selectedUri = data.getData();
            Log.e("Selected URI", String.valueOf(selectedUri));
            if(selectedUri.toString().contains("image")) {
                Glide.with(this).load(selectedUri).placeholder(R.drawable.person_login).into(binding.imageview);
                binding.imageview.setVisibility(View.VISIBLE);
                binding.videoview.setVisibility(View.INVISIBLE);
                type = "image";
                Log.e("Selected URI", String.valueOf(selectedUri));
            }
            else if(selectedUri.toString().contains("video")) {
                binding.videoview.setMediaController(mediaController);
                binding.videoview.setVisibility(View.VISIBLE);
                binding.imageview.setVisibility(View.INVISIBLE);
                binding.videoview.setVideoURI(selectedUri);
                binding.videoview.start();
                type = "video";
                Log.e("Selected URI", String.valueOf(selectedUri));
            }
            else
                Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void Dopost() {
        String description = binding.desc.getText().toString();

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
        final String saveDate = currenDate.format(cdate.getTime());


        if(!TextUtils.isEmpty(binding.desc.getText()) && selectedUri!=null) {
            finish();
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ ":" + getFileExt(selectedUri));
            uploadTask = reference.putFile(selectedUri);

            uploadTask.continueWithTask(task -> {
                if(!task.isSuccessful()) {
                    throw task.getException();
                }
                return reference.getDownloadUrl();

            }).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Uri downloaduri = task.getResult();

                    postMember.setName(Home.getUserData.name);
                    postMember.setDesc(description);
                    postMember.setTime(saveDate);
                    postMember.setPostUri(downloaduri.toString());
                    postMember.setUid(Home.getUserData.uid);
                    postMember.setUrl(Home.getUserData.imgurl);
                    postMember.setUsername(Home.getUserData.username);

                    if (type.equals("image")) {
                        postMember.setType("image");

                        //Storing ImagePost
                        String id1 = userimagesref.push().getKey();
                        Log.e("ID", id1);
                        postMember.setPostid(id1);

                        userimagesref.child(id1).setValue(postMember);
                        //Storing Allpost Image
                        Toast.makeText(this, "Post Uploaded", Toast.LENGTH_SHORT).show();
                    }
                    else if(type.equals("video")) {
                        postMember.setType("video");
                        //Storing VideoPost
                        String id3 = uservideosref.push().getKey();
                        postMember.setPostid(id3);
                        assert id3 != null;
                        uservideosref.child(id3).setValue(postMember);
                        //Storing Allpost VideoPost
                        Toast.makeText(this, "Post Uploaded", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Error Uploading", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        else{
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

    }




}