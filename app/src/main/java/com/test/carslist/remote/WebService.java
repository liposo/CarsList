package com.test.carslist.remote;

import com.google.gson.JsonObject;
import com.test.carslist.remote.POJOS.CarsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("locations.json")
    Call<CarsResponse> getCars();
}

