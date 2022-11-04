package com.example.socify.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socify.R;
import com.example.socify.RegistrationFragments.UserNameFragment;
import com.example.socify.databinding.FragmentNewPasswordBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPasswordFragment extends Fragment {

    FragmentNewPasswordBinding binding;
    String newPass, confirmPass;
    UserNameFragment userNameFragment = new UserNameFragment();

    void onClickListeners()
    {
        binding.newPassLayout.setEndIconOnClickListener(v -> showpass());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               newPass = binding.newPassword.getText().toString();


               if(newPass.isEmpty())
                   binding.newPassLayout.setError("Cannot be Empty");
               else if(newPass.length() < 8){
                   binding.newPassLayout.setError("should be at least 8 characters");
               }
               else if(!checkstrength(newPass))
                   binding.newPassLayout.setError("Password should contain atleast\n" +
                           "1 Uppercase and 1 Lowercase Letter \n" +
                           "1 number \n" +
                           "1 special character\n" +
                           "and no spaces");
               else
                   Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkstrength(String pass) {
        Pattern p = Pattern.compile("^(?=(.*[a-z])+)(?=(.*[A-Z])+)(?=(.*[0-9])+)(?=(.*[!@#$%^&*()\\-_+.])+)(?=\\S+$).{8,20}$");
        Matcher m = p.matcher(pass);
        return m.find();
    }


    int i =1, j = 1;
    private void showpass() {
        if (i == 1) {
            binding.newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            binding.newPassLayout.setEndIconDrawable(R.drawable.ic_eye_close);
            i = 0;
        } else {
            binding.newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.newPassLayout.setEndIconDrawable(R.drawable.ic_eye);
            i = 1;
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.newPassLayout.isErrorEnabled())
                    binding.newPassLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewPasswordBinding.inflate(inflater, container, false);

        onClickListeners();

        return binding.getRoot();
    }
}