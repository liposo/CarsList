package com.test.carslist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.carslist.R;
import com.test.carslist.adapters.CarAdapter;
import com.test.carslist.daos.DAOPlacemark;
import com.test.carslist.models.Placemark;

import io.realm.OrderedRealmCollection;

public class CarsListFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected CarAdapter carAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment  = inflater.inflate(R.layout.fragment_cars_list, container, false);

        recyclerView = (RecyclerView) fragment.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        carAdapter = new CarAdapter(getAllPlacemarks(), this.getActivity());
        recyclerView.setAdapter(carAdapter);

        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
    }

    private OrderedRealmCollection<Placemark> getAllPlacemarks() {
        DAOPlacemark daoPlacemark = new DAOPlacemark();
        return daoPlacemark.getAllPlacemarks();
    }
}
