package com.example.plusinfobus.api.model.GoogleApi.transit;

import java.util.List;

public class Route {
	
	private Bounds bounds;
	private String copyrights;
	private List<Leg> legs;
	private OverviewPolyline overview_polyline;
	private String summary;
	private List<String> warnings;
	private List<Object> waypoint_order;
	public Bounds getBounds() {
		return bounds;
	}
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	public String getCopyrights() {
		return copyrights;
	}
	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}
	public List<Leg> getLegs() {
		return legs;
	}
	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}
	public String getSummary() {
		return summary;
	}
	public OverviewPolyline getOverview_polyline() {
		return overview_polyline;
	}
	public void setOverview_polyline(OverviewPolyline overview_polyline) {
		this.overview_polyline = overview_polyline;
	}
	public List<Object> getWaypoint_order() {
		return waypoint_order;
	}
	public void setWaypoint_order(List<Object> waypoint_order) {
		this.waypoint_order = waypoint_order;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public List<String> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	
}
