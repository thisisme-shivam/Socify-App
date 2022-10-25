package com.example.socify.RegistrationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Registration;
import com.example.socify.R;
import com.example.socify.databinding.FragmentNameFragementBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserNameFragment extends Fragment {

    FragmentNameFragementBinding binding;
    String username, Password;
    Registration regActivity;
    GetCollegeFragment getCollegeFragment;
    public void onclicklisteners() {
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FieldValidation()) {

                    regActivity.profiledetails.put("Username",username);
                    regActivity.setPassword(Password);
                    //Uploading username & Password and mapping username with phone number
                    getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, getCollegeFragment).commit();
                }
            }
        });
    }

    public boolean FieldValidation() {
        username = (Objects.requireNonNull(binding.usernametext.getText())).toString().trim();
        Password = (Objects.requireNonNull(binding.passwordtext.getText())).toString();
        if(binding.usernametextlayout.isErrorEnabled())
            binding.usernametextlayout.setErrorEnabled(false);
        if(username.isEmpty())
            binding.usernametextlayout.setError("Username cannot be empty");
        else if(username.matches("[0-9]+"))
            binding.usernametextlayout.setError("username cannot contain only numbers");
        else if (username.length()<3)
            binding.usernametextlayout.setError("username should be greater than 3 character");
        else if (Password.isEmpty())
            binding.passwordtextlayout.setError("Password cannot be empty");
        else if(Password.length() < 6)
            binding.passwordtextlayout.setError("Password cannot be less than 6 characters");
        else if(!checkstrength()) {
            binding.passwordtextlayout.setError("Password should contain atleast\n" +
                    "1 Uppercase and 1 Lowercase Letter \n" +
                    "1 number \n" +
                    "1 special character\n" +
                    "and no spaces");
        }else
            return true;
        return false;
    }

    private boolean checkstrength() {
        Pattern p = Pattern.compile("^(?=(.*[a-z])+)(?=(.*[A-Z])+)(?=(.*[0-9])+)(?=(.*[!@#$%^&*()\\-_+.])+)(?=\\S+$).{8,20}$");
        Matcher m = p.matcher(Password);
        return m.find();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActivity = (Registration) getActivity();
        getCollegeFragment  = new GetCollegeFragment();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(40);

        onclicklisteners();
        if(regActivity.uri !=null) {
            binding.ProfilePic.setImageURI(regActivity.uri);
        }
        else{
            binding.picadded.setText("No Image Added");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNameFragementBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

}