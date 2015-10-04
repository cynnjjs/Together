package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseRelation;
import com.parse.GetCallback;

import java.util.List;
/**
 * Created by yiningchen on 7/26/15.
 */
public class RequestConfirm extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String StrGroupID = bundle.getString("GroupID", "DEFAULT");

        //inflate and return the layout
        setContentView(R.layout.activity_requestconfirm);
        final Button button2,button3;
        final EditText editText1 = (EditText)findViewById(R.id.editText1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button3.setClickable(true);
        button2.setClickable(true);

        // retrieve from parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("HelpMe");
        query.getInBackground(StrGroupID, new GetCallback<ParseObject>() {
            public void done(final ParseObject TargetGroup, ParseException e) {
                if (e == null) {
                    // TargetGroup will be the result
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TargetGroup.deleteInBackground();
                            button3.setClickable(false);
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // add points to the fulfiller
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                            System.out.println("start");

                            query.whereEqualTo("username", editText1.getText().toString().toLowerCase());
                            query.findInBackground(new FindCallback<ParseObject>() {
                                public void done(List<ParseObject> results, ParseException e) {
                                    System.out.println(results.size());
                                    System.out.println(editText1.getText().toString().toLowerCase());
                                    if (e == null) {
                                        Log.d("score", "Retrieved " + results.size() + " scores");
                                        final ParseUser fulfiller = (ParseUser) results.get(0);
                                        System.out.println(fulfiller.getEmail());
                                        ParseRelation relation = fulfiller.getRelation("MyPoints");
                                        ParseQuery query = relation.getQuery();

                                        query.findInBackground(new FindCallback<ParseObject>() {
                                            public void done(List<ParseObject> MyPointsList, ParseException e) {
                                                if (e == null) {
                                                    MyPointsList.get(0).put("Points", MyPointsList.get(0).getInt("Points") + TargetGroup.getInt("Points"));
                                                    MyPointsList.get(0).saveInBackground();
                                                    ParseUser user = ParseUser.getCurrentUser();
                                                    ParseRelation relation = user.getRelation("MyPoints");
                                                    ParseQuery query = relation.getQuery();
                                                    query.findInBackground(new FindCallback<ParseObject>() {
                                                        public void done(List<ParseObject> MyPointsList, ParseException e) {
                                                            if (e == null) {
                                                                MyPointsList.get(0).put("Points", MyPointsList.get(0).getInt("Points") - TargetGroup.getInt("Points"));
                                                                MyPointsList.get(0).saveInBackground();
                                                                TargetGroup.deleteInBackground();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Log.d("score relation", "Error: " + e.getMessage());
                                                }
                                            }
                                        });

                                    } else {
                                        Log.d("score", "Error: " + e.getMessage());
                                    }
                                }
                            });

                            button2.setClickable(false);
                        }
                    });


                } else {
                    // something went wrong
                    Log.d("group details page 2 ", "Error: " + e.getMessage());
                }
            }

        });

    }
}
