package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "VD1W8E2dj6qEl9Ukrf8Ytt9BvMbLShSu1WcTz7gR", "NR7BSRnyGTuKmYj2jTuqSXRbq9BRO3OHCx1l9QyJ");

    ParseUser.enableAutomaticUser();
    //ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    //defaultACL.setPublicReadAccess(true);
    //defaultACL.setPublicWriteAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
