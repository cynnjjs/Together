package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;

import com.parse.ParseUser;
import android.content.Intent;
import com.parse.ParseAnonymousUtils;

public class CheckLoginStatus extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to Login.class
            Intent intent = new Intent(CheckLoginStatus.this,
                    Login.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to MapsActivity.class
                Intent intent = new Intent(CheckLoginStatus.this, MapsActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to Login.class
                Intent intent = new Intent(CheckLoginStatus.this,
                        Login.class);
                startActivity(intent);
                finish();
            }
        }

		//ParseAnalytics.trackAppOpenedInBackground(getIntent());

	}
}
