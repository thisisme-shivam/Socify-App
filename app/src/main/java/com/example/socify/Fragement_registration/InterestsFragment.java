package com.example.socify.Fragement_registration;

import static android.graphics.BlendMode.COLOR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.Registration;
import com.example.socify.R;
import com.example.socify.databinding.FragmentInterestsBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import nl.bryanderidder.themedtogglebuttongroup.SelectAnimation;
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class InterestsFragment extends Fragment {

    FragmentInterestsBinding binding;
    public Registration registration;
    public void onclicklisteners() {
        binding.finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(100);
        binding.groupedtags.setSelectAnimation(SelectAnimation.CIRCULAR_REVEAL);
        onclicklisteners();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInterestsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}