package com.justiceleague.locus.rest;

import com.google.gson.JsonObject;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.justiceleague.locus.model.Loc;
import com.justiceleague.locus.model.User;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vishal.kumar1 on 18/08/16.
 */
public interface ApiInterface {
    @GET("api")
    Call<JsonObject> testing();

    @GET("api/login")
    Call<JsonObject> signIn(@Query("mobile_number") long mobile, @Query("lat") String password );


    @GET("api/send")
    Call<JsonObject> send(@Query("mobile_number") long mobile, @Query("lat") double lat, @Query("lng") double lng);

    @GET("api/receive")
    Call<Loc> recieve(@Query("mobile_number") long mobile);


    @POST("api/signup")
    Call<JsonObject> signup(@Body User user);
}

