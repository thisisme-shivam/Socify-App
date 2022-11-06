package com.example.socify.HomeFragments;

import static com.example.socify.HomeFragments.NewsFeedFragment.chattingusers;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.ChatListAdapter;
import com.example.socify.Classes.ChatListUser;
import com.example.socify.R;
import com.example.socify.databinding.FragmentAllChatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class AllChatFragment extends Fragment {

    FragmentAllChatBinding binding;
    ArrayList<ChatListUser> chatListUsers;
    ChatListAdapter chatListAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        chatListUsers = new ArrayList<>();
        getActivity().findViewById(R.id.bottomnavigationview).setVisibility(View.GONE);
        FirebaseDatabase.getInstance().getReference()
                .child("College")
                .child(Home.getUserData.college_name)
                .child("Chats")
                .child(Home.getUserData.uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                loadData(snapshot1.getKey());
                                Log.i("uid",snapshot.getKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        chatListAdapter = new ChatListAdapter(getActivity(), chatListUsers);
    }

    private  void loadData(String key){
            firebaseFirestore.collection("Profiles").document(key).addSnapshotListener((value, error) -> {
                if (value!=null) {
                    ChatListUser person = new ChatListUser();
                    person.setName(value.getString("Name"));
                    person.setImgUri(value.getString("ImgUrl"));
                    person.setUid(value.getString("UID"));
                    chatListUsers.add(person);
                }
                chatListAdapter.notifyDataSetChanged();
            });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });
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