package com.example.tripper.User;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripper.HelperClasses.apiController;
import com.example.tripper.HelperClasses.myPlaceAdapter;
import com.example.tripper.HelperClasses.responseModelPlaces;
import com.example.tripper.R;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceCatalogue extends AppCompatActivity {

    RecyclerView recyclerView;
    String category;
    myPlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_catalogue);
        ActionBar actionBar;
        //required action bar
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0F9D58"));
        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        recyclerView = findViewById(R.id.recView);
        category = getIntent().getStringExtra("PLACE_CATEGORY");
        Objects.requireNonNull(getSupportActionBar()).setTitle(category);  //
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        processData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processData() {
        Call<List<responseModelPlaces>> call = apiController
                .getInstance()
                .getapi()
                .gdata(category);
        call.enqueue(new Callback<List<responseModelPlaces>>() {
            @Override
            public void onResponse(Call<List<responseModelPlaces>> call,
                                   Response<List<responseModelPlaces>> response) {
                List<responseModelPlaces> data = response.body();
                adapter = new myPlaceAdapter(data, getApplicationContext());
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