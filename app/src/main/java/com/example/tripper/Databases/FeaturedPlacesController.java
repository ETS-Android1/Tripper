package com.example.tripper.Databases;

import com.example.tripper.Common.ConnectionAddress;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeaturedPlacesController {
    private static final String url= ConnectionAddress.ipaddress+"/";
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
