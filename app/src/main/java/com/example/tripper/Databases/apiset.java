package com.example.tripper.Databases;

import java.util.List;
import com.example.tripper.HelperClasses.responseModelPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiset {
    @GET("placesFetch.php")
    Call<List<responseModelPlaces>> gdata(@Query("category") String category);
}
