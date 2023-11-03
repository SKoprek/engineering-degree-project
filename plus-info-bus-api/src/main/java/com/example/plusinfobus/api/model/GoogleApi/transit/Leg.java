package com.example.plusinfobus.api.model.GoogleApi.transit;

import java.util.List;

public class Leg {

	
	
	private ArrivalTime arrival_time;
	private DepartureTime departure_time;
	private Distance distance;
	private Duration duration;
	private String end_address;
	private EndLocation end_location;
	private String start_address;
	private StartLocation start_location;
	private List<Step> steps;
	private List<Object> traffic_speed_entry;
	private List<Object> via_waypoint;
	public ArrivalTime getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(ArrivalTime arrival_time) {
		this.arrival_time = arrival_time;
	}
	public DepartureTime getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(DepartureTime departure_time) {
		this.departure_time = departure_time;
	}
	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public String getEnd_address() {
		return end_address;
	}
	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}
	public EndLocation getEnd_location() {
		return end_location;
	}
	public void setEnd_location(EndLocation end_location) {
		this.end_location = end_location;
	}
	public String getStart_address() {
		return start_address;
	}
	public void setStart_address(String start_address) {
		this.start_address = start_address;
	}
	public StartLocation getStart_location() {
		return start_location;
	}
	public void setStart_location(StartLocation start_location) {
		this.start_location = start_location;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	public List<Object> getTraffic_speed_entry() {
		return traffic_speed_entry;
	}
	public void setTraffic_speed_entry(List<Object> traffic_speed_entry) {
		this.traffic_speed_entry = traffic_speed_entry;
	}
	public List<Object> getVia_waypoint() {
		return via_waypoint;
	}
	public void setVia_waypoint(List<Object> via_waypoint) {
		this.via_waypoint = via_waypoint;
	}

	
}
