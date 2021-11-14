package com.example.tripper.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tripper.Databases.SessionManager;
import com.example.tripper.LocationContributor.LocationContributorDashboard;
import com.example.tripper.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText phoneNumber_editText, password_editText;
    TextInputLayout phoneNumber, password;
    CheckBox rememberMe;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contributor_login);
        //hooks
        phoneNumber = findViewById(R.id.phoneNoLogin);
        password = findViewById(R.id.passwordLogin);
        rememberMe = findViewById(R.id.remember_me);
        phoneNumber_editText = findViewById(R.id.phoneNumber_editText);
        password_editText = findViewById(R.id.password_editText);

        //Check whether phone number and password is already saved in Shared Preference or not
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNumber_editText.setText(rememberMeDetails.get(SessionManager.KEY_SESSSIONPHONENUMBER));
            password_editText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));

        }


        backBtn = findViewById(R.id.login_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.super.onBackPressed();
            }
        });


    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void forgotPasswordScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(intent);
    }

    public void letUserLogin(View view) {
        if (!validateFields()) {
            return;
        }

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        if(_phoneNumber.charAt(0)=='+'){
            _phoneNumber = _phoneNumber.substring(3);
        }
        String _completePhoneNumber = "+91" + _phoneNumber;

        if (rememberMe.isChecked()) {
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(_phoneNumber, _password);
        }


        // check Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    //Get users data from firebase
                    String _username = snapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                    String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    String _fullName = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                    String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                    String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                    String _gender = snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);
                    String _dateOfBirth = snapshot.child(_phoneNo).child("date").getValue(String.class);

                    //Session
                    SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                    sessionManager.createLoginSession(_fullName, _username, _email, _phoneNo, _password, _dateOfBirth, _gender);


                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);
                        startActivity(new Intent(getApplicationContext(), LocationContributorDashboard.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No such user exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().toString().trim();
        String _password = password.getEditText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone Number should not be empty");
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            return true;
        }
    }
}