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
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public static String EXTRA_ADDRESS = "extra_address";

    private Button getMarkerLocationButton;
    private FloatingActionButton getCurrentLocationButton;
    private ProgressBar progressBar;
    private EditText addressEditText;
    private GoogleMap mMap;

    private String addressSelection;
    private LatLng currentPosition;
    private String[] permissions;

    private LocationManager locationManager;
    private Marker marker;

    ExecutorService executorService;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getMarkerLocationButton = findViewById(R.id.get_marker_location_button);
        getCurrentLocationButton = findViewById(R.id.current_location_button);
        progressBar = findViewById(R.id.progress_bar);
        addressEditText = findViewById(R.id.address_edittext);

        progressBar.setVisibility(View.INVISIBLE);

        permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    });
                }
            }
        }

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
                progressBar.setVisibility(View.VISIBLE);

                executorService.execute(() -> {
                    try {
                        addressSelection = getCompleteAddress(currentPosition);
                        handler.post(() -> {
                            if (!addressSelection.isEmpty() && ActivityCompat.checkSelfPermission(getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                                progressBar.setVisibility(View.INVISIBLE);
                                marker.setPosition(currentPosition);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15.0f));
                                addressEditText.setText(addressSelection);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MapsActivity.this, "Can't get location. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        Log.e("getCurrentLocation", "onClick: ", e);
                    }
                });
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            }
        }

        // Add a marker in Sydney and move the camera
        LatLng salatiga = new LatLng(-7.3305, 110.5084);

        executorService.execute(() -> {
            addressSelection = getCompleteAddress(salatiga);
            handler.post(() -> {
                if (!addressSelection.isEmpty()) {
                    marker = mMap.addMarker(new MarkerOptions().position(salatiga));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(salatiga, 10.0f));
                    addressEditText.setText(addressSelection);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MapsActivity.this, "Can't get location. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("New Location", "onMapClick: " + getCompleteAddress(latLng));
        executorService.execute(() -> {
            addressSelection = getCompleteAddress(latLng);
            handler.post(() -> {
                if (!addressSelection.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    marker.setPosition(latLng);
                    addressEditText.setText(addressSelection);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MapsActivity.this, "Can't get location. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });


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
        } catch (IOException e) {
            Log.d("MAP", "Can't Get Address" + e);
        } catch (Exception ex) {
            Log.d("MAP", "There's Other Problem" + ex);
        }

        return ret;
    }

}