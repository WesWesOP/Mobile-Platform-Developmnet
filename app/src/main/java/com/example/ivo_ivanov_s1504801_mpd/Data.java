//
// Name                 Ivo Ivanov
// Student ID           S1504801
// Programme of Study   Games Programing(Software Development)

package com.example.ivo_ivanov_s1504801_mpd;

public class Data {

    float Magnitude;
    int Depth;
    String Location;
    float Long;
    float Lat;

    public Data (float M, int D, String L, float GeoLong, float GeoLat)
    {
        Magnitude = M;
        Depth = D;
        Location = L;
        Long = GeoLong;
        Lat = GeoLat;
    }

    public float GetMagnitude()
    {
        return Magnitude;
    }

    public int GetDepth()
    {
        return Depth;
    }

    public String GetLocation()
    {
        return Location;
    }

    public float GetLong()
    {
        return Long;
    }

    public float GetLat()
    {
        return Lat;
    }
}