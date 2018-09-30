package com.test.carslist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CarsList extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("db.realm")
                .schemaVersion(1)
                .compactOnLaunch()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
