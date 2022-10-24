package com.example.socify.HomeFragments;

import static com.example.socify.HomeFragments.NewsFeedFragment.chattingusers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.Adapters.ChatListAdapter;
import com.example.socify.Classes.ChatListUser;
import com.example.socify.R;
import com.example.socify.databinding.FragmentAllChatBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class AllChatFragment extends Fragment {

    FragmentAllChatBinding binding;
    ArrayList<ChatListUser> chatListUsers;
    ChatListAdapter chatListAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        chatListUsers = new ArrayList<>();

        for(String chatlist: chattingusers) {
            firebaseFirestore.collection("Profiles").document(chatlist).addSnapshotListener((value, error) -> {
                if (value!=null) {
                    Log.e("Chatlist", chatlist);
                    ChatListUser person = new ChatListUser();
                    person.setName(value.getString("Name"));
                    person.setImgUri(value.getString("ImgUrl"));
                    person.setUid(value.getString("UID"));
                    chatListUsers.add(person);
                }
            });
        }

        chatListAdapter = new ChatListAdapter(getActivity(), chatListUsers);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllChatBinding.inflate(inflater, container, false);
        binding.recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.notifyDataSetChanged();
        return binding.getRoot();
    }
}