//
// Name                 Ivo Ivanov
// Student ID           S1504801
// Programme of Study   Games Programing(Software Development)

package com.example.ivo_ivanov_s1504801_mpd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.ivo_ivanov_s1504801_mpd.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondScreen extends AppCompatActivity {



    private Button mapButton;
    String Location;
    float Magnitude;
    int Depth;
    float Long;
    float Lat;
    ArrayList<String> Info = new ArrayList<String>();
    ListView listView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        listView2 = (ListView) findViewById(R.id.lview2);

        Intent intent = getIntent();
        Location = intent.getStringExtra("Location");
        Magnitude = intent.getFloatExtra("Magnitude", 0);
        Depth = intent.getIntExtra("Depth", 0);
        Long = intent.getFloatExtra("Long", 0);
        Lat = intent.getFloatExtra("Lat", 0);
        Info.add("Location: " + Location);
        Info.add("Magnitude: " + Magnitude);
        Info.add("Depth: " + Depth);
        Info.add("Long: " + Long);
        Info.add("Lat: " + Lat);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondScreen.this, android.R.layout.simple_list_item_1, Info);
        listView2.setAdapter(adapter);



        mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               moveToMap();
            }
        });
    }

    public void moveToMap()
    {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("Location",Location);
        intent.putExtra("Magnitude",Magnitude);
        intent.putExtra("Depth",Depth);
        intent.putExtra("Long",Long);
        intent.putExtra("Lat",Lat);
        startActivity(intent);
    }

}
