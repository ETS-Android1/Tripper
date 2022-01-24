package com.example.tripper.Databases;

import com.example.tripper.HelperClasses.placeResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApiSet {
    @GET("singlePlace.php")
    Call<List<placeResponseModel>> retrieve(@Query("placeId") String placeId);
}

