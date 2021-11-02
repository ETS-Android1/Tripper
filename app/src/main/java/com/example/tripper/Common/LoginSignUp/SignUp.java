package com.example.tripper.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripper.R;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_contributor_sign_up);

        backBtn=findViewById(R.id.signup_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.super.onBackPressed();
            }
        });
    }



    public void callNextSignUpScreen(View view){
        Intent intent=new Intent(getApplicationContext(),SignUp2ndClass.class);
            startActivity(intent);
        }
        public  void callLoginScreen(View view){
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }
}