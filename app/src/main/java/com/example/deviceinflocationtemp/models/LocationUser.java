package com.example.deviceinflocationtemp.models;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.deviceinflocationtemp.interfaces.CallbackInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/09/11
 */
public class LocationUser {

    private static final String TAG = "Test_code";

    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private double latitude;
    private double longitude;

    private String city;

    public LocationUser(Context context) {
        this.context = context;
        setLocationManager();
    }

    private void setLocationManager() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void getLocation(CallbackInterface callback) {
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d(TAG, "onLocationChanged: Location position: latitude:" + latitude + " longitude:" + longitude);
                callback.access();
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocation: Need permission ACCESS_FINE_LOCATION");
            return;
        }
//
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public String getCity() {
        Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();
                // here saving name of city (or location)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    public String getLocationAndCity() {

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d(TAG, "onLocationChanged: Location position: latitude:" + latitude + " longitude:" + longitude);

                // Change coordination to city/location name
                Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        city = addresses.get(0).getLocality();
                        // here saving name of city (or location)
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "Error! Need permission of ACCESS_FINE_LOCATION";
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        return city;
    }

    public String getCountryCode() {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            Log.d(TAG, "getCountryCode: Get Country code. latitude:" + latitude + " longitude:" + longitude);

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                String countryCode = addresses.get(0).getCountryCode(); // Short code of country (like JP)
                return countryCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCountryName() {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                String countryName = addresses.get(0).getCountryName(); // full name of country (like, "Japan" for Japan)
                return countryName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
