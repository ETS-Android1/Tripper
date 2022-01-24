package com.example.tripper.Databases;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeaturedPlacesController {
    private static final String url="http://192.168.1.32/tripper/";
    private static FeaturedPlacesController clientObject;
    private static Retrofit retrofit;

    FeaturedPlacesController(){
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public static synchronized FeaturedPlacesController getInstance(){
        if (clientObject==null){
            clientObject=new FeaturedPlacesController();
        }
        return clientObject;
    }

    public topPlaceApiSet getApiSet(){
        return retrofit.create(topPlaceApiSet.class);
    }


}
