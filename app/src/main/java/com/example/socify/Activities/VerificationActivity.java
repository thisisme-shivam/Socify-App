package com.example.socify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
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
    PhoneAuthProvider.ForceResendingToken tok;
    String vid;
    PhoneAuthOptions options;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks fcallbacks;

    private void setOnclicklistners(){
         InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

         otpDialog.setOnShowListener(dialogInterface -> {

             otpDialog.findViewById(R.id.otpInput).requestFocus();
             Window window = otpDialog.getWindow();
             window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
             window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

         });
        binding.getOtpButton.setOnClickListener(view -> {
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
        });
        close_dialog.setOnClickListener(view -> {
            otpDialog.dismiss();
            binding.phoneInput.requestFocus();

        });
        otpDialog.findViewById(R.id.otpSubmitButton).setOnClickListener(view -> {
            PinView vie = (PinView) otpDialog.findViewById(R.id.otpInput);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vid,Objects.requireNonNull(vie.getText()).toString());

            fauth.signInWithCredential(credential).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),Registration.class));
                    finish();
                }else
                    Toast.makeText(getApplicationContext(),"Failed to login" , Toast.LENGTH_SHORT).show();
            });
        });
        otpDialog.findViewById(R.id.resendText).setOnClickListener(view -> sendotp());


    }

    private void sendotp(){

        fcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(getApplicationContext(),"fjalskdjf",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Log.i("country code",binding.countryCode.getSelectedCountryCode()+phonenumber);

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
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(fcallbacks)
                        .setForceResendingToken(tok)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
        progressDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting instatnce of firebase
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fauth = FirebaseAuth.getInstance();
                // otp input dialog setup
                otpDialog = new Dialog(VerificationActivity.this);
                otpDialog.setCancelable(false);
                otpDialog.setContentView(R.layout.otp_verification);
                otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                close_dialog = otpDialog.findViewById(R.id.close_icon);
                otpDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


                binding.phoneInput.requestFocus();
                progressDialog = new Dialog(VerificationActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.progressdialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                //setting on click listeners for required views
                setOnclicklistners();

                progressBar = (ProgressBar) progressDialog.findViewById(R.id.spin_kit);
            }
        }, 0);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        otpDialog.dismiss();
    }
}