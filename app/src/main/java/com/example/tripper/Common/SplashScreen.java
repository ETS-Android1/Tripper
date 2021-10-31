package com.example.tripper.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tripper.R;
import com.example.tripper.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    LottieAnimationView lottieAnimationView;
    TextView tv;
    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Removes the status bar from top
        setContentView(R.layout.splash_screen);
        logo=findViewById(R.id.logo);

        tv=findViewById(R.id.powered);
        lottieAnimationView=findViewById(R.id.lottie);
        logo.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
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
                    onBoardingScreen=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                    boolean isFirst=onBoardingScreen.getBoolean("firstTime",true);

                    if(isFirst){
                        SharedPreferences.Editor editor=onBoardingScreen.edit();
                        editor.putBoolean("firstTime",false);
                        editor.commit();

                        Intent intent=new Intent(SplashScreen.this,OnBoarding.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent=new Intent(SplashScreen.this, UserDashboard.class);
                        startActivity(intent);
                        finish();
                    }


                }
            }
        };
        td.start();

    }

}