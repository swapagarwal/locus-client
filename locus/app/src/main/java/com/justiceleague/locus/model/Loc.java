package com.justiceleague.locus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vishal.kumar1 on 28/08/16.
 */
public class Loc {
    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    public Loc(long lat, long lng){
        this.lat=lat;
        this.lng=lng;
    }

    public void setLat(long lat){
        this.lat=lat;
    }
    public void setLng(long lng){
        this.lng=lng;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLong() {
        return this.lng;
    }
}
