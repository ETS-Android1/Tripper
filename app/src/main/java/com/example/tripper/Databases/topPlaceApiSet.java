package com.example.tripper.Databases;

import com.example.tripper.HelperClasses.HomeAdapter.FeaturedHelperClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface topPlaceApiSet {
    @GET("top5places.php")
    Call<List<FeaturedHelperClass>> mostRated();

}
