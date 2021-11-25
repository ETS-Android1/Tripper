package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.tripper.R;

public class AllCategories extends AppCompatActivity {

    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_categoires);

        //Hooks
        backBtn=findViewById(R.id.back_pressed);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategories.super.onBackPressed();
            }
        });
    }

    public void callCities(View view) {
        String category="Town & Cities";
        callCatalogue(category);
    }

    public void callIsland(View view) {
        String category="Island";
        callCatalogue(category);

    }

    public void callHillStation(View view) {
        String category="Hill Station";
        callCatalogue(category);
    }

    public void callBeaches(View view) {
        String category="Beaches";
        callCatalogue(category);
    }

    public void callCatalogue(String category){
        Intent intent = new Intent(getBaseContext(), PlaceCatalogue.class);
        intent.putExtra("PLACE_CATEGORY",category);
        startActivity(intent);
    }


}
