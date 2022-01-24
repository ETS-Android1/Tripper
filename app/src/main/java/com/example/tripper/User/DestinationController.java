package com.example.tripper.User;

import com.example.tripper.Databases.LoginApiSet;
import com.example.tripper.Databases.PlaceApiSet;
import com.example.tripper.Databases.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DestinationController {

    private static final String url="http://192.168.1.32/tripper/";
    private static DestinationController clientObject;
    private static Retrofit retrofit;

    DestinationController(){
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized DestinationController getInstance(){
        if (clientObject==null){
            clientObject=new DestinationController();
        }
        return clientObject;
    }
    public PlaceApiSet getapi(){
        return retrofit.create(PlaceApiSet.class);
    }

}
