package com.test.carslist.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.test.carslist.daos.DAOPlacemark;
import com.test.carslist.interfaces.RequestInterface;
import com.test.carslist.models.Coordinates;
import com.test.carslist.models.Placemark;
import com.test.carslist.remote.POJOS.CarsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsRequest {

    RequestInterface requestInterface;

    WebService webService = RetrofitClient.getClient().create(WebService.class);

    public CarsRequest(Context context) {
        requestInterface = (RequestInterface) context;
    }

    public CarsRequest() {
        super();
    }

    public void makeRequestAndPersistData() {

        Call<CarsResponse> carsRequest = webService.getCars();
        if(requestInterface != null) {
            requestInterface.onStartRequest();
        }
        carsRequest.enqueue(new Callback<CarsResponse>() {
            @Override
            public void onResponse(Call<CarsResponse> call, Response<CarsResponse> response) {
                Log.i("OK", String.valueOf(response.code()));
                persistData(response);
            }

            @Override
            public void onFailure(Call<CarsResponse> call, Throwable t) {
                Log.e("FAIL", t.toString());
                if(requestInterface != null) {
                    requestInterface.onFailRequest();
                }
            }
        });
    }

    private void persistData(final Response<CarsResponse> response) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (com.test.carslist.remote.POJOS.Placemark placemarkPOJO : response.body().getPlacemarks()) {
                    Placemark placemark = new Placemark();
                    placemark.setVin(placemarkPOJO.getVin());
                    placemark.setName(placemarkPOJO.getName());
                    placemark.setInterior(placemarkPOJO.getInterior());
                    placemark.setFuel(placemarkPOJO.getFuel());
                    placemark.setExterior(placemarkPOJO.getExterior());
                    placemark.setEngineType(placemarkPOJO.getEngineType());
                    placemark.setAddress(placemarkPOJO.getAddress());

                    Coordinates coordinates = new Coordinates();
                    coordinates.setLatitude(placemarkPOJO.getCoordinates().get(1));
                    coordinates.setLongitude(placemarkPOJO.getCoordinates().get(0));
                    placemark.setCoordinates(coordinates);

                    DAOPlacemark daoPlacemark = new DAOPlacemark();
                    daoPlacemark.createOrUpdate(placemark);
                }
                if(requestInterface != null) {
                    requestInterface.onFinishRequest();
                }
                DAOPlacemark daoPlacemark = new DAOPlacemark();
                Log.i("SAVED", String.valueOf(daoPlacemark.getCount()));
            }
        });
    }
}
