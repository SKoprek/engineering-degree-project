package com.example.plusinfobus.api.model.GoogleApi.transit;

import java.util.List;

public class Line {


	private List<Agency> agencies;
	private String name;
	private String url;
	private Vehicle vehicle;
	public List<Agency> getAgencies() {
		return agencies;
	}
	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
