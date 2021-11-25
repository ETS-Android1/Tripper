package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tripper.R;

public class PlaceCatalogue extends AppCompatActivity {

    TextView placeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_catalogue);
        placeView=findViewById(R.id.PlaceText);

        String category = getIntent().getStringExtra("PLACE_CATEGORY");
        placeView.setText(category);
    }
}