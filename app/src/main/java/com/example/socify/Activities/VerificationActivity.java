package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socify.R;


public class VerificationActivity extends AppCompatActivity {


    Button btn_get_otp;
    ImageView close_dialog, back_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verification);

        btn_get_otp = findViewById(R.id.getOtpButton);

        Dialog otpDialog = new Dialog(VerificationActivity.this);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(R.layout.otp_verification);
        otpDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        close_dialog = otpDialog.findViewById(R.id.close_icon);

        btn_get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpDialog.show();
            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpDialog.dismiss();
            }
        });
        
    }



}