package com.example.tripper.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.tripper.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.tripper.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.example.tripper.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.tripper.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.tripper.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.tripper.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.example.tripper.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler,mostViewedRecycler,categoriesRecycler;
    RecyclerView.Adapter adapter;
    GradientDrawable gradient1,gradient2,gradient3,gradient4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featuredRecycler = findViewById(R.id.featured_recycler);
        categoriesRecycler=findViewById(R.id.categories_recycler);
        mostViewedRecycler=findViewById(R.id.most_viewed_recycler);

        //Function Execute when dashboard created
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();;
    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocation=new ArrayList<>();
        mostViewedLocation.add(new MostViewedHelperClass(R.drawable.manali,"Manali"));
        mostViewedLocation.add(new MostViewedHelperClass(R.drawable.manali,"manali"));
        mostViewedLocation.add(new MostViewedHelperClass(R.drawable.manali,"manali"));
        mostViewedLocation.add(new MostViewedHelperClass(R.drawable.manali,"manali"));

        adapter=new MostViewedAdapter(mostViewedLocation);
        mostViewedRecycler.setAdapter(adapter);

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

    private void categoriesRecycler(){
        gradient1 =new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xffeff400,0xffaff600});
        gradient2 =new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xff7adccf,0xff7adccf});
        gradient3 =new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xff7c59f,0xFF7c59f});
        gradient4 =new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xffb8d7f5,0xffb8d7f5});

        ArrayList<CategoriesHelperClass> categoriesHelperClasses=new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1,R.drawable.restaurant_logo,"Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1,R.drawable.restaurant_logo,"Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1,R.drawable.restaurant_logo,"Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1,R.drawable.restaurant_logo,"Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1,R.drawable.restaurant_logo,"Restaurant"));

        categoriesRecycler.setHasFixedSize(true);
        adapter=new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        categoriesRecycler.setAdapter(adapter);
    }

}