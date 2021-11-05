package com.example.tripper.Common.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;

    //variables
    TextInputLayout fullName, userName, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_contributor_sign_up);

        backBtn = findViewById(R.id.signup_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.super.onBackPressed();
            }
        });

        //Hooks for getting data
        fullName = findViewById(R.id.signup_fullname);
        userName = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }


    private boolean validateFullName() {
        String value = fullName.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            fullName.setError("Field cannot be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String value = userName.getEditText().getText().toString().trim();
        String RegularExpression = "\\A\\w{1,20}\\z";

        if (value.isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        } else if (value.length() > 20) {
            userName.setError("Username should be between 1-20");
            return false;
        } else if (!value.matches(RegularExpression)) {
            userName.setError("No white spaces allowed");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String value = email.getEditText().getText().toString().trim();
        String RegularExpression = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        if (value.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(RegularExpression)) {
            email.setError("Email is invalid");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String value = password.getEditText().getText().toString().trim();
        String RegularExpression = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{4,}$";

        if (value.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(RegularExpression)) {
            password.setError("Password should contain 1 digit, 1 special character and should have 4 letters.");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void callNextSignUpScreen(View view) {

        if(!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);
        intent.putExtra("fullName",fullName.getEditText().getText().toString().trim());
        intent.putExtra("Username",userName.getEditText().getText().toString().trim());
        intent.putExtra("Email",email.getEditText().getText().toString().trim());
        intent.putExtra("Password",password.getEditText().getText().toString().trim());
        startActivity(intent);
    }




    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}