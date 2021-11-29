package com.example.tripper.Common.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.tripper.Databases.UserHelperClass;
import com.example.tripper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    TextView verifyOtpPhone;
    PinView pinFromUser;
    String codeBySystem;
    String phoneNo, fullName, username, email, password, date, gender,whatToDo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

         mAuth = FirebaseAuth.getInstance();
        pinFromUser = findViewById(R.id.pin_view);

        //Get all Data from Intent
        phoneNo = getIntent().getStringExtra("phoneNo");
        fullName = getIntent().getStringExtra("fullName");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        whatToDo=getIntent().getStringExtra("whatToDo");

        verifyOtpPhone=findViewById(R.id.verifyOtpPhone);
        verifyOtpPhone.setText("Enter one time password sent on "+phoneNo);
        sendVerificationCode(phoneNo);
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyOTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUsersData() {
        Intent intent=new Intent(getApplicationContext(),SetNewPassword.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUserData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser=new UserHelperClass(fullName,username,email,phoneNo,password,date,gender);
        reference.child(phoneNo).setValue(addNewUser);


    }

    public void callNextScreenFromOTP(View view) {
        String code = Objects.requireNonNull(pinFromUser.getText()).toString();
        if (!code.isEmpty()) {
            verifyCode(code);
            if (whatToDo.equals("updateData")){
                updateOldUsersData();
            }else{
                //storeNewUserData();
                Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this,Login.class);
                startActivity(intent);
            }
        }
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    }