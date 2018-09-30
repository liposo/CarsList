package com.test.carslist.daos;

import com.test.carslist.models.Placemark;

import io.realm.Realm;
import io.realm.RealmResults;

public class DAOPlacemark {

    private Realm realm;

    public DAOPlacemark() {
        realm = Realm.getDefaultInstance();
    }

    public void createOrUpdate(Placemark placemark) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(placemark);
            realm.commitTransaction();
            realm.close();

        } catch (Exception e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
    }

    public RealmResults<Placemark> getAllPlacemarks() {
        return realm.where(Placemark.class).findAll();
    }

    public Placemark findFirst() {
        return realm.where(Placemark.class).findFirst();
    }

    public int getCount() {
        return realm.where(Placemark.class).findAll().size();
    }

    public Placemark findByName(String name) {
        return realm.where(Placemark.class).equalTo("name", name).findFirst();
    }
}
