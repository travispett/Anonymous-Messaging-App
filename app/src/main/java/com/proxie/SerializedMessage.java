package com.proxie;

import com.google.android.gms.maps.model.LatLng;
import java.io.Serializable;

public class SerializedMessage implements Serializable {


    private String text;
    private String sender;
    private String target;
    private double latitude;
    private double longitude;
    private int numHops;
    private long expirationTime;
    private int createdByUser;

    /* Create a new message */
    public SerializedMessage(String text, String sender, String target, double latitude,
                             double longitude, int numHops, long expirationTime, int createdByUser) {

        this.text = text;
        this.sender = sender;
        this.target = target;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numHops = numHops;
        this.expirationTime = expirationTime;
        this.createdByUser = createdByUser;
    }

    /* Getter methods */
    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public int getNumHops() {
        return numHops;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public int getCreatedByUser() {
        return createdByUser;
    }
}
