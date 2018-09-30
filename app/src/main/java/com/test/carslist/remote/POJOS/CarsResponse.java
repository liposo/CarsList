package com.test.carslist.remote.POJOS;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarsResponse {

    @SerializedName("placemarks")
    @Expose
    private List<Placemark> placemarks = null;

    public List<Placemark> getPlacemarks() {
        return placemarks;
    }

    public void setPlacemarks(List<Placemark> placemarks) {
        this.placemarks = placemarks;
    }

}
