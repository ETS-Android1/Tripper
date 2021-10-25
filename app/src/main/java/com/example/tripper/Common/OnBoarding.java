package com.example.tripper.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.tripper.HelperClasses.SliderAdapter;
import com.example.tripper.R;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dots;

    SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        //Hooks
        viewPager=findViewById(R.id.slider);
        dots=findViewById(R.id.dots);

        //sliderAdapter
        sliderAdapter=new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
    }

}