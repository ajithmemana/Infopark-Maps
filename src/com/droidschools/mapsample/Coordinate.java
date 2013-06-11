package com.droidschools.mapsample;

import com.google.android.gms.maps.model.LatLng;

public class Coordinate {

	private LatLng latLng;
	private double altitude;
	public LatLng getLatLng() {
		return latLng;
	}
	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
}
