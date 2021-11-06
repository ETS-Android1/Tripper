package com.example.tripper.Common.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {

    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd_class);

        scrollView = findViewById(R.id.scrollView);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
    }

    public void callBackScreen(View view) {
        SignUp3rdClass.super.onBackPressed();
    }

    public void callVerifyOTPScreen(View view) {
        if (!validatePhoneNumber()) {
            return;
        }
        String fullName = getIntent().getStringExtra("fullName");
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String birthDate = getIntent().getStringExtra("dateOfBirth");
        String gender = getIntent().getStringExtra("gender");

        String getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + getUserEnteredPhoneNumber;
        Log.d("Harsh",phoneNo);

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        intent.putExtra("fullName", fullName);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("BirthDate", birthDate);
        intent.putExtra("gender", gender);
        intent.putExtra("phoneNo", phoneNo);
        startActivity(intent);

    }

    private boolean validatePhoneNumber() {
        String phone = phoneNumber.getEditText().getText().toString().trim();
        String Regex = "[6-9][0-9]{10}";
        if (phone.matches(Regex)) {
            phoneNumber.setError("Field cannot be empty");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}