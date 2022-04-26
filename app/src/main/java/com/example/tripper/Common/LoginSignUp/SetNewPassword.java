package com.example.tripper.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tripper.Databases.LoginController;
import com.example.tripper.Databases.ResponseModel;
import com.example.tripper.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPassword,reTypePassword;
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
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
        setNewPassword(_newPassword,_phoneNumber);
    }

    private void setNewPassword(String newPassword, String phoneNumber) {
        Call<ResponseModel> call = LoginController
                .getInstance()
                .getApiSet()
                .resetPassword(phoneNumber, newPassword);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj=response.body();
                if(obj.getResult().equals(SUCCESS)) {
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),ForgotPasswordSuccessMessage.class));
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