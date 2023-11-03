package com.example.admin.plusinfobus.model;

public class BusTransit {

    private String departure_stop_text;
    private String arrival_stop_text;
    private long timeFromEpoch;
    private String distance_text;
    private String duration_text;
    private String departure_time_text;
    private String arrival_time_text;
    private String agencies_name;
    public String getDeparture_stop_text() {
        return departure_stop_text;
    }
    public void setDeparture_stop_text(String departure_stop_text) {
        this.departure_stop_text = departure_stop_text;
    }
    public String getArrival_stop_text() {
        return arrival_stop_text;
    }
    public void setArrival_stop_text(String arrival_stop_text) {
        this.arrival_stop_text = arrival_stop_text;
    }
    public long getTimeFromEpoch() {
        return timeFromEpoch;
    }
    public void setTimeFromEpoch(long timeFromEpoch) {
        this.timeFromEpoch = timeFromEpoch;
    }
    public String getDistance_text() {
        return distance_text;
    }
    public void setDistance_text(String distance_text) {
        this.distance_text = distance_text;
    }
    public String getDuration_text() {
        return duration_text;
    }
    public void setDuration_text(String duration_text) {
        this.duration_text = duration_text;
    }
    public String getDeparture_time_text() {
        return departure_time_text;
    }
    public void setDeparture_time_text(String departure_time_text) {
        this.departure_time_text = departure_time_text;
    }
    public String getArrival_time_text() {
        return arrival_time_text;
    }
    public void setArrival_time_text(String arrival_time_text) {
        this.arrival_time_text = arrival_time_text;
    }
    public String getAgencies_name() {
        return agencies_name;
    }
    public void setAgencies_name(String agencies_name) {
        this.agencies_name = agencies_name;
    }


}