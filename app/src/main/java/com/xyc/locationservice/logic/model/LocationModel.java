package com.xyc.locationservice.logic.model;



/**
 * Created by hasee on 2018/3/29.
 */

public class LocationModel {
    private String lastTime;
    private String address;
    private double latitude;
    private double longtitude;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
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
