package com.test.carslist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.carslist.R;
import com.test.carslist.daos.DAOPlacemark;
import com.test.carslist.models.Placemark;

import java.util.HashMap;

import io.realm.OrderedRealmCollection;

public class CarsMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private SupportMapFragment supportMapFragment;

    HashMap markerMap = new HashMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment  = inflater.inflate(R.layout.fragment_map, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(supportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();

            fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
        }

        supportMapFragment.getMapAsync(this);

        return fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setRotateGesturesEnabled(false);

        createMarkers();

        //Start the map camera position
        DAOPlacemark daoPlacemark = new DAOPlacemark();
        Placemark placemark = daoPlacemark.findFirst();
        zoomTo(map, createFromPlacemark(placemark), 10);
    }

    private void createMarkers() {
        DAOPlacemark daoPlacemark = new DAOPlacemark();

        OrderedRealmCollection<Placemark> placemarks = daoPlacemark.getAllPlacemarks();
        for (Placemark placemark : placemarks) {

            Marker marker = map.addMarker(new MarkerOptions().position(createFromPlacemark(placemark)).title(placemark.getName()));
            markerMap.put(placemark.getName(), marker);

        }
    }

    private void zoomTo(GoogleMap googleMap, LatLng latLng, int zoom)
    {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom), 1000, null);

    }

    private LatLng createFromPlacemark(Placemark placemark) {
        return new LatLng(placemark.getCoordinates().getLatitude(), placemark.getCoordinates().getLongitude());
    }

    public void VerifyIfMarkerExistsAndShowTitle(String name)
    {
        Marker marker = (Marker)markerMap.get(name);
        if(marker == null) {
            //TODO show error if marker does not exist
        }
        zoomTo(map, marker.getPosition(), 17);
        marker.showInfoWindow();
    }
}
