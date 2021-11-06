package com.example.tripper.Common.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripper.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

    ImageView backBtn;
    RadioGroup radioGroup;
    DatePicker datePicker;
    RadioButton selectedGender;

    String fullName,username,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2nd_class);

        backBtn = findViewById(R.id.signup_back_btn);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp2ndClass.super.onBackPressed();
            }
        });

        Intent intent=getIntent();
        fullName=intent.getStringExtra("fullName");
        username=intent.getStringExtra("Username");
        email=intent.getStringExtra("Email");
        password=intent.getStringExtra("Password");

    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userYear = datePicker.getYear();
        int difference = currentYear - userYear;

        if (difference < 14) {
            Toast.makeText(this, "Age should be more than 14years", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void callNextSignUpScreen(View view) {

        if (!validateAge() | !validateGender()) {
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        int day=datePicker.getDayOfMonth();
        int month=datePicker.getMonth();
        int year=datePicker.getYear();

        String date=day+"/"+month+"/"+year;
        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);
        intent.putExtra("gender",gender);
        intent.putExtra("dateOfBirth",date);
        intent.putExtra("fullName",fullName);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void callBackScreen(View view) {
        SignUp2ndClass.super.onBackPressed();
    }
}