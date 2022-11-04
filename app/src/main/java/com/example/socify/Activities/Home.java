package com.example.socify.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.socify.ClubFragments.CreateClubFragment;
import com.example.socify.ClubFragments.MyClubFragment;
import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.HomeFragments.CreatePostFragment;
import com.example.socify.HomeFragments.DiscoverFragment;
import com.example.socify.HomeFragments.NewsFeedFragment;
import com.example.socify.HomeFragments.ProfileFragment;
import com.example.socify.InterfaceClass;
import com.example.socify.QueryFragments.Ask_QueryFragment;
import com.example.socify.QueryFragments.QueryTagFragment;
import com.example.socify.R;
import com.example.socify.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;



public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    BottomNavigationView navigationView;
    NavController navController;


    int[] drawables;
    public static GetUserData getUserData;
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

        navigationView.getMenu().getItem(0).setIcon(drawables[4]);
        Dialog otpDialog = new Dialog(Home.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.post_creation_popup);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        //Loading User Profile Data
        final boolean[] first = {true};
        getUserData = new GetUserData(FirebaseAuth.getInstance().getCurrentUser().getUid(), (InterfaceClass.LoadDataInterface) () -> {
            if(first[0]) {
                //getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, newsFeedFragment).commit();
                first[0] = false;
            }
        });
        QueryTagFragment.tags = getUserData.tags;
        getUserData.loadFollowingList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        navController = Navigation.findNavController(this, R.id.FragmentView);

        NavigationUI.setupWithNavController(binding.bottomnavigationview, navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Online");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        FirebaseDatabase.getInstance().getReference("College").child(Home.getUserData.college_name).child("Online Users").child(Home.getUserData.uid).setValue("Offline");
//    }
}