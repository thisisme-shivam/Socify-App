package com.example.socify.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.HomeFragments.CommentsFragment;
import com.example.socify.HomeFragments.DiscoverFragment;
import com.example.socify.HomeFragments.NewsFeedFragment;
import com.example.socify.HomeFragments.ProfileFragment;
import com.example.socify.HomeFragments.VisitProfile;
import com.example.socify.R;
import com.example.socify.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
    ProfileFragment profileFragment  = new ProfileFragment();
    BottomNavigationView navigationView;
    DiscoverFragment discoverFragment = new DiscoverFragment();
    int[] drawables;
    public ArrayList<String> tags;
    public static  GetUserData getUserData;
    int lastSelected;


    public void setIcon(int i){
        if(lastSelected == i ){
            return;
        }
        if(lastSelected > 2 )
            navigationView.getMenu().getItem(lastSelected).setIcon(drawables[lastSelected-1]);
        else
            navigationView.getMenu().getItem(lastSelected).setIcon(drawables[lastSelected]);
        if(i > 2 )
            navigationView.getMenu().getItem(i).setIcon(drawables[i+3]);
        else
            navigationView.getMenu().getItem(i).setIcon(drawables[i+4]);

    }

    public void itemselectedfromnavbar() {

        binding.bottomnavigationview.setOnItemSelectedListener(item -> {


            if (item.getItemId() == R.id.newsfeed) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,newsFeedFragment).commitNowAllowingStateLoss();
                setIcon(0);
                lastSelected = 0;
            }else if(item.getItemId() == R.id.discover){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,discoverFragment).commitAllowingStateLoss();
                setIcon(1);
                lastSelected =1;
            }
            else if(item.getItemId() == R.id.addpost){
                showDialog();
            }
            else if(item.getItemId() == R.id.clubs){
                setIcon(3);
                lastSelected = 3;
                showDialogAccess();
            }
            else if(item.getItemId() == R.id.profile){
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,profileFragment).commitNowAllowingStateLoss();
                setIcon(4);
                lastSelected = 4;
            }

            return false;
        });

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_popup);

        LinearLayout query = dialog.findViewById(R.id.askquery);
        LinearLayout post = dialog.findViewById(R.id.createpost);
        LinearLayout club = dialog.findViewById(R.id.mycommunities);

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for query creation to be written here
                Toast.makeText(Home.this, "Query selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, QnA.class));
                QnA.fragwitch =0;
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for post creation to be written here
                Toast.makeText(Home.this, "Post selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, CreatePost.class));
            }
        });

        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for community creation to be written here
                Toast.makeText(Home.this, "Club selected", Toast.LENGTH_SHORT).show();
                clubs.clubFragSwitch = 0;
                startActivity(new Intent(Home.this, clubs.class));
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showDialogAccess() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_popup);

        LinearLayout myquery = dialog.findViewById(R.id.myqueries);
        LinearLayout mygroups = dialog.findViewById(R.id.mygroups);
        LinearLayout myclubs = dialog.findViewById(R.id.mycommunities);

        myquery.setOnClickListener(v -> {
            //Code for query creation to be written here
            Toast.makeText(Home.this, "MYQuery selected", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Home.this, QnA.class));
            QnA.fragwitch =1;
        });

        mygroups.setOnClickListener(v -> {
            //Code for post creation to be written here
            Toast.makeText(Home.this, "Groups selected", Toast.LENGTH_SHORT).show();
        });

        myclubs.setOnClickListener(v -> {
            //Code for community creation to be written here
            Toast.makeText(Home.this, "my clubs selected", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Home.this, clubs.class));
            clubs.clubFragSwitch = 1;
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigationView = binding.bottomnavigationview;
        drawables = new int[]{
                R.drawable.newsgrey,
                R.drawable.discovergrey,
                R.drawable.clubicongrey,
                R.drawable.profilegrey,
                R.drawable.newsicon,
                R.drawable.discovericon,
                R.drawable.clubicon,
                R.drawable.profileicon
        };
        binding.bottomnavigationview.setItemIconTintList(null);


        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,newsFeedFragment).commit();
        itemselectedfromnavbar();
        navigationView.getMenu().getItem(0).setIcon(drawables[4]);
        Dialog otpDialog = new Dialog(Home.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.post_creation_popup);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        //Loading User Profile Data
        getUserData = new GetUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
        getUserData.loadFollowingList();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}