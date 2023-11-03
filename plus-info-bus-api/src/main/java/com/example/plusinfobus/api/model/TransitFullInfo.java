package com.example.plusinfobus.api.model;

import java.util.List;

public class TransitFullInfo {
	
    private String departure_stop;
    private String arrival_stop;
    private List<List<BusTransit>> busTransit;
	public String getDeparture_stop() {
		return departure_stop;
	}
	public void setDeparture_stop(String departure_stop) {
		this.departure_stop = departure_stop;
	}
	public String getArrival_stop() {
		return arrival_stop;
	}
	public void setArrival_stop(String arrival_stop) {
		this.arrival_stop = arrival_stop;
	}
	public List<List<BusTransit>> getBusTransit() {
		return busTransit;
	}
	public void setBusTransit(List<List<BusTransit>> busTransit) {
		this.busTransit = busTransit;
	}

    
}
