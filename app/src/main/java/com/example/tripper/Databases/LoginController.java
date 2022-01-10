package com.example.tripper.Databases;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginController {

    private static final String url="http://192.168.1.32/tripper/";
    private static LoginController clientObject;
    private static Retrofit retrofit;

    LoginController(){
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public static synchronized LoginController getInstance(){
        if (clientObject==null){
            clientObject=new LoginController();
        }
        return clientObject;
    }

    public LoginApiSet getApiSet(){
        return retrofit.create(LoginApiSet.class);
    }


}
