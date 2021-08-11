package com.b2gsoft.sailorsexpress.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.utils.CustomInfoWindowGoogleMap;
import com.b2gsoft.sailorsexpress.utils.GPSTracker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private TextInputLayout textInputLayout;
    private AppCompatEditText etLocation;
    private AppCompatButton btnSubmit;

    private GoogleMap googleMap;
    private boolean permissionGranted;
    private FusedLocationProviderClient fusedLocationProvider;
    private MarkerOptions markerOptions;
    private Marker marker;

    private LatLng defaultLocation = new LatLng(23.758581, 90.378989);
    private Location currentLocation;
    private GPSTracker gpsTracker;

    private String address;
    private boolean firstSet = true;

    private Bitmap markerBitmap;

    private final int FINE_LOCATION_REQUESTED_CODE = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        etLocation = (AppCompatEditText) findViewById(R.id.et_location);

        markerOptions = new MarkerOptions();
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        gpsTracker = new GPSTracker(this);

        initMarkerIcon();
        initMap();
        requestPermission();
    }


    private void initMarkerIcon() {

        int height = 120;
        int width = 120;

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.marker);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        markerBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }


    private void requestPermission() {

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            permissionGranted = true;
            getCurrentLocation();
            onMapReady(googleMap);
        }
        else {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUESTED_CODE);
        }
    }


    private void getCurrentLocation() {

        if(gpsTracker.canGetLocation()) {

            currentLocation = gpsTracker.getLocation();
        }
        else {

            gpsTracker.showSettingsAlert();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        permissionGranted = false;

        switch (requestCode) {

            case FINE_LOCATION_REQUESTED_CODE: {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    permissionGranted = true;
                    getCurrentLocation();
                    onMapReady(googleMap);
                }
                else {

                    permissionGranted = false;
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        try {

            googleMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap(this));
            googleMap.setOnMarkerDragListener(this);

            initMapSettings();
            initializeMapLocationSettings();

            new getAddressFromLocation().execute();
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        //showMarker(16.5f);
    }


    private void initMapSettings() {

        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        googleMap.setTrafficEnabled(true);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(false);
    }


    private void initializeMapLocationSettings() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(false);
    }


    private void showMarker(float zoom) {

        try {

            if(currentLocation != null) {

                LatLng position = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                if(marker != null) {

                    marker.remove();
                }

                if(firstSet) {

                    firstSet = false;
                    zoom = 16.5f;
                }

                marker = googleMap.addMarker(markerOptions.position(position).draggable(true).icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
                marker.showInfoWindow();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
            }
            else {

                if(marker != null) {

                    marker.remove();
                }

                marker = googleMap.addMarker(markerOptions.position(defaultLocation).draggable(true).icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
                marker.showInfoWindow();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 6.5f));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public void onMarkerDragStart(Marker marker) {

        marker.hideInfoWindow();
    }


    @Override
    public void onMarkerDrag(Marker marker) {

        marker.hideInfoWindow();
    }


    @Override
    public void onMarkerDragEnd(Marker marker) {

        currentLocation.setLatitude(marker.getPosition().latitude);
        currentLocation.setLongitude(marker.getPosition().longitude);

        new getAddressFromLocation().execute();
        marker.showInfoWindow();
    }


    private void showPermissionDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        MaterialButton btnClose = dialog.findViewById(R.id.btn_close);
        MaterialButton btnSubmit = dialog.findViewById(R.id.btn_submit);

        TextView txtTitle = dialog.findViewById(R.id.txt_title);
        TextView txtDesc = dialog.findViewById(R.id.txt_desc);
        btnSubmit.setText(R.string.ok);

        txtTitle.setText(getString(R.string.warning));
        txtDesc.setText(getString(R.string.location_permission_needed));

        btnClose.setVisibility(View.GONE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    class getAddressFromLocation extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(getAddress() != null) {

                address = getAddress().get(0).getAddressLine(0);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            if(address != null) {

                etLocation.setText(address);
            }

            showMarker(googleMap.getCameraPosition().zoom);
        }
    }


    private List<Address> getAddress() {

        if(currentLocation != null) {

            try {

                Geocoder geocoder = new Geocoder(LocationActivity.this);

                List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);

                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                return addresses;

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
