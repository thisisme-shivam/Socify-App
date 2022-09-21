package com.example.socify.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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

import com.example.socify.Classes.PostMember;
import com.example.socify.R;
import com.example.socify.databinding.ActivityCreatePostBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePost extends AppCompatActivity {

    ActivityCreatePostBinding binding;
    private static final int PICK_FILE=1;
    private Uri selectedUri;
    UploadTask uploadTask;
    String name, url, username;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db1,db2,db3;
    DocumentReference documentReference;
    MediaController mediaController;
    String type;
    String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    PostMember postMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaController = new MediaController(this);
        postMember = new PostMember();
        db1 = database.getReference("Posts").child("All Images").child(currentUID);
        db2 = database.getReference("Posts").child("All Videos").child(currentUID);
        db3 = database.getReference("Posts").child("All Posts").child(currentUID);
        storageReference = FirebaseStorage.getInstance().getReference("Posts").child("User's Posts").child(currentUID);;
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

        if(requestCode==PICK_FILE || requestCode==RESULT_OK
        || data!=null || data.getData()!=null) {
            selectedUri = data.getData();
            Log.e("Selected URI", String.valueOf(selectedUri));
            if(selectedUri.toString().contains("image")) {
                Picasso.get().load(selectedUri).into(binding.imageview);
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

        String time = saveDate;
        if(TextUtils.isEmpty(binding.desc.getText()) || selectedUri!=null) {
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ ":" + getFileExt(selectedUri));
            uploadTask = reference.putFile(selectedUri);

            Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
                if(!task.isSuccessful()) {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    Uri downloaduri = task.getResult();

                    if (type=="image") {
                        postMember.setName(name);
                        postMember.setDesc(description);
                        postMember.setTime(saveDate);
                        postMember.setType("image");
                        postMember.setPostUri(downloaduri.toString());
                        postMember.setUid(currentUID);
                        postMember.setUrl(url);
                        postMember.setUsername(username);

                        //Storing ImagePost
                        String id1 = db1.push().getKey();
                        assert id1 != null;
                        db1.child(id1).setValue(postMember);

                        //Storing Allpost Image
                        String id2 = db3.push().getKey();
                        db3.child(id2).setValue(postMember);
                        Toast.makeText(this, "Post Uploaded", Toast.LENGTH_SHORT).show();

                    }
                    else if(type=="video") {
                        postMember.setName(name);
                        postMember.setDesc(description);
                        postMember.setTime(saveDate);
                        postMember.setType("video");
                        postMember.setPostUri(downloaduri.toString());
                        postMember.setUid(currentUID);
                        postMember.setUrl(url);
                        postMember.setUsername(username);

                        //Storing VideoPost
                        String id3 = db1.push().getKey();
                        assert id3 != null;
                        db2.child(id3).setValue(postMember);

                        //Storing Allpost VideoPost
                        String id4 = db3.push().getKey();
                        db3.child(id4).setValue(postMember);
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

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);

        documentReference.get()
                .addOnCompleteListener(task -> {
                    if(task.getResult().exists()) {
                        name = task.getResult().getString("Name");
                        url = task.getResult().getString("ImgUrl");
                        username = task.getResult().getString("Username");
                        Picasso.get().load(url).into(binding.profilepic);
                        binding.name.setText(name);
                    }
                    else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }

                });
    }



}