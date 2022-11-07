package com.example.socify.Activities;

import android.app.Dialog;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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

import io.reactivex.rxjava3.internal.schedulers.NewThreadWorker;


public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    BottomNavigationView navigationView;
    NavController navController;


    int[] drawables;
    public static GetUserData getUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigationView = binding.bottomnavigationview;

        drawables = new int[]{
                R.drawable.newsgrey,
                R.drawable.discovergrey,
                R.drawable.plusicongrey,
                R.drawable.clubicongrey,
                R.drawable.profilegrey,
                R.drawable.newsicon,
                R.drawable.discovericon,
                R.drawable.plusicon,
                R.drawable.clubicon,
                R.drawable.profileicon
        };

        binding.bottomnavigationview.setItemIconTintList(null);

        navigationView.getMenu().getItem(0).setIcon(drawables[4]);
        Dialog otpDialog = new Dialog(Home.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.post_creation_popup);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);



        getUserData = new GetUserData(FirebaseAuth.getInstance().getUid(), new InterfaceClass.LoadDataInterface() {
            @Override
            public void onWorkDone() {
                Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.FragmentView);
                NewsFeedFragment fragment = (NewsFeedFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);;
                fragment.loadData();
            }

            @Override
            public void onWorkNotDone() {

            }
        });



    }

boolean first = true;
    int currentIndex =0,lastSelected = 4;
    @Override
    protected void onStart() {
        super.onStart();
        if(first){
            navController = Navigation.findNavController(this, R.id.FragmentView);
            NavigationUI.setupWithNavController(binding.bottomnavigationview, navController);
            first = false;
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    int navId = navDestination.getId();
                        if(navId == R.id.newsFeedFragment){
                            currentIndex = 0;
                            changeIcon();
                        }else if(navId == R.id.discoverFragment) {
                            currentIndex = 1;
                            changeIcon();
                        }else if(navId == R.id.createFragment) {
                            currentIndex = 2;
                            changeIcon();
                        }else if(navId == R.id.accessMyFragment) {
                            currentIndex = 3;
                            changeIcon();
                        }else if(navId == R.id.profileFragment) {
                            currentIndex = 4;
                            changeIcon();
                        }
                }
            });
        }


    }

    private void changeIcon() {
        navigationView.getMenu().getItem(lastSelected).setIcon(drawables[lastSelected]);
        navigationView.getMenu().getItem(currentIndex).setIcon(drawables[currentIndex+5]);
        lastSelected = currentIndex;
    }


}