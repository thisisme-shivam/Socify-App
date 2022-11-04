package com.example.socify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.socify.R;
import com.example.socify.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    String username ="",password="";
    FirebaseAuth auth;
    Dialog progressDialog;

    private  void setListeners(){
        binding.passwordshow.setOnClickListener(v -> showpass());

        binding.signup.setOnClickListener(v -> startActivity(new Intent(Login.this,VerificationActivity.class)));

        binding.forgotPass.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });


        binding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.setErrorUsername.getVisibility() == View.VISIBLE)
                    binding.setErrorUsername.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.setErrorPassword.getVisibility() == View.VISIBLE)
                    binding.setErrorPassword.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void loginUser() {
        if(validation()){
            if(checkifnumber()) {
                username = username + "@gmail.com";
                loginbyPhone();
            }
            else
                loginbyusername();
        }
    }

    private void loginbyusername() {
        progressDialog.show();
        DocumentReference doc = FirebaseFirestore.getInstance().collection("MapPhoneUsername").document(username);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()) {
                    username = task.getResult().getString(username) + "@gmail.com";
                    loginbyPhone();
                }else{
                    binding.setErrorUsername.setText("User dosen't exists");
                    binding.setErrorUsername.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void loginbyPhone() {
        progressDialog.show();
        auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
                else {
                    binding.setErrorUsername.setText("Wrong Credentials");
                    binding.setErrorUsername.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }
        });
    }

    private boolean checkifnumber() {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(username);
        return m.matches();
    }

    private boolean validation() {
        username = Objects.requireNonNull(binding.username.getText()).toString().trim();
        password = Objects.requireNonNull(binding.password.getText()).toString();

        if(username.isEmpty()) {
            binding.setErrorUsername.setText("Required field cannot be empty");
            binding.setErrorUsername.setVisibility(View.VISIBLE);
        }
        else if(password.isEmpty()){
            binding.setErrorPassword.setText("Password cannot be empty");
            binding.setErrorPassword.setVisibility(View.VISIBLE);
        }else if (password.length()<8){
            binding.setErrorPassword.setText("Password must have 8 characters");
            binding.setErrorPassword.setVisibility(View.VISIBLE);
        }else
            return true;

        return false;
    }

    int i =1;
    private void showpass(){
        if(i == 1) {
            binding.password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            binding.passwordshow.setImageResource(R.drawable.hide_pass_icon);
            i=0;
        }else{
            binding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.passwordshow.setImageResource(R.drawable.show_pass_icon);
            i=1;
        }
        binding.password.setSelection(binding.password.getText().length());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        auth = FirebaseAuth.getInstance();

        progressDialog = new Dialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progressbarlayoutsignin);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}