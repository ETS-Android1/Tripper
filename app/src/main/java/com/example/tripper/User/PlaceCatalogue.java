package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tripper.HelperClasses.apiController;
import com.example.tripper.HelperClasses.myPlaceAdapter;
import com.example.tripper.R;
import com.example.tripper.HelperClasses.responseModelPlaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceCatalogue extends AppCompatActivity {

    RecyclerView recyclerView;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_catalogue);
        recyclerView=findViewById(R.id.recView);
        category = getIntent().getStringExtra("PLACE_CATEGORY");
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        processdata();
    }

    private void processdata() {
        Call<List<responseModelPlaces>> call= apiController
                .getInstance()
                .getapi()
                .gdata(category);
        call.enqueue(new Callback<List<responseModelPlaces>>() {
            @Override
            public void onResponse(Call<List<responseModelPlaces>> call,
                                   Response<List<responseModelPlaces>> response) {
                    List<responseModelPlaces> data=response.body();
                Log.d("Harsh",data.toString());
                myPlaceAdapter adapter=new myPlaceAdapter(data);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<responseModelPlaces>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}