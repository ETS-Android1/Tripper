package com.example.tripper.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tripper.R;

public class SplashScreen extends AppCompatActivity {
    ImageView logo,appName,splashImg;
    LottieAnimationView lottieAnimationView;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Removes the status bar from top
        setContentView(R.layout.splash_screen);
        logo=findViewById(R.id.logo);
        appName=findViewById(R.id.app_name);
        splashImg=findViewById(R.id.image);
        tv=findViewById(R.id.powered);
        lottieAnimationView=findViewById(R.id.lottie);

        splashImg.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        tv.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        Thread td=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent=new Intent(SplashScreen.this,OnBoarding.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        td.start();

    }

}