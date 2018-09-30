package com.test.carslist.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://s3-us-west-2.amazonaws.com/wunderbucket/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        if (retrofit == null) {
            retrofit = builder.build();
        }
        return retrofit;
    }
}
