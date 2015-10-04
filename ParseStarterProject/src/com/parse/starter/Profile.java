package com.parse.starter;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Activity {

    private TextView sb1,sb2,sb3,sb4,sb5,sb0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sb0 = (TextView) findViewById(R.id.sb0);
        sb1 = (TextView) findViewById(R.id.sb1);
        sb2 = (TextView) findViewById(R.id.sb2);
        sb3 = (TextView) findViewById(R.id.sb3);
        sb4 = (TextView) findViewById(R.id.sb4);
        sb5 = (TextView) findViewById(R.id.sb5);

        ParseUser CurrentUser = ParseUser.getCurrentUser();
        ParseRelation relation = CurrentUser.getRelation("MyPoints");
        ParseQuery query = relation.getQuery();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> MyPointsList, ParseException e) {
                System.out.println("START");
                if (e == null) {
                    System.out.println(MyPointsList.size());
                    System.out.println(MyPointsList.get(0).getInt("Points"));
                    sb0.setText(Integer.toString(MyPointsList.get(0).getInt("Points")));
                } else
                    sb0.setText("0");
            }
        });
/*
        query = ParseQuery.getQuery("User");
        query.setLimit(5);
        query.orderByDescending("Points");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score board", "Retrieved " + scoreList.size() + " scores");
                    if (scoreList.size() > 0) {
                        final ParseUser CurrentUser = (ParseUser) scoreList.get(0);
                        ParseRelation relation = CurrentUser.getRelation("MyPoints");
                        ParseQuery query = relation.getQuery();

                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> MyPointsList, ParseException e) {
                                if (e == null) {
                                    sb1.setText(CurrentUser.getUsername() + " " + MyPointsList.get(0).getInt("Points"));
                                } else
                                    sb1.setText(CurrentUser.getUsername() + "0");
                            }
                        });
                    }
                    if (scoreList.size() > 1) {
                        final ParseUser CurrentUser = (ParseUser) scoreList.get(1);
                        ParseRelation relation = CurrentUser.getRelation("MyPoints");
                        ParseQuery query = relation.getQuery();

                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> MyPointsList, ParseException e) {
                                if (e == null) {
                                    sb2.setText(CurrentUser.getUsername() + " " + MyPointsList.get(1).getInt("Points"));
                                } else
                                    sb2.setText(CurrentUser.getUsername() + "0");
                            }
                        });
                    }
                    if (scoreList.size() > 2) {
                        final ParseUser CurrentUser = (ParseUser) scoreList.get(2);
                        ParseRelation relation = CurrentUser.getRelation("MyPoints");
                        ParseQuery query = relation.getQuery();

                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> MyPointsList, ParseException e) {
                                if (e == null) {
                                    sb3.setText(CurrentUser.getUsername() + " " + MyPointsList.get(2).getInt("Points"));
                                } else
                                    sb3.setText(CurrentUser.getUsername() + "0");
                            }
                        });
                    }
                    if (scoreList.size() > 3) {
                        final ParseUser CurrentUser = (ParseUser) scoreList.get(3);
                        ParseRelation relation = CurrentUser.getRelation("MyPoints");
                        ParseQuery query = relation.getQuery();

                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> MyPointsList, ParseException e) {
                                if (e == null) {
                                    sb4.setText(CurrentUser.getUsername() + " " + MyPointsList.get(3).getInt("Points"));
                                } else
                                    sb4.setText(CurrentUser.getUsername() + "0");
                            }
                        });
                    }
                    if (scoreList.size() > 4) {
                        final ParseUser CurrentUser = (ParseUser) scoreList.get(4);
                        ParseRelation relation = CurrentUser.getRelation("MyPoints");
                        ParseQuery query = relation.getQuery();

                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> MyPointsList, ParseException e) {
                                if (e == null) {
                                    sb5.setText(CurrentUser.getUsername() + " " + MyPointsList.get(4).getInt("Points"));
                                } else
                                    sb5.setText(CurrentUser.getUsername() + "0");
                            }
                        });
                    }
                } else {
                    Log.d("score board", "Error: " + e.getMessage());
                }
            }
        });
*/
    }

}
