package com.example.socify.HomeFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.socify.Activities.Home;
import com.example.socify.Adapters.CommentsAdapter;
import com.example.socify.Classes.CommentMember;
import com.example.socify.Classes.PostIdGetter;
import com.example.socify.R;
import com.example.socify.databinding.FragmentCommentsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CommentsFragment extends Fragment {

    String postid, posteruid;
    String commentkey = FirebaseDatabase.getInstance().getReference().push().getKey();
    ArrayList<CommentMember> commentMembers;
    CommentsAdapter commentsAdapter;

    FragmentCommentsBinding binding;

    DatabaseReference commentreference;

    private void setonclicklisteners() {



        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docomment();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController controller = Navigation.findNavController(view);

        CommentsFragmentArgs args = CommentsFragmentArgs.fromBundle(getArguments());
        postid = args.getPostid();
        posteruid = args.getPosteruid();

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions = CommentsFragmentDirections.actionCommentsFragmentToNewsFeedFragment();
                controller.navigate(directions);
            }
        });


        commentMembers = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(getActivity(), commentMembers);
        commentreference = FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Posts").child(posteruid).child("All Images").child(postid);
        fetch_comment();
        binding.commentlistRV.setAdapter(commentsAdapter);
        setonclicklisteners();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommentsBinding.inflate(inflater, container, false);

        return binding.getRoot();


    }

    private void docomment() {

        CommentMember commentMember = new CommentMember();
        commentMember.setComment(binding.commentbox.getText().toString());
        binding.commentbox.setText("");
        commentMember.setCommentkey(commentkey);
        commentMember.setTime(String.valueOf(new Date().getTime()));
        commentMember.setUid(Home.getUserData.uid);
        commentMember.setuserName(Home.getUserData.username);

        commentreference.child("Comments").child(commentkey).setValue(commentMember);
        hideSoftKeyboard(requireActivity(), binding.commentbox);
    }

    private void fetch_comment() {
        commentreference.child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot cmtsnap: snapshot.getChildren()) {
                    CommentMember member = cmtsnap.getValue(CommentMember.class);
                    commentMembers.add(member);
                }
                commentsAdapter.notifyDataSetChanged();
                binding.commentlistRV.smoothScrollToPosition(binding.commentlistRV.getAdapter().getItemCount());//scroll to latest
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

}