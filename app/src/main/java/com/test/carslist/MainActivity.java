package com.test.carslist;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.TabLayout;;
import android.view.View;
import android.widget.ProgressBar;

import com.test.carslist.adapters.TabAdapter;
import com.test.carslist.fragments.CarsMapFragment;
import com.test.carslist.interfaces.GoToMarker;
import com.test.carslist.interfaces.RequestInterface;
import com.test.carslist.remote.CarsRequest;


public class MainActivity extends AppCompatActivity implements GoToMarker, RequestInterface {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private TabLayout tabLayout;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_label_carsList)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_label_map)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //TODO add swipe to refresh
        CarsRequest carsRequest = new CarsRequest(this);
        carsRequest.makeRequestAndPersistData();
    }

    @Override
    public void sendName(String name) {
        Log.i("RECEIVED", name);
        String tag = "android:switcher:" + R.id.pager + ":" + 1;
        CarsMapFragment carsMapFragment = (CarsMapFragment) getSupportFragmentManager().findFragmentByTag(tag);
        carsMapFragment.VerifyIfMarkerExistsAndShowTitle(name);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onFailRequest() {
        progressBar.setVisibility(View.GONE);
        //TODO SnackBar message with retry option.
    }

    @Override
    public void onFinishRequest() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStartRequest() {
        progressBar.setVisibility(View.VISIBLE);
    }
}
