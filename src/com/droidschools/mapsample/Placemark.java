package com.droidschools.mapsample;

import java.util.ArrayList;

public class Placemark {
	private String name;
	private String description;
	private String type;
	private ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(ArrayList<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}
	public void addCoordinates(Coordinate coordinate) {
		this.coordinates.add(coordinate);
	}

}
