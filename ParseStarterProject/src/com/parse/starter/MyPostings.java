package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.FindCallback;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jenniferjain on 7/8/15.
 */
public class MyPostings extends Activity {

    // Declare Variables
    ListView listview;
    ArrayAdapter<String> adapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflate and return the layout
        //View v = inflater.inflate(R.layout.mygrouplist_layout, container, false);
        setContentView(R.layout.mygrouplist_layout);

        listview = (ListView) findViewById(R.id.list);

        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation relation = user.getRelation("MyGroups");
        ParseQuery query = relation.getQuery();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> MyGroupList, ParseException e) {
                if (e == null) {
                    Log.d("group", "Retrieved " + MyGroupList.size() + " groups");
                    adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview);
                    final List<String> finalList = new ArrayList<String>();
                    for (ParseObject StudyGroup : MyGroupList) {
                        adapter.add(StudyGroup.getString("Hashtag") + "\n" + "Not wanted after date: " + StudyGroup.getString("Month") +
                                "/" + StudyGroup.getString("Day") + "/" + StudyGroup.getString("Year") + "\n" + "Not wanted after time: " +
                                StudyGroup.getString("Hour") + ":" + StudyGroup.getString("Minute") + " " +
                                StudyGroup.getString("AMPM") + "\n" + "Point rewards: " +  StudyGroup.getInt("Points"));
                        String objectId = StudyGroup.getObjectId();
                        finalList.add(objectId);
                    }
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int position, long arg3) {
                            int itemPosition = position;

                            // ListView Clicked item value
                            String itemValue = (String) listview.getItemAtPosition(position);

                            Intent intent = new Intent(MyPostings.this, RequestConfirm.class);
                            intent.putExtra("GroupID", finalList.get(itemPosition));
                            startActivity(intent);

                            // Show Alert
                            Toast.makeText(getApplicationContext(),
                                    "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                                    .show();

                        }
                    });

                } else {
                    Log.d("MyGroupList query ", "Error: " + e.getMessage());
                }
            }
        });

    }

}