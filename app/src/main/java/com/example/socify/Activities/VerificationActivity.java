package com.example.socify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.socify.R;
import com.example.socify.databinding.ActivityVerificationBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class VerificationActivity extends AppCompatActivity {

    ActivityVerificationBinding binding;
    Dialog otpDialog;
    Dialog progressDialog;
    ImageView close_dialog, back_btn;
    ProgressBar progressBar;
    FirebaseAuth fauth;
    String phonenumber;
    String vid;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks fcallbacks;

    private void setOnclicklistners(){


        binding.getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenumber = binding.phoneInput.getText().toString();
                if (phonenumber.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                else if (phonenumber.length() < 10) {
                    if (phonenumber.length() == 9)
                        Toast.makeText(getApplicationContext(), Integer.toString(10 - phonenumber.length()) + " digit is missing", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), Integer.toString(10 - phonenumber.length()) + " digits are missing", Toast.LENGTH_SHORT).show();
                }else{
                    sendotp();
                }
            }
        });
        close_dialog.setOnClickListener(view -> {
            otpDialog.dismiss();
            binding.phoneInput.requestFocus();

        });
        otpDialog.findViewById(R.id.otpSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PinView vie = (PinView) otpDialog.findViewById(R.id.otpInput);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vid,Objects.requireNonNull(vie.getText()).toString());

                fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),Registration.class));
                            finish();
                        }else
                            Toast.makeText(getApplicationContext(),"Failed to login" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        otpDialog.findViewById(R.id.resendText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendotp();
            }
        });


    }

    private void sendotp(){
        progressDialog.show();

        fcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                progressDialog.dismiss();
                otpDialog.show();
                vid = verificationId;
            }
        };


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(fauth)
                        .setPhoneNumber( "+" + binding.countryCode.getSelectedCountryCode() + phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(fcallbacks)          // OnVerificationStateChangedCallbacks
                        .build();


        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting instatnce of firebase
        fauth = FirebaseAuth.getInstance();
        // otp input dialog setup
        otpDialog = new Dialog(VerificationActivity.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.otp_verification);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        close_dialog = otpDialog.findViewById(R.id.close_icon);

        progressDialog = new Dialog(VerificationActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //setting on click listeners for required views
        setOnclicklistners();

        progressBar = (ProgressBar)progressDialog.findViewById(R.id.spin_kit);

        
    }



}