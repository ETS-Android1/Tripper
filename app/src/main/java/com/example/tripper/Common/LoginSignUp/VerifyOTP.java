package com.example.tripper.Common.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.tripper.Databases.UserHelperClass;
import com.example.tripper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    TextView verifyOtpPhone;
    PinView pinFromUser;
    String codeBySystem;
    String phoneNo, fullName, username, email, password, date, gender,whatToDo;
    private FirebaseAuth mAuth;
    PhoneAuthCredential credential;
    String otpCode = "123456";
    String verificationId;
    Boolean verificationOnProgress = false;
    PhoneAuthProvider.ForceResendingToken token;
    ImageButton resend;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        resend=findViewById(R.id.reset);
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
        if(!verificationOnProgress){
                requestPhoneAuth(phoneNo);
            }

        credential = PhoneAuthProvider.getCredential(verificationId,otpCode);
        verifyAuth(credential);
            }

    private void requestPhoneAuth(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60L, TimeUnit.SECONDS,this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        Toast.makeText(getApplicationContext(), "OTP Timeout, Please Re-generate the OTP Again.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                        token = forceResendingToken;
                        verificationOnProgress = true;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        // called if otp is automatically detected by the app
                        verifyAuth(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void verifyAuth(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Phone Verified."+mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    checkUserProfile();
                }else {
                    Toast.makeText(getApplicationContext(), "Can not Verify phone and Create Account.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            checkUserProfile();
        }
    }

    private void checkUserProfile() {
        DocumentReference docRef = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Toast.makeText(getApplicationContext(),"User Already Exist",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }else {
                    storeNewUserData();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Profile Do Not Exists", Toast.LENGTH_SHORT).show();
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