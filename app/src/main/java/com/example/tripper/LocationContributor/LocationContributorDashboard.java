package com.example.tripper.LocationContributor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tripper.Databases.SessionManager;
import com.example.tripper.R;

import java.util.HashMap;

public class LocationContributorDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_contributor_dashboard);

        TextView textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetailFromSession=sessionManager.getUserDetailFromSession();
        String fullName=userDetailFromSession.get(SessionManager.KEY_FULLNAME);
        String phoneNumber=userDetailFromSession.get(SessionManager.KEY_PHONENUMBER);
        textView.setText(fullName+"\n"+phoneNumber);

    }
}