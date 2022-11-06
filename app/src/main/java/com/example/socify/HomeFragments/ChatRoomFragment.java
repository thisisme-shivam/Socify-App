package com.example.socify.HomeFragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.Adapters.MessagesLoaderAdapter;
import com.example.socify.Classes.Message;
import com.example.socify.R;
import com.example.socify.databinding.FragmentChatRoomBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatRoomFragment extends Fragment {

    FragmentChatRoomBinding binding;
    String name;
    String UID;
    String imgurl;
    MessagesLoaderAdapter adapter;
    ArrayList<Message> messages;
    String senderRoom, receiverRoom;
    String senderUID, receiverUID;
    FirebaseDatabase chatdb;
    Uri chatimgUri;
    StorageReference storageReference;
    String msgtext;
    ProgressDialog dialog;

    private void setonclicklisteners() {

        binding.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        binding.sendbtn.setOnClickListener(v -> {
            sendMsg("No Image",binding.messagebox.getText().toString());
        });

    }


    private void sendMsg(String imgUri, String msgtext) {

        if(!msgtext.equals("")) {
            Date date = new Date();
            Message message = new Message(msgtext, senderUID, date.getTime(), Home.getUserData.imgurl);
            message.setMessage(msgtext);
            message.setSenderID(senderUID);
            message.setTimestamp(date.getTime());
            message.setReceiverUri(Home.getUserData.imgurl);
            message.setImgUri(imgUri);
            binding.messagebox.setText("");
            String randomKey = chatdb.getReference().push().getKey();

            HashMap<String, Object> lastMsg = new HashMap<>();
            lastMsg.put("lastMsg", message.getMessage());
            lastMsg.put("lastMsgTime", date.getTime());

            chatdb.getReference("College").child(Home.getUserData.college_name).child("Chats").child(Home.getUserData.uid).child(receiverUID).updateChildren(lastMsg);
            chatdb.getReference("College").child(Home.getUserData.college_name).child("Chats").child(receiverUID).child(Home.getUserData.uid).updateChildren(lastMsg);

            chatdb.getReference("College")
                    .child(Home.getUserData.college_name)
                    .child("Chats")
                    .child(Home.getUserData.uid)
                    .child(receiverUID)
                    .child("Messages")
                    .child(randomKey)
                    .setValue(message)
                    .addOnSuccessListener(unused -> chatdb.getReference("College")
                            .child(Home.getUserData.college_name)
                            .child("Chats")
                            .child(receiverUID)
                            .child(Home.getUserData.uid)
                            .child("Messages")
                            .child(randomKey)
                            .setValue(message));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && data!=null) {
            chatimgUri = data.getData();
            uploadimg();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Online");
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Offline");
    }

    private void checkStatus() {

        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(receiverUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    if(!status.isEmpty()) {
                        binding.status.setText(status);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.messagebox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            final Handler handler = new Handler();

            @Override
            public void afterTextChanged(Editable s) {
                FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Typing...");
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(typingStopped, 500);
            }

            Runnable typingStopped = () -> {
                FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Online");
            };

        });


    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadimg() {
        storageReference = FirebaseStorage.getInstance().getReference("ChatImgs").child(Home.getUserData.uid).child(receiverUID);
        final StorageReference reference = storageReference.child(System.currentTimeMillis() + ":" + getFileExt(chatimgUri));

        UploadTask uploadTask = reference.putFile(chatimgUri);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return reference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloaduri = task.getResult();
                Date date = new Date();
                sendMsg(downloaduri.toString(),"photo");
            } else {
                Toast.makeText(getActivity(), "Error Uploading", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loaddetails() {
        senderUID = Home.getUserData.uid;
        receiverUID = UID;
        senderRoom = senderUID + receiverUID;
        receiverRoom = receiverUID + senderUID;
        Log.i("receiverUID", UID);
        binding.usernametxt.setText(name);
        Glide.with(this).load(imgurl).placeholder(R.drawable.user).into(binding.profilepicchatuser);
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        chatdb = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        messages = new ArrayList<>();
        adapter = new MessagesLoaderAdapter(getActivity(), messages);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NavController controller = Navigation.findNavController(view);
        binding.backIcon.setOnClickListener(v -> {
            NavDirections directions = ChatRoomFragmentDirections.actionChatRoomFragmentToAllChatFragment();
            controller.navigate(directions);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatRoomBinding.inflate(inflater, container, false);
        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());

        name = args.getName();
        UID = args.getUid();
        imgurl = args.getImage();

        loaddetails();
        msgtext = binding.messagebox.getText().toString();

        chatdb.getReference("College")
                .child(Home.getUserData.college_name)
                .child("Chats")
                .child(Home.getUserData.uid)
                .child(receiverUID)
                .child("Messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                            Message message1 = snapshot1.getValue(Message.class);
                            messages.add(message1);
                        }
                        adapter.notifyDataSetChanged();
                        binding.chatRV.smoothScrollToPosition(binding.chatRV.getAdapter().getItemCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.chatRV.setAdapter(adapter);
        setonclicklisteners();
        checkStatus();

        return binding.getRoot();
    }
}