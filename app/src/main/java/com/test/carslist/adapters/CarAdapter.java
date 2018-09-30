package com.test.carslist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.carslist.R;
import com.test.carslist.interfaces.GoToMarker;
import com.test.carslist.models.Placemark;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class CarAdapter extends RealmRecyclerViewAdapter<Placemark, CarAdapter.MyViewHolder> {

    private GoToMarker goToMarker;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView vin;
        private TextView interior;
        private TextView exterior;
        private TextView engineType;
        private TextView fuel;
        private TextView coordinates;
        private TextView address;
        public Placemark placemark;

        private MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.nameTextView);
            vin = (TextView) view.findViewById(R.id.vinTextView);
            interior = (TextView) view.findViewById(R.id.interiorTextView);
            exterior = (TextView) view.findViewById(R.id.exteriorTextView);
            engineType = (TextView) view.findViewById(R.id.engineTextView);
            fuel = (TextView) view.findViewById(R.id.fuelTextView);
            coordinates = (TextView) view.findViewById(R.id.coordinatesTextView);
            address = (TextView) view.findViewById(R.id.addressTextView);
        }
    }

    public CarAdapter(OrderedRealmCollection<Placemark> placemarks, Context context) {
        super(placemarks, true);

        goToMarker = (GoToMarker) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Placemark placemark = getItem(position);

        holder.placemark = placemark;
        holder.name.setText(holder.placemark.getName());
        holder.address.setText(holder.placemark.getAddress());
        holder.exterior.setText(holder.placemark.getExterior());
        holder.interior.setText(holder.placemark.getInterior());
        holder.vin.setText(holder.placemark.getVin());
        holder.fuel.setText(Integer.toString(holder.placemark.getFuel()));
        holder.engineType.setText(holder.placemark.getEngineType());
        holder.coordinates.setText(holder.placemark.getCoordinates().getLongitude() + " , " +holder.placemark.getCoordinates().getLatitude());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(goToMarker != null) {
                    goToMarker.sendName(holder.placemark.getName());
                }
            }
        });
    }
}
