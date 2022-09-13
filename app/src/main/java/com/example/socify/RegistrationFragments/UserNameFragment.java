package com.example.socify.RegistrationFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.PatternMatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Activities.Registration;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.R;
import com.example.socify.databinding.FragmentNameFragementBinding;

import org.intellij.lang.annotations.RegExp;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.RegEx;


public class UserNameFragment extends Fragment {

    FragmentNameFragementBinding binding;
    String username, Password;
    SendProfileData sendProfileData = new SendProfileData();

    public void onclicklisteners() {
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FieldValidation()) {
                    Registration.details.setUsername(username);
                    Registration.details.setPassword(Password);
                    Log.i("Name", Registration.details.getName());
                    Log.i("YOP", Registration.details.getPassyear());
                    Log.e("username", username);

                    //Uploading username & Password and mapping username with phone number
                    sendProfileData.sendUsername();
                    sendProfileData.sendPassword();
                    sendProfileData.mapUserwithPhone(username);
                    Registration.fragment_curr_pos++;
                    getParentFragmentManager().beginTransaction().replace(R.id.frame_registration, Registration.getCollegeFragment).commit();
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
        Pattern p = Pattern.compile("^(?=(.*[a-z])+)(?=(.*[A-Z])+)(?=(.*[0-9])+)(?=(.*[!@#$%^&*()\\-_+.])+)(?=\\S+$).{6,}$");
        Matcher m = p.matcher(Password);
        return m.find();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(40);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNameFragementBinding.inflate(inflater, container, false);
        onclicklisteners();
        if(!Objects.equals(Registration.details.getImgUri(), "No Image")) {
            binding.ProfilePic.setImageURI(Uri.parse(Registration.details.getImgUri()));
        }
        else{
            binding.picadded.setText("No Image Selected");
        }

        return binding.getRoot();
    }

}