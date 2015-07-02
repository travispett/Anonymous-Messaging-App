package com.pett.travis.proxie.message_service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.proxie.SerializedMessage;

/**
 * Created by Travis on 4/2/2015.
 */
public class ProxieMessage implements Parcelable{

    public static final String BROADCAST_MESSAGE = "BROADCAST";
    public static final String ANONYMOUS = "Anonymous";

    private String text;
    private String sender;
    private String target;
    double senderLatitude;
    double senderLongitude;
    int numHops;
    long expirationTime;
    int createByUser;

    public ProxieMessage(String text, String sender, String target, LatLng source, int numHops, long expirationTime, int createByUser) {
        this.text = text;
        this.sender = sender;
        this.target = target;
        this.senderLatitude = source.latitude;
        this.senderLongitude = source.longitude;
        this.numHops = numHops;
        this.expirationTime = expirationTime;
        this.createByUser = createByUser;
    }

    public ProxieMessage(SerializedMessage m) {
        text = m.getText();
        sender = m.getSender();
        target = m.getTarget();
        senderLatitude = m.getLatitude();
        senderLongitude = m.getLongitude();
        numHops = m.getNumHops();
        expirationTime = m.getExpirationTime();
        createByUser = m.getCreatedByUser();
    }

    public SerializedMessage serialize() {
        return new SerializedMessage(text, sender, target, senderLatitude, senderLongitude, numHops, expirationTime, createByUser);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(sender);
        dest.writeString(target);
        dest.writeDouble(senderLatitude);
        dest.writeDouble(senderLongitude);
        dest.writeInt(numHops);
        dest.writeLong(expirationTime);
        dest.writeInt(createByUser);
    }

    public static final Parcelable.Creator<ProxieMessage> CREATOR = new Parcelable.Creator<ProxieMessage>() {
        public ProxieMessage createFromParcel(Parcel in) {
            return new ProxieMessage(in);
        }

        public ProxieMessage[] newArray(int size) {
            return new ProxieMessage[size];
        }
    };

    private ProxieMessage(Parcel in) {
        text = in.readString();
        sender = in.readString();
        target = in.readString();
        senderLatitude = in.readDouble();
        senderLongitude = in.readDouble();
        numHops = in.readInt();
        expirationTime = in.readLong();
        createByUser = in.readInt();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public LatLng getSource() {
        return new LatLng(senderLatitude, senderLongitude);
    }

    public void setSource(LatLng source) {
        this.senderLatitude = source.latitude;
        this.senderLongitude = source.longitude;
    }

    public int getNumHops() {
        return numHops;
    }

    public void setNumHops(int numHops) {
        this.numHops = numHops;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(int createByUser) {
        this.createByUser = createByUser;
    }
}
