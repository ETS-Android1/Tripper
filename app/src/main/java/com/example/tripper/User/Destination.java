package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tripper.R;

public class Destination extends AppCompatActivity {

    TextView placeDisplayId;
    String placeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        placeDisplayId=findViewById(R.id.placeDisplyId);
        placeId=getIntent().getStringExtra("placeId");
        placeDisplayId.setText(placeId);
    }
}