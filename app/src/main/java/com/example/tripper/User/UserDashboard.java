package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.tripper.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.tripper.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.tripper.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featuredRecycler = findViewById(R.id.featured_recycler);

        featuredRecycler();
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations=new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.manali,"Manali","Manali offers the most magnificent views of the Pir Panjal and the Dhauladhar ranges covered with snow"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.manali,"Manali","Manali offers the most magnificent views of the Pir Panjal and the Dhauladhar ranges covered with snow"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.manali,"Manali","Manali offers the most magnificent views of the Pir Panjal and the Dhauladhar ranges covered with snow"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }

}