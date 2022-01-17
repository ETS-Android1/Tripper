package com.example.tripper.User;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripper.R;

public class UserPlaceInfo extends AppCompatActivity {

    TextView txtViewPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_place_info);
        txtViewPlace = findViewById(R.id.textViewPlace);
        txtViewPlace.setText(getIntent().getStringExtra("placeId"));


    }
}