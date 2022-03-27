package com.example.tripper.HelperClasses;

import com.example.tripper.Common.ConnectionAddress;
import com.example.tripper.Databases.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiController {
    private static final String url= ConnectionAddress.ipaddress;
    private static apiController clientobject;
    private static Retrofit retrofit;

    apiController(){
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }

    public static synchronized apiController getInstance(){
        if (clientobject==null) {
            clientobject = new apiController();
        }
        return clientobject;
    }
    public apiset getapi(){
        return retrofit.create(apiset.class);
    }
}
