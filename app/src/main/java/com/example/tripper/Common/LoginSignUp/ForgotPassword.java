package com.example.tripper.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tripper.R;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void MakeSelectionScreen(View view) {
        Intent intent =new Intent(getApplicationContext(),MakeSelection.class);
        startActivity(intent);
    }
}