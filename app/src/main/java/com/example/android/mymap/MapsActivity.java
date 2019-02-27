package com.example.android.mymap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;


        settingsForMap();

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        // Show Silicon Valley on the map.
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(37.4029937, -122.1811827), 10));
/*
        //Add a marker in Silicon Valley and move the camera
        LatLng siliconValley = new LatLng(37.4029937, -122.1811827);
        mMap.addMarker(new MarkerOptions().position(siliconValley).title("Marker in Silicon Valley"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(siliconValley));
*/



    }



    @Override
    public void onCameraMove() {
        mMap.clear();
        Log.d("onCameraMove","The camera is moving.");
      //  Toast.makeText(this, "The camera is moving.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveCanceled() {
        Log.d("onCameraMoveCanceled","Camera movement canceled.");
       // Toast.makeText(this, "Camera movement canceled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        Log.d("onCameraIdle","The camera has stopped moving.");
       // Toast.makeText(this, "The camera has stopped moving.", Toast.LENGTH_SHORT).show();

        calculateCorners();
    }







    private void calculateCorners() {

        Projection projection = mMap.getProjection();

        VisibleRegion visibleRegion = projection.getVisibleRegion();

        LatLng corner = projection.fromScreenLocation(new Point(0, 0));
        Log.d("Projection","North East : "+visibleRegion.latLngBounds.northeast.latitude + ","+visibleRegion.latLngBounds.northeast.longitude);
        Log.d("Projection","South West : "+visibleRegion.latLngBounds.southwest.latitude + ","+visibleRegion.latLngBounds.southwest.longitude);



        PolylineOptions randomLine1 = new PolylineOptions()
                .add(new LatLng(visibleRegion.latLngBounds.northeast.latitude, visibleRegion.latLngBounds.northeast.longitude))
                .add(new LatLng(visibleRegion.latLngBounds.southwest.latitude, visibleRegion.latLngBounds.southwest.longitude)) ;
        Polyline polyline1 = mMap.addPolyline(randomLine1);


    }

    private void settingsForMap() {
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }






}
