package com.parse.starter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    protected static final String TAG = "basic-location-sample";
    protected Location mLastLocation;
    protected LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_maps);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, HelpMe.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MyPostings.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, Profile.class);
                startActivity(intent);
            }
        });

                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mMap = mapFragment.getMap();
                mGoogleApiClient.connect();

                // Setting a custom info window adapter for the google map
 /*       if(mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker arg0) {

                    // Getting view from the layout file info_window_layout
                    View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                    // Getting the position from the marker
                    LatLng latLng = arg0.getPosition();

                    // Getting reference to the TextView to set latitude
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

                    // Getting reference to the TextView to set longitude
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                    // Setting the latitude
                    tvLat.setText("Latitude:" + latLng.latitude);

                    // Setting the longitude
                    tvLng.setText("Longitude:" + latLng.longitude);

                    // Returning the view containing InfoWindow contents

                    return v;

                }
            });
        }*/

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        LinearLayout info = new LinearLayout(getApplicationContext());
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(getApplicationContext());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(getApplicationContext());
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });


            }

            protected synchronized void buildGoogleApiClient() {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
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
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(-34, 151);
                // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                //mMap.setMyLocationEnabled(true);
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        /*
        if (mLastLocation != null) {
            mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            if (mLatLng != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                mMap.addMarker(new MarkerOptions().position(mLatLng).title("Me"));
            }
        }
        else {
            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        }
        */
            }

            @Override
            public void onConnected(Bundle connectionHint) {
                // Provides a simple way of getting a device's location and is well suited for
                // applications that do not require a fine-grained location and that do not need location
                // updates. Gets the best and most recent location currently available, which may be null
                // in rare cases when a location is not available.
                LatLng sydney = new LatLng(-34, 151);

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    //mMap.addMarker(new MarkerOptions().position(mLatLng).title("Me"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
                    //addHelpRequest(mLatLng.latitude, mLatLng.longitude, "Help me", "12:00", "I need help. There is nothing you can do. I'm at a payphone trying to call. I wonder if this will break the bubble?", 10);
                    //addHelpRequest(mLatLng.latitude + .02, mLatLng.longitude + .02, "Help me", "12:00", "I need help. There is nothing you can do. I'm at a payphone trying to call. I wonder if this will break the bubble?", 10);
                    updateMap();
                    mMap.setMyLocationEnabled(true);

                } else {

                    //Add something here once we decide what to do

                }
            }

            @Override
            public void onConnectionSuspended(int cause) {
                // The connection to Google Play services was lost for some reason. We call connect() to
                // attempt to re-establish the connection.
                Log.i(TAG, "Connection suspended");
                mGoogleApiClient.connect();
            }

            @Override
            public void onConnectionFailed(ConnectionResult result) {
                // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
                // onConnectionFailed.
                Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
            }

            public void addHelpRequest(double latitude, double longitude, String tag, String time, String description, int points) {
                LatLng newLatLng = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(newLatLng)
                        .title(tag)
                        .snippet("Available until:" + time + "\nDescription: " + description + "\nPoints:" + points));
            }

            public void updateMap() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("HelpMe");
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> requestList, ParseException e) {
                        if (e == null) {
                            System.out.println(requestList.size());
                            for (ParseObject request : requestList) {
                                System.out.println(request.getString("Info"));
                                addHelpRequest(request.getDouble("Lati"), request.getDouble("Long"), request.getString("Hashtag"), request.getString("Month") +
                                        "/" + request.getString("Day") + "/" + request.getString("Year") + " " +
                                        request.getString("Hour") + ":" + request.getString("Minute") + " " +
                                        request.getString("AMPM"), request.getString("Info"), request.getInt("Points"));
                            }
                        } else {
                            Log.d("mark on map", "Error: " + e.getMessage());
                        }
                    }
                });
            }

        }
