package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripper.HelperClasses.rating.BarLabels;
import com.example.tripper.HelperClasses.rating.RatingReviews;
import com.example.tripper.R;

import java.util.Random;

public class Destination extends AppCompatActivity {

    TextView placeDisplayId,placeTitleDisplay,bestVisitTime,budgetDisplay,descriptionDisplay;
    Button ratePlaceBtn,viewMapBtn;
    ImageView likeView;
    String placeId,modifiedDate,address;
    ImageView placeImageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        placeDisplayId=findViewById(R.id.placeDisplyId);
        placeId=getIntent().getStringExtra("placeId");
        placeDisplayId.setText(placeId);
        placeTitleDisplay=findViewById(R.id.placeTitleDisplay);
        bestVisitTime=findViewById(R.id.bestTimeVisitDisplay);
        budgetDisplay=findViewById(R.id.budgetDisplay);
        descriptionDisplay=findViewById(R.id.descriptionDisplay);
        ratePlaceBtn=findViewById(R.id.ratePlaceBtn);
        viewMapBtn=findViewById(R.id.viewMapBtn);
        likeView=findViewById(R.id.likeView);
        placeImageDisplay=findViewById(R.id.placeImageDisplay);


        RatingReviews ratingReviews = (RatingReviews) findViewById(R.id.rating_reviews);

        Pair colors[] = new Pair[]{
                new Pair<>(Color.parseColor("#0c96c7"), Color.parseColor("#00fe77")),
                new Pair<>(Color.parseColor("#7b0ab4"), Color.parseColor("#ff069c")),
                new Pair<>(Color.parseColor("#fe6522"), Color.parseColor("#fdd116")),
                new Pair<>(Color.parseColor("#104bff"), Color.parseColor("#67cef6")),
                new Pair<>(Color.parseColor("#ff5d9b"), Color.parseColor("#ffaa69"))
        };

        int minValue = 30;

        int raters[] = new int[]{
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1),
                minValue + new Random().nextInt(100 - minValue + 1)
        };


        ratingReviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);



    }

    public void callBackScreen(View view) {
        Destination.super.onBackPressed();
    }
}