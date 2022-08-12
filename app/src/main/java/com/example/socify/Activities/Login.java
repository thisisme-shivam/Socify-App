package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;

import com.example.socify.R;
import com.example.socify.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    private  void setoncicklisteners(){
        binding.passwordshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpass();
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,VerificationActivity.class));
            }
        });

        binding.forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,VerificationActivity.class);
                intent.putExtra("id","Forgot Password");
                startActivity(intent);
            }
        });
    }
    int i =1;
    private void showpass(){
        if(i == 1) {
            binding.passwordshow.setImageResource(R.drawable.ic_eye_close);
            i=0;
        }else{
            binding.passwordshow.setImageResource(R.drawable.ic_eye);
            i=1;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setoncicklisteners();
//        TextPaint paint = binding.socify.getPaint();
//        float width = paint.measureText("SOCIFY");
//
//        Shader textShader = new LinearGradient(0, 0, width, binding.socify.getTextSize(),
//                new int[]{
//                        Color.parseColor("#000000"),
//                        Color.parseColor("#37AA9C"),
//                        Color.parseColor("#000000"),
//                }, null, Shader.TileMode.CLAMP);
//        binding.socify.getPaint().setShader(textShader);

    }
}