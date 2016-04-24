package com.example.anr.healthifyme;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ANR on 23/04/16.
 */
public interface RestServices {

    @GET("/api/v1/booking/slots/all?username=alok%40x.coz&api_key=a4aeb4e27f27b5786828f6cdf00d8d2cb44fe6d7&vc=276&expert_username=neha%40healthifyme.com&format=json")
    void getData1(Callback<Model> cb);

}
