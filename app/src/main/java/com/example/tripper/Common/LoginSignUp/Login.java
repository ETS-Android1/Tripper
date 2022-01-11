package com.example.tripper.Common.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripper.Common.ForgotPassword;
import com.example.tripper.Databases.LoginController;
import com.example.tripper.Databases.ResponseModel;
import com.example.tripper.Databases.SessionManager;
import com.example.tripper.LocationContributor.LocationContributorDashboard;
import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText phoneNumber_editText, password_editText;
    TextInputLayout phoneNumber, password;
    CheckBox rememberMe;
    ImageView backBtn;
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

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
        if (_phoneNumber.charAt(0) == '+') {
            _phoneNumber = _phoneNumber.substring(3);
        }
        String _completePhoneNumber = "+91" + _phoneNumber;

        if (rememberMe.isChecked()) {
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(_phoneNumber, _password);
        }
        loginProcess(_completePhoneNumber,_password);
    }

    private void loginProcess(String phoneNumber, String password) {
        Call<ResponseModel> call = LoginController
                .getInstance()
                .getApiSet()
                .verifyuser(phoneNumber, password);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
               ResponseModel obj=response.body();
               if(obj.getResult().equals(SUCCESS)){
                   Log.i("Harsh", obj.getUser().getFullName()+ obj.getUser().getUsername()+ obj.getUser().getEmail()+ obj.getUser().getPassword()+ obj.getUser().getDate()+ obj.getUser().getGender()+ obj.getUser().getPhoneNo());
                    SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                    sessionManager.createLoginSession(
                            obj.getUser().getFullName(),
                            obj.getUser().getUsername(),
                            obj.getUser().getEmail(),
                            obj.getUser().getPassword(),
                            obj.getUser().getDate(),
                            obj.getUser().getGender(),
                            obj.getUser().getPhoneNo());
                    startActivity(new Intent(getApplicationContext(), LocationContributorDashboard.class));
                    finish();
                }else if (obj.getResult().equals(FAILURE)){
                    Toast.makeText(getApplicationContext(), obj.getMessage(), Toast.LENGTH_SHORT).show();
                }

                }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }

        });
    }

        private boolean validateFields () {
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