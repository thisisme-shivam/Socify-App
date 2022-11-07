package com.example.socify.RegistrationFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.socify.Activities.Registration;
import com.example.socify.Activities.VerificationActivity;
import com.example.socify.InterfaceClass;
import com.example.socify.R;
import com.example.socify.databinding.FragmentInterestsBinding;

import java.util.ArrayList;

import nl.bryanderidder.themedtogglebuttongroup.SelectAnimation;

public class InterestsFragment extends Fragment {

    ArrayList<String> tags;
    FragmentInterestsBinding binding;
    Registration regActivity;
    Dialog progressDialog;
    public void onclicklisteners() {

        NavController controller = Navigation.findNavController(getView());

         binding.finishbtn.setOnClickListener(v -> {
                     //Getting text from tapped tags
             if (tags.isEmpty() || tags.size() < 3)
                 Toast.makeText(getContext(), "Select atleast 3 tag ", Toast.LENGTH_SHORT).show();
             else
                 sendData();


         });

         binding.groupedtags.setOnSelectListener(themedButton -> {
             tags.add(themedButton.getText());
             return null;
         });

        requireActivity().findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = InterestsFragmentDirections.actionInterestsFragmentToCoursesFragment();
                controller.navigate(action);
            }
        });
    }

    private void sendData() {

        progressDialog.show();
        Log.i("entering", "true");
        regActivity.tagMap.put("Tags", tags);
        //Uploading Tags
        regActivity.registerUser(new InterfaceClass.InterestInterface() {
            @Override
            public void onWorkDone() {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onWorkNotDone() {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Sorry something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActivity = (Registration) getActivity();
        progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.register_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(100);
        tags = new ArrayList<>();
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