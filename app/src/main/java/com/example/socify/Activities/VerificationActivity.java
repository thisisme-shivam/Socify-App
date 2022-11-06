package com.example.socify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.socify.R;
import com.example.socify.databinding.ActivityVerificationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class VerificationActivity extends AppCompatActivity {

    ActivityVerificationBinding binding;
    Dialog otpDialog;
    Dialog progressDialog;
    FloatingActionButton closeDialog;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String phonenumber;
    PhoneAuthProvider.ForceResendingToken tok;
    String vid;
    PinView vie;
    PhoneAuthOptions options;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks fcallbacks;
    Window window;

    private void setOnclicklistners(){


         otpDialog.setOnShowListener(dialogInterface -> {

             otpDialog.findViewById(R.id.otpInput).requestFocus();
             window = otpDialog.getWindow();
             window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
             window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
         });
         
        binding.getOtpButton.setOnClickListener(view -> {
            phonenumber = binding.phoneInput.getText().toString();
            if (phonenumber.isEmpty()){
                assert binding.errorbox != null;
                binding.errorbox.setText("Phone number field is empty");
                binding.errorbox.setVisibility(View.VISIBLE);
            }
            else if (phonenumber.length() < 10) {
                    assert binding.errorbox != null;
                    binding.errorbox.setText("Phone number invalid");
                    binding.errorbox.setVisibility(View.VISIBLE);
            }else{
                verifyNumber();
            }
        });
        closeDialog.setOnClickListener(view -> {
            otpDialog.dismiss();
            binding.phoneInput.requestFocus();

        });
        otpDialog.findViewById(R.id.otpSubmitButton).setOnClickListener(view -> {
            if(validateotp()) {

                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vid, Objects.requireNonNull(vie.getText()).toString());
                progressDialog.setContentView(R.layout.progressbarlayoutsignin);
                progressDialog.show();
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), Registration.class).putExtra("PhoneNumber",phonenumber));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Otp", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                });
            }
        });
        otpDialog.findViewById(R.id.resendText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp();
                progressDialog.show();
            }
        });


    }

    private void verifyNumber() {
        Log.i("phonenumber",phonenumber);

        progressDialog.show();

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(phonenumber+"@gmail.com").addOnSuccessListener(new OnSuccessListener<SignInMethodQueryResult>() {
            @Override
            public void onSuccess(SignInMethodQueryResult signInMethodQueryResult) {

                int size = signInMethodQueryResult.getSignInMethods().size();
                Log.i("size", String.valueOf(size));
                if(size == 1) {
                    Toast.makeText(getApplicationContext(), "Phone already Registered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else
                    sendOtp();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sorry something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateotp() {
        if(vie.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "please enter otp", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(vie.getText().toString().length() <6 ){
            Toast.makeText(getApplicationContext(),"Otp Incomplete",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void sendOtp(){
        fcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sorry something went wrong",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                progressDialog.dismiss();
                otpDialog.show();
                vid = verificationId;
                tok =token;
            }
        };
        options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber( "+" + binding.countryCode.getSelectedCountryCode() + phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(fcallbacks)
                        .setForceResendingToken(tok)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting instatnce of firebase


        firebaseAuth = FirebaseAuth.getInstance();
        otpDialog = new Dialog(VerificationActivity.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.otp_verification);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        otpDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        closeDialog = otpDialog.findViewById(R.id.close_icon);
        setOnclicklistners();

        progressDialog = new Dialog(VerificationActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progressdialogotp);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar = (ProgressBar) progressDialog.findViewById(R.id.spin_kit);
        binding.phoneInput.requestFocus();
        vie =  otpDialog.findViewById(R.id.otpInput);

        binding.phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.errorbox.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        otpDialog.dismiss();
        progressDialog.dismiss();
    }
}