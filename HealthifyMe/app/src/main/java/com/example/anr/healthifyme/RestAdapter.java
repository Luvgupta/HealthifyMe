package com.example.anr.healthifyme;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.client.OkClient;

/**
 * Created by ANR on 23/04/16.
 */
public class RestAdapter {
    private static final String MAGIC_REST_ENDPOINT = "http://108.healthifyme.com";
    private static RestServices restService;

    static OkHttpClient okHttpClient = new OkHttpClient();

    public static RestServices getRestService() {
        if(restService == null) {
            okHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return null;
                }
            });

            retrofit.RestAdapter restAdapter = new retrofit.RestAdapter.Builder()
                    .setEndpoint(MAGIC_REST_ENDPOINT)
                    .build();

            restService = restAdapter.create(RestServices.class);
        }
        return restService;
    }
}
