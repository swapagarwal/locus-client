package com.justiceleague.locus2.rest;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vishal.kumar1 on 18/08/16.
 */
public interface ApiInterface {
    @GET("reverse")
    Call<JsonObject> getTopRatedMovies(@Query("format") String format, @Query("lat") double lat, @Query("lon") double lon,@Query("zoom") int zoom, @Query("addressdetails") int address);

    //http://nominatim.openstreetmap.org/reverse?format=json&lat=12.975709&lon=77.602660&zoom=18&addressdetails=1

}

