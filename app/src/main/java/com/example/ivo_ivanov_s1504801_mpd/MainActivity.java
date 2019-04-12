/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Ivo Ivanov
// Student ID           S1504801
// Programme of Study   Games Programing(Software Development)

package com.example.ivo_ivanov_s1504801_mpd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.ivo_ivanov_s1504801_mpd.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity implements OnClickListener, PopupMenu.OnMenuItemClickListener
{

    ListView listView;
    public ArrayList<String> title= new ArrayList<String>();
    public ArrayList<String> description = new ArrayList<String>();
    public ArrayList<String> Location= new ArrayList<String>();
    public ArrayList<Integer> Magnitude= new ArrayList<Integer>();
    public ArrayList<Integer> Depth= new ArrayList<Integer>();
    public ArrayList<Data> Data = new ArrayList<Data>();
    public ArrayList<Float> Long = new ArrayList<Float>();
    public ArrayList<Float> Lat = new ArrayList<Float>();
    SearchView SearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lView);
        title = new ArrayList<String>();
        description = new ArrayList<String>();
        startProgress();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), SecondScreen.class);
                intent.putExtra("Location",Data.get(position).GetLocation());
                intent.putExtra("Magnitude",Data.get(position).GetMagnitude());
                intent.putExtra("Depth",Data.get(position).GetDepth());
                intent.putExtra("Long",Data.get(position).GetLong());
                intent.putExtra("Lat",Data.get(position).GetLat());
                startActivity(intent);

            }
        });

        SearchView = (SearchView) findViewById(R.id.SearchView);
        SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String String) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String String) {
                Search(String);
                return false;
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popupmenu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.search1:
                SortSouth();
                WriteList();
                Collections.reverse(description);
                break;

            case R.id.search2:
                SortSouth();
                WriteList();
                break;

            case R.id.search3:
                SortEast();
                WriteList();
                break;

            case R.id.search4:
                SortEast();
                WriteList();
                Collections.reverse(description);
                break;

            case R.id.search5:
                SortMagnitude();
                WriteList();
                Collections.reverse(description);
                break;

            case R.id.search6:
                SortDepth();
                WriteList();
                Collections.reverse(description);
                break;

            case R.id.search7:
                SortDepth();
                WriteList();
                break;

            case R.id.search8:
                SortLocation();
                WriteList();
                break;
        }
        return false;
    }

    void Search(String String)
    {
        description.clear();
        for (int i=0;i<Data.size();i++)
        {
            if(Data.get(i).GetLocation().toLowerCase().contains(String))
            {
                description.add(Data.get(i).GetLocation());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, description);
        listView.setAdapter(adapter);
    }

    void SeparateData (ArrayList<String> Description)
    {
        for (int i=0;i<Description.size();i++)
        {
            String[] S;
            S = Description.get(i).split(":");
            String Location;
            String[] DepthString;
            String[] Location2;
            //String[] MagnitudeString;
            Integer Depth;
            Float Magnitude;
            DepthString = S[6].split("k");
            //MagnitudeString = S[7].split("");
            Depth = Integer.parseInt(DepthString[0].trim());
            Location = S[4].trim(); //5, 7
            Location2 = Location.split(";");
            Location = Location2[0];
            //Depth = Integer.parseInt(S[6]);
            Magnitude = Float.parseFloat(S[7].trim());
            Data.add(new Data(Magnitude,Depth,Location, Long.get(i), Lat.get(i)));
        }
        WriteList();
    }

    private void SortLocation() {
        Collections.sort(Data, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.GetLocation().compareTo(o2.GetLocation());
            }
        });
    }

    public void SortDepth(){
        Collections.sort(Data, new Comparator<Data>() {
            @Override public int compare(Data o1, Data o2) {
                return o1.GetDepth() - o2.GetDepth();
            }});}

    public void SortMagnitude(){
    Collections.sort(Data, new Comparator<Data>() {
        @Override public int compare(Data o1, Data o2) {
            return Float.compare(o1.GetMagnitude(), o2.GetMagnitude());
        }});}

    public void SortEast(){
        Collections.sort(Data, new Comparator<Data>() {
            @Override public int compare(Data o1, Data o2) {
                return Float.compare(o1.GetLong(), o2.GetLong());
            }});}

    public void SortSouth(){
        Collections.sort(Data, new Comparator<Data>() {
            @Override public int compare(Data o1, Data o2) {
                return Float.compare(o1.GetLat(), o2.GetLat());
            }});}


    void WriteList()
    {
        description.clear();
        for (int i = 0 ; i < Data.size() ; i++)
        {
            description.add("Location: " + Data.get(i).GetLocation() + " Depth: " + Data.get(i).GetDepth() + " Magnitude: " + Data.get(i).GetMagnitude());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, description);
        listView.setAdapter(adapter);
    }

    public void onClick(View aview)
    {
        //startProgress();
    }

    public void startProgress()
    {
        new ProcessInBackground().execute();
    }


    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }


    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Busy loading rss feed...please wait...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType(); //loop control variable
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                title.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                description.add(xpp.nextText());
                            }
                        }

                        else if (xpp.getName().equalsIgnoreCase("geo:lat"))
                        {
                            if (insideItem)
                            {
                                Lat.add(Float.parseFloat(xpp.nextText()));
                            }
                        }

                        else if (xpp.getName().equalsIgnoreCase("geo:long"))
                        {
                            if (insideItem)
                            {
                                Long.add(Float.parseFloat(xpp.nextText()));
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    title);

            listView.setAdapter(adapter);

            SeparateData(description);

            progressDialog.dismiss();
        }
    }


}
