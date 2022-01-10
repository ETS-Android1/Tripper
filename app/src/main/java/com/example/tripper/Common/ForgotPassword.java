package com.example.tripper.Common;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPassword extends AppCompatActivity {
    TextInputLayout phoneNumber_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        phoneNumber_forgotPassword = findViewById(R.id.phoneNumber_forgotPassword);


    }

    public void verifyPhoneNumber(View view) {
        String _phoneNumber = phoneNumber_forgotPassword.getEditText().getText().toString().trim();
        if (!validateFields(_phoneNumber)) {
            return;
        }
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }else if(_phoneNumber.charAt(0)=='+'){
            _phoneNumber=_phoneNumber.substring(3);
        }
        String fullPhoneNo = "+91"+ _phoneNumber;
        Log.d("harsh",fullPhoneNo);

        //Check whether user exists or not in database
        /*Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(fullPhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber_forgotPassword.setError(null);
                    phoneNumber_forgotPassword.setErrorEnabled(false);


                    finish();
                }else {
                    phoneNumber_forgotPassword.setError("No Such user exist");
                    phoneNumber_forgotPassword.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private boolean validateFields(String _phoneNumber) {
        if (_phoneNumber.isEmpty()) {
            phoneNumber_forgotPassword.setError("Phone Number should not be empty");
            return false;
        } else {
            return true;
        }
    }
}