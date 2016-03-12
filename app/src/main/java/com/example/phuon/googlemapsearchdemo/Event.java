package com.example.phuon.googlemapsearchdemo;

/**
 * Created by phuon on 3/12/2016.
 */
public class Event {

    private String name;
    private String date;
    private String time;
    private String organizer;
    private String lat;
    private String lng;

    public Event(String name, String date, String time, String organizer, String lat, String lng) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.organizer = organizer;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
