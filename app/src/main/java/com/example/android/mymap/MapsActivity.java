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
        Log.d("onCameraMove","The camera is moving.");

        mMap.clear();

        //if you want to draw lines immediately after camera moves activate DrawLines() method in onCameraMove() and deactivate DrawLines() method  in the onCameraIdle() method
        //DrawLines();

    }

    @Override
    public void onCameraIdle() {
        Log.d("onCameraIdle","The camera has stopped moving.");

        //if you want to draw lines when camera is idle activate DrawLines() method in onCameraIdle() and deactivate DrawLines() method  in the onCameraMove() method
       DrawLines();
    }

    //Calculates screen's width and height and calls drawVerticalLines, drawHorizontalLines, drawMarker methods
    private void DrawLines() {

        Projection projection = mMap.getProjection();

        VisibleRegion visibleRegion = projection.getVisibleRegion();

        Point northEast = projection.toScreenLocation(visibleRegion.latLngBounds.northeast);
        Point southWest = projection.toScreenLocation(visibleRegion.latLngBounds.southwest);


        double width = Math.sqrt(Math.pow(northEast.x, 2) + Math.pow(northEast.y,2));
        double height = Math.sqrt(Math.pow(southWest.x, 2) + Math.pow(southWest.y,2));

        int unitWidth = (int)(width/4);
        int unitHeight = (int)(height/5);


        drawVerticalLines(unitWidth, unitHeight, projection);
        drawHorizontalLines(unitWidth, unitHeight, projection);

        drawMarker(unitWidth, unitHeight, projection);


    }

    /**
     * Draws horizontal lines
     *
     * @param unitWidth is 1/4 of width of screen
     * @param unitHeight is 1/5 of height of screen
     * @param projection is used to translate between on screen location and geographic coordinates on the surface
     *                   of the Earth (LatLng). Screen location is in screen pixels (not display pixels) with respect to the top left corner
     *                   of the map (and not necessarily of the whole screen).
     */
    private void drawHorizontalLines(int unitWidth, int unitHeight, Projection projection) {

        for (int i=1; i<5; i++){
            LatLng startPoint = projection.fromScreenLocation(new Point(0, unitHeight*i));
            LatLng endPoint = projection.fromScreenLocation(new Point(unitWidth*4, unitHeight*i));
            PolylineOptions randomLine1 = new PolylineOptions()
                    .add(new LatLng(startPoint.latitude, startPoint.longitude))
                    .add(new LatLng(endPoint.latitude, endPoint.longitude));
           mMap.addPolyline(randomLine1);

        }



    }


    /**
     * Draws vertical lines
     *
     * @param unitWidth is 1/4 of width of screen
     * @param unitHeight is 1/5 of height of screen
     * @param projection is used to translate between on screen location and geographic coordinates on the surface
     *                   of the Earth (LatLng). Screen location is in screen pixels (not display pixels) with respect to the top left corner
     *                   of the map (and not necessarily of the whole screen).
     */
    private void drawVerticalLines(int unitWidth, int unitHeight, Projection projection) {

        for (int i=1; i<4; i++){
            LatLng startPoint = projection.fromScreenLocation(new Point(unitWidth*i, 0));
            LatLng endPoint = projection.fromScreenLocation(new Point(unitWidth*i, unitHeight*5));
            PolylineOptions randomLine1 = new PolylineOptions()
                    .add(new LatLng(startPoint.latitude, startPoint.longitude))
                    .add(new LatLng(endPoint.latitude, endPoint.longitude));
            mMap.addPolyline(randomLine1);

        }
    }


    /**
     * Put markers to intersections
     *
     * @param pointX is x coordinate of the point
     * @param pointY is y coordinate of the point
     * @param projection is used to translate between on screen location and geographic coordinates on the surface
     *                   of the Earth (LatLng). Screen location is in screen pixels (not display pixels) with respect to the top left corner
     *                   of the map (and not necessarily of the whole screen).
     */
    private void drawMarker(int pointX, int pointY, Projection projection) {

        for(int j=1; j<4; j++){
            for(int i=1 ;i<5; i++){
                //Add a marker to intersection
                mMap.addMarker(new MarkerOptions().position(projection.fromScreenLocation(new Point(pointX*j, pointY*i))));
            }
        }
    }

    //Map settings
    private void settingsForMap() {
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

}
