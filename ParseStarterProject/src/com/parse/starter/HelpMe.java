package com.parse.starter;


import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class HelpMe extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button button2;
    private Spinner spinnerMonth,spinnerDay,spinnerYear;
    private Spinner spinnerHour,spinnerMinute,spinnerAMPM;
    private EditText hashTxt,descriptionTxt;
    private EditText pointsTxt;

    private GoogleApiClient mGoogleApiClient;
    protected static final String TAG = "basic-location-sample";
    protected Location mLastLocation;
    protected LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_helpme);
        button2 = (Button) findViewById(R.id.button2);

        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        spinnerMinute = (Spinner) findViewById(R.id.spinnerMinute);
        spinnerAMPM = (Spinner) findViewById(R.id.spinnerAMPM);
        hashTxt = (EditText) findViewById(R.id.hashTxt);
        descriptionTxt = (EditText) findViewById(R.id.descriptionTxt);
        pointsTxt = (EditText) findViewById(R.id.pointsTxt);

        setTitle("Help Me!");

        mGoogleApiClient.connect();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if both name and location fields are unfilled
                if (descriptionTxt.getText().toString().equals("")) {
                    // make notification to fill in both fields
                    Toast.makeText(getApplicationContext(), "Please fill in description!", Toast.LENGTH_LONG).show();
                }
                // check if only name is unfilled
                else if (hashTxt.getText().toString().equals("")) {
                    // make notification to fill in name field
                    Toast.makeText(getApplicationContext(), "Please fill in hashtag!", Toast.LENGTH_LONG).show();
                }
                // check if only location is unfilled
                else {
                    boolean f=true;
                    int pts=0;
                    try {
                        pts = Integer.parseInt(pointsTxt.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Please fill in integer points!", Toast.LENGTH_LONG).show();
                        f=false;
                    }
                    // otherwise the group should be able to be created with all the necessary information
                    if (f) {
                        //Toast.makeText(getApplicationContext(),
                        //    "Group created",
                        //    Toast.LENGTH_SHORT).show();
                        button2.setText("Request Sent");
                        button2.setClickable(false);

                        final ParseUser currentUser = ParseUser.getCurrentUser();

                        final ParseObject NewGroup = new ParseObject("HelpMe");

                        NewGroup.put("Hashtag", hashTxt.getText().toString());
                        NewGroup.put("Founder", currentUser.getUsername());
                        NewGroup.put("Month", String.valueOf(spinnerMonth.getSelectedItem()));
                        NewGroup.put("Day", String.valueOf(spinnerDay.getSelectedItem()));
                        NewGroup.put("Year", String.valueOf(spinnerYear.getSelectedItem()));
                        NewGroup.put("Hour", String.valueOf(spinnerHour.getSelectedItem()));
                        NewGroup.put("Minute", String.valueOf(spinnerMinute.getSelectedItem()));
                        NewGroup.put("AMPM", String.valueOf(spinnerAMPM.getSelectedItem()));
                        NewGroup.put("Points", pts);
                        NewGroup.put("Long", mLatLng.longitude);
                        NewGroup.put("Lati", mLatLng.latitude);
                        NewGroup.put("Points", pts);
                        NewGroup.put("Info", descriptionTxt.getText().toString());
                        NewGroup.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    //myObjectSavedSuccessfully();

                                    // Add relation: user to group
                                    ParseRelation relation = currentUser.getRelation("MyGroups");
                                    relation.add(NewGroup);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                finish();
                                            } else {
                                                Log.d("currentUser Weird1", "Error: " + e.getMessage());
                                            }
                                        }
                                    });

                                } else {
                                    //myObjectSaveDidNotSucceed();
                                    Log.d("NewGroup weird 1!!!", "Error: " + e.getMessage());
                                }
                            }
                        });
                        //finish();
                    }
                }
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

        } else {

            mLatLng = new LatLng(34.5, -72.2);

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
}
