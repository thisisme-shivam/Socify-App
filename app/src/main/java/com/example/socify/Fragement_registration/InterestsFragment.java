package com.example.socify.Fragement_registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.R;
import com.example.socify.databinding.FragmentInterestsBinding;
import com.google.android.material.button.MaterialButton;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;

public class InterestsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(30);

//        MaterialButton btn = view.findViewById(R.id.refresh);
//        SlideToActView sta = (SlideToActView) view.findViewById(R.id.tag1);
//        SlideToActView sta1 = (SlideToActView) view.findViewById(R.id.tag2);
//        SlideToActView sta2 = (SlideToActView) view.findViewById(R.id.tag3);
//        SlideToActView sta3 = (SlideToActView) view.findViewById(R.id.tag4);
//        SlideToActView sta4 = (SlideToActView) view.findViewById(R.id.tag5);
//        SlideToActView sta5 = (SlideToActView) view.findViewById(R.id.tag6);
//        SlideToActView sta6 = (SlideToActView) view.findViewById(R.id.tag7);
//        SlideToActView sta7 = (SlideToActView) view.findViewById(R.id.tag8);
//
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(sta.isCompleted() || sta1.isCompleted() || sta2.isCompleted() || sta3.isCompleted() || sta4.isCompleted() || sta5.isCompleted() || sta6.isCompleted() || sta7.isCompleted()) {
//                    sta.resetSlider();
//                    sta1.resetSlider();
//                    sta2.resetSlider();
//                    sta3.resetSlider();
//                    sta4.resetSlider();
//                    sta5.resetSlider();
//                    sta6.resetSlider();
//                    sta7.resetSlider();
//                }
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interests, container, false);
    }
}