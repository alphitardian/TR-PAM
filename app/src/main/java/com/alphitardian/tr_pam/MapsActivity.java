package com.alphitardian.tr_pam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public static String EXTRA_ADDRESS = "extra_address";
    private final int PERMISSION_REQ_CONTACT = 2;

    private Button getMarkerLocationButton;
    private FloatingActionButton getCurrentLocationButton;
    private GoogleMap mMap;

    private String addressSelection;
    private LatLng newPosition;

    private LocationManager locationManager;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQ_CONTACT);
                }
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getMarkerLocationButton = findViewById(R.id.get_marker_location_button);
        getCurrentLocationButton = findViewById(R.id.current_location_button);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                newPosition = new LatLng(location.getLatitude(), location.getLongitude());

            }
        });

        getMarkerLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra(EXTRA_ADDRESS, addressSelection);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        getCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.setPosition(newPosition);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 15.0f));
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng salatiga = new LatLng(-7.3305, 110.5084);
        marker = mMap.addMarker(new MarkerOptions().position(salatiga));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(salatiga, 10.0f));
        addressSelection = getCompleteAddress(salatiga);

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        marker.setPosition(latLng);
        addressSelection = getCompleteAddress(latLng);

    }

    private String getCompleteAddress(LatLng latLng) {
        String ret = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnAddress = addresses.get(0);
                StringBuilder stringBuilder = new StringBuilder("");

                for (int i = 0; i <= returnAddress.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(returnAddress.getAddressLine(i)).append("\n");
                }

                ret = stringBuilder.toString();
                Log.d("MAP", "getCompleteAddress: " + ret);
            } else {
                Log.d("MAP", "getCompleteAddress: No Address");
            }
        } catch (Exception e) {
            Log.d("MAP", "Can't Get Address");
        }

        return ret;
    }
}