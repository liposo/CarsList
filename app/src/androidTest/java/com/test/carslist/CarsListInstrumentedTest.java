package com.test.carslist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.test.carslist.daos.DAOPlacemark;
import com.test.carslist.remote.CarsRequest;

import org.junit.Before;
import org.junit.Test;

import io.realm.Realm;

import static org.junit.Assert.assertEquals;

public class CarsListInstrumentedTest {

    @Before
    public void createRealmInstace() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Realm.init(appContext);

        CarsRequest carsRequest = new CarsRequest();
        carsRequest.makeRequestAndPersistData();
    }

    @Test
    public void objectsNumberCorrect(){
        DAOPlacemark placemark = new DAOPlacemark();
        assertEquals(423, placemark.getCount());
    }

    @Test
    public void informationCorret() {
        DAOPlacemark placemark = new DAOPlacemark();
        assertEquals("HH-GO8522", placemark.findByName("HH-GO8522").getName());
    }
}
