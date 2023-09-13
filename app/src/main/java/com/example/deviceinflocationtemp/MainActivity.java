package com.example.deviceinflocationtemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.deviceinflocationtemp.enums.DeviceInf;
import com.example.deviceinflocationtemp.interfaces.CallbackInterface;
import com.example.deviceinflocationtemp.models.LocationUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    private static final int PERMISSION_REQ_CODE = 100;

    private Button getInfBtn;
    private TextView ipIpv4TextView, ipIpv6TextView, manufacturerTextView, brandTextView, productTextView, modelTextView,
            countryCodeTextView, countryNameTextView, cityTextView;

    private LocationUser locationUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();
        checkPermission();

        getInfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSetIP();
                getSetDeviceInf();
                getSetLocation();
            }
        });
    }

    private void setReferences() {
        getInfBtn = findViewById(R.id.get_inf_btn);
        ipIpv4TextView = findViewById(R.id.ip_ipv4_text_view);
        ipIpv6TextView = findViewById(R.id.ip_ipv6_text_view);
        manufacturerTextView = findViewById(R.id.manufacturer_text_view);
        brandTextView = findViewById(R.id.brand_text_view);
        productTextView = findViewById(R.id.product_text_view);
        modelTextView = findViewById(R.id.model_text_view);
        countryCodeTextView = findViewById(R.id.country_code_text_view);
        countryNameTextView = findViewById(R.id.country_name_text_view);
        cityTextView = findViewById(R.id.city_text_view);
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermission: ACCESS_FINE_LOCATION Permission already have");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermission: ACCESS_COARSE_LOCATION Permission already have");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermission: INTERNET Permission already have");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermission: READ_EXTERNAL_STORAGE Permission already have");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermission: WRITE_EXTERNAL_STORAGE Permission already have");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermission: ACCESS_WIFI_STATE Permission already have");
        }
    }

    private void getSetIP() {
        String ipAddressIPv4 = PublicMethods.getIPAddress(true);
        ipIpv4TextView.setText(ipAddressIPv4);

        String ipAddressIPv6 = PublicMethods.getIPAddress(false);
        ipIpv6TextView.setText(ipAddressIPv6);
    }

    private void getSetDeviceInf() {
        String manufacturer = PublicMethods.getDeviceInf(DeviceInf.MANUFACTURER);
        manufacturerTextView.setText(manufacturer);

        String brand = PublicMethods.getDeviceInf(DeviceInf.BRAND);
        brandTextView.setText(brand);

        String product = PublicMethods.getDeviceInf(DeviceInf.PRODUCT);
        productTextView.setText(product);

        String model = PublicMethods.getDeviceInf(DeviceInf.MODEL);
        modelTextView.setText(model);
    }

    private void getSetLocation() {
        locationUser = new LocationUser(getBaseContext());

        locationUser.getLocation(new CallbackInterface() {
            @Override
            public void access() {
                getSetCountryCodeName();
            }
        });
    }

    private void getSetCountryCodeName() {
        String countryCode = locationUser.getCountryCode();
        countryCodeTextView.setText(countryCode);

        String countryName = locationUser.getCountryName();
        countryNameTextView.setText(countryName);

        String city = locationUser.getCity();
        cityTextView.setText(city);
    }
}