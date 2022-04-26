package com.example.tripper.Databases;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiSet {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> verifyuser(
            @Field("phone") String phone,
            @Field("password") String password
    );

    Call<ResponseModel> resetPassword(
            @Field("phone") String phone,
            @Field("password") String password
    );
}
