package com.example.tripper.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.tripper.R;

public class Login extends AppCompatActivity {

    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contributor_login);


        backBtn=findViewById(R.id.login_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.super.onBackPressed();
            }
        });


    }

    public void callSignUpScreen(View view){
        Intent intent=new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

    public void forgotPasswordScreen(View view) {
        Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
        startActivity(intent);
    }
}