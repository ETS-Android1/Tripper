package com.example.tripper.LocationContributor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;
import com.example.tripper.User.UserDashboard;

import java.util.HashMap;

public class ContributorProfileFragment extends Fragment {

    TextView profile_FullName,profile_email,profile_phoneNumber,profile_username,profile_gender,profile_dateOfBirth;
    Button editProfile;
    ImageView logoutUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contributor_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutUser=view.findViewById(R.id.logoutUser);
        profile_FullName=view.findViewById(R.id.profile_textView_fullName);
        profile_email=view.findViewById(R.id.profile_textView_email);
        profile_phoneNumber=view.findViewById(R.id.profile_textView_mobileNo);
        profile_username=view.findViewById(R.id.profile_textView_username);
        profile_gender=view.findViewById(R.id.profile_textView_gender);
        profile_dateOfBirth=view.findViewById(R.id.profile_textView_dateOfBirth);

        SessionManager sessionManager=new SessionManager(getActivity(),SessionManager.SESSION_USERSESSION);


        HashMap<String,String> userDetails=sessionManager.getUserDetailFromSession();

        String fullName=userDetails.get(SessionManager.KEY_FULLNAME);
        String email=userDetails.get(SessionManager.KEY_EMAIL);
        String phoneNumber=userDetails.get(SessionManager.KEY_PHONENUMBER);
        String username=userDetails.get(SessionManager.KEY_USERNAME);
        String gender=userDetails.get(SessionManager.KEY_GENDER);
        String dateOfBirth=userDetails.get(SessionManager.KEY_DATE);

        profile_FullName.setText(fullName);
        profile_email.setText(email);
        profile_phoneNumber.setText(phoneNumber);
        profile_username.setText(username);
        profile_gender.setText(gender);
        profile_dateOfBirth.setText(dateOfBirth);

        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUserFromSession();
                startActivity(new Intent(getActivity(), UserDashboard.class));
                getActivity().finish();
            }
        });

    }
}