package com.pett.travis.proxie.util;

/**
 * Created by Travis on 2/24/2015.
 */

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.pett.travis.proxie.MainActivity;
import com.pett.travis.proxie.R;

public class LocationServer implements LocationListener {
    private MainActivity activity = null;
    private Context context = null;
    private LocationManager locationManager = null;
    private GoogleMap map = null;

    public LocationServer(MainActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
        Toast.makeText(context, "LocationServer", Toast.LENGTH_SHORT).show();

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        setMap();
    }

    public void start() {
        locationManager.requestLocationUpdates(5000, 0, new Criteria(), this, null);
    }

    public void stop() {
        locationManager.removeUpdates(this);
    }

    private void setMap() {
        MapFragment mapFragment = (MapFragment)activity.getFragmentManager().findFragmentById(R.id.map);

        if(mapFragment != null) {
            map = mapFragment.getMap();
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(context, "Location Updated", Toast.LENGTH_SHORT).show();

        LatLng cameraLocation = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(cameraLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
