package com.example.plusinfobus.api.model.GoogleApi.transit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class GoogleApiTransit {

	@JsonIgnoreProperties(ignoreUnknown = true)
	private List<GeocodedWaypoint> geocoded_waypoints;
	private List<Route> routes;
	private String status;
	
	public List<GeocodedWaypoint> getGeocoded_waypoints() {
		return geocoded_waypoints;
	}
	public void setGeocodedWaypoints(List<GeocodedWaypoint> geocoded_waypoints) {
		this.geocoded_waypoints = geocoded_waypoints;
	}
	public List<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}
