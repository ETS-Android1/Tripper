package com.example.tripper.LocationContributor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tripper.Common.LoginSignUp.ForgotPasswordSuccessMessage;
import com.example.tripper.Common.LoginSignUp.Login;
import com.example.tripper.Common.LoginSignUp.SignUp2ndClass;
import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;
import com.example.tripper.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ContributorProfileFragment extends Fragment {

    TextInputLayout profile_email,profile_fullNameEdit,profile_username,profile_gender,profile_dateOfBirth;
    TextView profile_FullName,username_field;
    Button editProfile,updateProfile;
    private static final String url="http://192.168.43.170/tripper/profile_update.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contributor_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile_FullName=view.findViewById(R.id.profile_textView_fullName);
        profile_email=view.findViewById(R.id.profile_textView_email);
        profile_fullNameEdit=view.findViewById(R.id.profile_editText_fullName);
        profile_username=view.findViewById(R.id.profile_textView_username);
        profile_gender=view.findViewById(R.id.profile_textView_gender);
        profile_dateOfBirth=view.findViewById(R.id.profile_textView_dateOfBirth);
        username_field=view.findViewById(R.id.username_field);
        editProfile=view.findViewById(R.id.edit_profile);
        updateProfile=view.findViewById(R.id.update_profile);

        SessionManager sessionManager=new SessionManager(getActivity(),SessionManager.SESSION_USERSESSION);


        HashMap<String,String> userDetails=sessionManager.getUserDetailFromSession();

        String fullName=userDetails.get(SessionManager.KEY_FULLNAME);
        String email=userDetails.get(SessionManager.KEY_EMAIL);
        String fullNameEdit=userDetails.get(SessionManager.KEY_FULLNAME);
        String username=userDetails.get(SessionManager.KEY_USERNAME);
        String gender=userDetails.get(SessionManager.KEY_GENDER);
        String dateOfBirth=userDetails.get(SessionManager.KEY_DATE);
        String phone=userDetails.get(SessionManager.KEY_PHONENUMBER);


        profile_FullName.setText(fullName);
        profile_email.getEditText().setText(email);
        profile_fullNameEdit.getEditText().setText(fullNameEdit);
        profile_username.getEditText().setText(username);
        profile_gender.getEditText().setText(gender);
        profile_dateOfBirth.getEditText().setText(dateOfBirth);
        username_field.setText(username);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_email.getEditText().setEnabled(true);
                profile_fullNameEdit.getEditText().setEnabled(true);
                profile_username.getEditText().setEnabled(true);
                profile_gender.getEditText().setEnabled(true);
                profile_dateOfBirth.getEditText().setEnabled(true);
                editProfile.setVisibility(View.INVISIBLE);
                updateProfile.setVisibility(View.VISIBLE);
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= profile_email.getEditText().getText().toString();
                String fullName=profile_fullNameEdit.getEditText().getText().toString();
                String username= profile_username.getEditText().getText().toString();
                String gender=profile_gender.getEditText().getText().toString();
                String birthdate=profile_dateOfBirth.getEditText().getText().toString();
                String uid=userDetails.get(SessionManager.KEY_USERID);

                //DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Uscers");


                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        if(response.equals("Profile Updated Succesfully")){
                            Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("fullName",fullName);
                        map.put("username",username);
                        map.put("email",email);
                        map.put("birthdate",birthdate);
                        map.put("gender",gender);
                        map.put("uid",uid);
                        return map;
                    }
                };

                RequestQueue queue= Volley.newRequestQueue(getContext());
                queue.add(request);
                Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });

    }
}