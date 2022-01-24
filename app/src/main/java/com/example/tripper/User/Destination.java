package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tripper.HelperClasses.apiController;
import com.example.tripper.HelperClasses.placeResponseModel;
import com.example.tripper.HelperClasses.rating.BarLabels;
import com.example.tripper.HelperClasses.rating.RatingReviews;
import com.example.tripper.HelperClasses.responseModelPlaces;
import com.example.tripper.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Destination extends AppCompatActivity {

    TextView placeDisplayId,placeTitleDisplay,bestVisitTime,budgetDisplay,descriptionDisplay;
    Button viewMapBtn;
    ImageView likeView;
    String placeId,modifiedDate,address,placeState;
    ImageView placeImageDisplay;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        placeDisplayId=findViewById(R.id.placeDisplyId);
        placeId=getIntent().getStringExtra("placeId");
        placeState=getIntent().getStringExtra("location");
        placeDisplayId.setText(placeId);
        placeTitleDisplay=findViewById(R.id.placeTitleDisplay);
        bestVisitTime=findViewById(R.id.bestTimeVisitDisplay);
        budgetDisplay=findViewById(R.id.budgetDisplay);
        descriptionDisplay=findViewById(R.id.descriptionDisplay);
        viewMapBtn=findViewById(R.id.viewMapBtn);
        likeView=findViewById(R.id.likeView);
        placeImageDisplay=findViewById(R.id.placeImageDisplay);
        constraintLayout=findViewById(R.id.constraintLayout);
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

        FetchData();

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratePlace();
            }
        });

        viewMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInMap();
            }
        });
    }

    private void openInMap() {
        Log.d("harsh", "openInMap: "+placeTitleDisplay.getText()+","+placeState);
        Uri mapUri;
        if (placeState==null) {
            mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(placeTitleDisplay.getText() + "," +address));
        }else{
            mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(placeTitleDisplay.getText() + "," + placeState));

        }
        Intent intent=new Intent(Intent.ACTION_VIEW,mapUri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void ratePlace() {
        Toast.makeText(getApplicationContext(), "Place rate is in Progress", Toast.LENGTH_SHORT).show();
    }

    private void FetchData() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Call<List<placeResponseModel>> call = DestinationController
                .getInstance()
                .getapi()
                .retrieve(placeId);
        call.enqueue(new Callback<List<placeResponseModel>>() {
            @Override
            public void onResponse(Call<List<placeResponseModel>> call, Response<List<placeResponseModel>> response) {
                List<placeResponseModel> placeDetails= response.body();
                placeTitleDisplay.setText(placeDetails.get(0).getTitle());
                bestVisitTime.setText(placeDetails.get(0).getVisitTime());
                budgetDisplay.setText("Rs. "+placeDetails.get(0).getBudget());
                descriptionDisplay.setText(placeDetails.get(0).getDescription());
                modifiedDate=placeDetails.get(0).getModifiedDate();
                address=placeDetails.get(0).getAddress();

                Glide.with(getApplicationContext()).load("http://192.168.1.32/tripper/images/"
                        + placeDetails.get(0).getPlaceImage()).into(placeImageDisplay);
            }

            @Override
            public void onFailure(Call<List<placeResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
    });
    }

    public void callBackScreen(View view) {
        Destination.super.onBackPressed();
    }

}