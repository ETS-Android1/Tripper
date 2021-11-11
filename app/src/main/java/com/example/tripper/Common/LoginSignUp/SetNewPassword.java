package com.example.tripper.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPassword,reTypePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        newPassword=findViewById(R.id.new_password);
        reTypePassword=findViewById(R.id.new_repassword);
    }

    public void setNewPasswordBtn(View view) {
        if(!validatePassword() | !validateConfirmPassword()){
            return;
        }

        //Get data from fields
        String _newPassword=newPassword.getEditText().getText().toString().trim();
        String _phoneNumber=getIntent().getStringExtra("phoneNo");

        //Update Data in Firestore and in Sessions
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(),ForgotPasswordSuccessMessage.class));
        finish();
    }

    private boolean validateConfirmPassword() {
        String _newPassword=newPassword.getEditText().getText().toString().trim();
        String _reNewPassword=reTypePassword.getEditText().getText().toString().trim();
        if(_newPassword.equals(_reNewPassword)){
            return true;
        }else{
            return false;
        }

    }

    private boolean validatePassword() {
        String _newPassword=newPassword.getEditText().toString().trim();
        if (_newPassword.isEmpty() || _newPassword.length()<6){
            return false;
        }else{
            return true;
        }
    }
}