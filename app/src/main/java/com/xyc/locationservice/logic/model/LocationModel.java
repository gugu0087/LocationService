package com.xyc.locationservice.logic.model;



/**
 * Created by hasee on 2018/3/29.
 */

public class LocationModel {
    private String lastTime;
    private String address;
    private long latitude;
    private long longtitude;

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(long longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "LocationModel{" +
                "lastTime='" + lastTime + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }
}
