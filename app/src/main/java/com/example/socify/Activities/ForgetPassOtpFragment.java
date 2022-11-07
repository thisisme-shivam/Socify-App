package com.example.socify.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.socify.R;
import com.example.socify.databinding.FragmentForgetPassOtpBinding;
import com.example.socify.databinding.FragmentMyClubBinding;
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

public class ForgetPassOtpFragment extends Fragment {

    FragmentForgetPassOtpBinding binding;
    FloatingActionButton closeOtpDialog;
    Dialog otpDialog;
    Dialog progressDialog;
    FloatingActionButton closeDialog;
    ProgressBar progressBar;
    FirebaseAuth fauth;
    String phonenumber;
    PhoneAuthProvider.ForceResendingToken tok;
    String vid;
    PinView vie;
    PhoneAuthOptions options;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks fcallbacks;
    Window window;

    void onClickListeners()
    {
        otpDialog.setOnShowListener(dialogInterface -> {

            otpDialog.findViewById(R.id.otpInput).requestFocus();
            window = otpDialog.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        });

        binding.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenumber = binding.phoneNumber.getText().toString();

                if (phonenumber.isEmpty()){
                    assert binding.errorText != null;
                    binding.errorText.setText("Phone number field is empty");
                    binding.errorText.setVisibility(View.VISIBLE);
                }
                else if (phonenumber.length() < 10) {
                    assert binding.errorText != null;
                    binding.errorText.setText("Phone number invalid");
                    binding.errorText.setVisibility(View.VISIBLE);
                }else{
                    verifyNumber();
                }
            }
        });

        closeDialog.setOnClickListener(v -> {    //closing OTP Dialog
            otpDialog.dismiss();
            binding.phoneNumber.requestFocus();
        });



        otpDialog.findViewById(R.id.otpSubmitButton).setOnClickListener(view -> {
            if(validateotp()) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vid, Objects.requireNonNull(vie.getText()).toString());
                progressDialog.setContentView(R.layout.progressbarlayoutsignin);
                progressDialog.show();
                fauth.signInWithCredential(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        otpDialog.dismiss();
                        progressDialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.forgetPassFrame, new NewPasswordFragment()).commit();
                    } else {
                        Toast.makeText(getContext(), "Incorrect Otp", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }

                });
            }
        });


        otpDialog.findViewById(R.id.resendText).setOnClickListener(view -> sendotp());


    }






    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    private boolean validateotp() {
        if(vie.getText().toString().isEmpty())
            Toast.makeText(getActivity(),"please enter otp",Toast.LENGTH_SHORT).show();
        return true;
    }

    private void verifyNumber() {
        Log.i("phonenumber",phonenumber);

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(phonenumber+"@gmail.com").addOnSuccessListener(new OnSuccessListener<SignInMethodQueryResult>() {
            @Override
            public void onSuccess(SignInMethodQueryResult signInMethodQueryResult) {

                int size = signInMethodQueryResult.getSignInMethods().size();
                Log.i("size", String.valueOf(size));
                if(size == 1)
                    sendotp();
                else
                Toast.makeText(getActivity(),"Phone is not Registered",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Sorry something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendotp(){


        fcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Sorry something went wrong",Toast.LENGTH_SHORT).show();
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
                PhoneAuthOptions.newBuilder(fauth)
                        .setPhoneNumber( "+" + binding.countryCode.getSelectedCountryCode() + phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this.getActivity())                 // Activity (for callback binding)
                        .setCallbacks(fcallbacks)
                        .setForceResendingToken(tok)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
        progressDialog.show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPassOtpBinding.inflate(inflater, container, false);

        fauth = FirebaseAuth.getInstance();
        otpDialog = new Dialog(this.getActivity());
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.otp_verification);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        otpDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        closeDialog = otpDialog.findViewById(R.id.close_icon);
        onClickListeners();

        progressDialog = new Dialog(this.getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progressdialogotp);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar = (ProgressBar) progressDialog.findViewById(R.id.spin_kit);
        binding.phoneNumber.requestFocus();
        vie =  otpDialog.findViewById(R.id.otpInput);
        return binding.getRoot();
    }
}