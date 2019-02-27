package com.example.android.mymap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
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
     * we just add a marker near Silicon Valley.
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

        // Show Silicon Valley on the map.
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(37.4029937, -122.1811827), 10));
    }



    @Override
    public void onCameraMove() {
        mMap.clear();
        Log.d("onCameraMove","The camera is moving.");
        //DrawLines();
      //  Toast.makeText(this, "The camera is moving.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        Log.d("onCameraIdle","The camera has stopped moving.");
       // Toast.makeText(this, "The camera has stopped moving.", Toast.LENGTH_SHORT).show();

        DrawLines();
    }

    private void DrawLines() {

        Projection projection = mMap.getProjection();

        VisibleRegion visibleRegion = projection.getVisibleRegion();

        Point corner = new Point(0, 0);
        Point northEast = projection.toScreenLocation(visibleRegion.latLngBounds.northeast);
        Point southWest = projection.toScreenLocation(visibleRegion.latLngBounds.southwest);


        double width = Math.sqrt(Math.pow(northEast.x, 2) + Math.pow(northEast.y,2));
        double height = Math.sqrt(Math.pow(southWest.x, 2) + Math.pow(southWest.y,2));

        int oneWidthUnit = (int)(width/4);
        int oneHightUnit = (int)(height/5);
        int height2 = (int) height;
        int width2 = (int) width;



        LatLng hps1 = projection.fromScreenLocation(new Point(oneWidthUnit, 0));
        LatLng hps2 = projection.fromScreenLocation(new Point(oneWidthUnit*2, 0));
        LatLng hps3 = projection.fromScreenLocation(new Point(oneWidthUnit*3, 0));

        LatLng hpe1 = projection.fromScreenLocation(new Point(oneWidthUnit, height2));
        LatLng hpe2 = projection.fromScreenLocation(new Point(oneWidthUnit*2, height2));
        LatLng hpe3 = projection.fromScreenLocation(new Point(oneWidthUnit*3, height2));

        PolylineOptions randomLine1 = new PolylineOptions()
                .add(new LatLng(hps1.latitude, hps1.longitude))
                .add(new LatLng(hpe1.latitude, hpe1.longitude));
        Polyline polyline1 = mMap.addPolyline(randomLine1);

        PolylineOptions randomLine2 = new PolylineOptions()
                .add(new LatLng(hps2.latitude, hps2.longitude))
                .add(new LatLng(hpe2.latitude, hpe2.longitude));
        Polyline polyline2 = mMap.addPolyline(randomLine2);

        PolylineOptions randomLine3 = new PolylineOptions()
                .add(new LatLng(hps3.latitude, hps3.longitude))
                .add(new LatLng(hpe3.latitude, hpe3.longitude));
        Polyline polyline3 = mMap.addPolyline(randomLine3);





        LatLng vps1 = projection.fromScreenLocation(new Point(0, oneHightUnit));
        LatLng vps2 = projection.fromScreenLocation(new Point(0, oneHightUnit*2));
        LatLng vps3 = projection.fromScreenLocation(new Point(0, oneHightUnit*3));
        LatLng vps4 = projection.fromScreenLocation(new Point(0, oneHightUnit*4));

        LatLng vpe1 = projection.fromScreenLocation(new Point(width2, oneHightUnit));
        LatLng vpe2 = projection.fromScreenLocation(new Point(width2, oneHightUnit*2));
        LatLng vpe3 = projection.fromScreenLocation(new Point(width2, oneHightUnit*3));
        LatLng vpe4 = projection.fromScreenLocation(new Point(width2, oneHightUnit*4));

        PolylineOptions horizantalLine1 = new PolylineOptions()
                .add(new LatLng(vps1.latitude, vps1.longitude))
                .add(new LatLng(vpe1.latitude, vpe1.longitude));
        Polyline HoriPolyline1 = mMap.addPolyline(horizantalLine1);

        PolylineOptions horizantalLine2 = new PolylineOptions()
                .add(new LatLng(vps2.latitude, vps2.longitude))
                .add(new LatLng(vpe2.latitude, vpe2.longitude));
        Polyline HoriPolyline2 = mMap.addPolyline(horizantalLine2);

        PolylineOptions horizantalLine3 = new PolylineOptions()
                .add(new LatLng(vps3.latitude, vps3.longitude))
                .add(new LatLng(vpe3.latitude, vpe3.longitude));
        Polyline HoriPolyline3 = mMap.addPolyline(horizantalLine3);

        PolylineOptions horizantalLine4 = new PolylineOptions()
                .add(new LatLng(vps4.latitude, vps4.longitude))
                .add(new LatLng(vpe4.latitude, vpe4.longitude));
        Polyline HoriPolyline4 = mMap.addPolyline(horizantalLine4);

        int startX = oneWidthUnit;
        int startY = oneHightUnit;
        for(int j=1; j<4;j++){

            for(int i=1;i<5;i++){
                //Add a marker in Silicon Valley and move the camera
                LatLng p = projection.fromScreenLocation(new Point(startX*j, startY*i));
                mMap.addMarker(new MarkerOptions().position(p));
            }

        }

    }

    private void settingsForMap() {
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

}
