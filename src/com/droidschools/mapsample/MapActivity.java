package com.droidschools.mapsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.droischools.mapsample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity {
	String data;
	static final String KEY_PLACEMARK = "Placemark";
	static final String KEY_NAME = "name";
	static final String KEY_COORDINATES = "coordinates";
	ArrayList<Placemark> places = new ArrayList<Placemark>();
	int[] colors = {Color.RED, Color.BLUE, Color.MAGENTA, Color.RED, Color.BLUE, Color.MAGENTA, Color.LTGRAY,
			Color.BLACK, Color.DKGRAY, Color.GRAY, Color.RED, Color.BLUE, Color.MAGENTA, Color.RED, Color.BLUE,
			Color.MAGENTA, Color.LTGRAY, Color.BLACK, Color.DKGRAY, Color.GRAY, Color.RED, Color.BLUE, Color.MAGENTA,
			Color.RED, Color.BLUE, Color.MAGENTA, Color.LTGRAY, Color.BLACK, Color.DKGRAY, Color.GRAY, Color.RED,
			Color.BLUE, Color.MAGENTA, Color.RED, Color.BLUE, Color.MAGENTA, Color.LTGRAY, Color.BLACK, Color.DKGRAY,
			Color.GRAY};
	int colorIndex = 0;
	int markerResources[] = {R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6,R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6,R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6,R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6,R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6,R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6,R.drawable.marker1, R.drawable.marker2, R.drawable.marker3, R.drawable.marker4,
			R.drawable.marker5, R.drawable.marker6};
	int markerIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// LatLng koratty = new LatLng(10.26127, 76.35465);
		// LatLng trichurRound = new LatLng(10.527549, 76.214487);
		// LatLng infopark = new LatLng(10.268992, 76.354183);
		GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		// map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		/*
		 * map.addMarker(new
		 * MarkerOptions().position(koratty).title("Koratty zodiac bar")
		 * .snippet("best drinks available ").anchor(0, 0)); map.addMarker(new
		 * MarkerOptions().position(infopark).title("Infopark koratty"));
		 * map.addPolyline(new PolylineOptions().add(koratty, infopark,
		 * trichurRound).width(5).color(Color.RED));
		 */// Shows my point on map
		map.setMyLocationEnabled(true);
		// map.moveCamera(CameraUpdateFactory.newLatLngZoom(koratty, 15));
		// //////////////////////////// kml ////////////////////////
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		AssetManager assetManager = getAssets();
		try {
			InputStream isr = assetManager.open("infoparkcampus.kml");
			data = getStringFromInputStream(isr);
			// Log.d("data", data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		XMLParser parser = new XMLParser();

		// for the project put the url in the following commented line
		// String xml = parser.getXmlFromUrl(URL); // getting XML

		log(data);
		Document doc = parser.getDomElement(data);
		// getting DOM element for each "Placemark " tag
		NodeList nl = doc.getElementsByTagName(KEY_PLACEMARK);
		// looping through all <Placemark> nodes
		for (int i = 0; i < nl.getLength(); i++) {
			Placemark placemark = new Placemark();
			// HashMap<String, String> cordinatesHashMap = new HashMap<String,
			// String>();
			// Getting dom element for each place mark
			Element e = (Element) nl.item(i);
			// Extracting coordiantes
			String coordinateString = parser.getValue(e, KEY_COORDINATES);
			// Extracting Placemark name
			placemark.setName(parser.getValue(e, "name"));
			// Extracting Placemark description

			placemark.setDescription(parser.getValue(e, "description"));
			String strin = parser.getValue(e, "Point");
			if (parser.getValue(e, "Point").length() != 0)
				placemark.setType("Point");
			else if (parser.getValue(e, "LineString").length() != 0)
				placemark.setType("LineString");
			else if (parser.getValue(e, "Polygon").length() != 0)
				placemark.setType("Polygon");

			log("String" + coordinateString);
			// ArrayList<ArrayList<String>> brokenCoordinates = new
			// ArrayList<ArrayList<String>>();
			String[] locationCoordinates;
			// 3 length array to store lat, lng, alt
			String[] latLngAlt;
			locationCoordinates = coordinateString.split(" ");
			for (String singleLocation : locationCoordinates) {
				// Clear prev data
				latLngAlt = null;
				latLngAlt = singleLocation.split(",");
				Coordinate coordinate = new Coordinate();
				// Ignore blank sizes and tabs
				if (latLngAlt.length == 3 && !latLngAlt[0].equals("") && !latLngAlt[1].equals("")
						&& !latLngAlt[2].equals("")) {
					coordinate
							.setLatLng(new LatLng(Double.parseDouble(latLngAlt[1]), Double.parseDouble(latLngAlt[0])));
					coordinate.setAltitude(Double.parseDouble(latLngAlt[2]));
					placemark.addCoordinates(coordinate);
				}

			}

			log("adding one place");
			places.add(placemark);
			// adding each child node <cordinate> to HashMap key => value
			// *cordinatesHashMap.put(KEY_NAME, parser.getValue(e,
			// KEY_COORDINATES));
			// adding HashList to ArrayList
			// log(parser.getValue(e, KEY_COORDINATES));
			// menuItems.add(cordinatesHashMap);
		}
		boolean zoomed = false;
		for (Placemark place : places) {

			if (place.getType() != null && place.getType().equals("Point")) {
				log("POINT aanu");
				// TODO check it more points are in list
				if (place.getName().equals("Qburst")) {
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getCoordinates().get(0).getLatLng(), 18));
					zoomed = true;
				}
				map.addMarker(new MarkerOptions().position(place.getCoordinates().get(0).getLatLng())
						.title(place.getName()).snippet(place.getDescription())
						.icon(BitmapDescriptorFactory.fromResource(markerResources[markerIndex])));
				markerIndex++;
			} else if (place.getType() != null && place.getType().equals("LineString")) {
				ArrayList<LatLng> latLngList = new ArrayList<LatLng>();
				log("Line annu");
				for (int i = 0; i < place.getCoordinates().size(); i++) {
					latLngList.add(place.getCoordinates().get(i).getLatLng());
				}
				map.addPolyline(new PolylineOptions().addAll(latLngList).width(5).color(colors[colorIndex]));
				colorIndex++;

			} else if (place.getType() != null && place.getType().equals("Polygon")) {
				ArrayList<LatLng> latLngList = new ArrayList<LatLng>();
				for (int i = 0; i < place.getCoordinates().size(); i++) {
					latLngList.add(place.getCoordinates().get(i).getLatLng());
				}
				if(latLngList.size()!= 0)
				map.addPolygon(new PolygonOptions().addAll(latLngList).strokeWidth(2).strokeColor(colors[colorIndex]).fillColor(colors[colorIndex+1]));
				colorIndex++;

				

			} else {
				Toast.makeText(getBaseContext(), "Unknown placemark", 0).show();
			}
		}

		// End of oncreate
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	public void log(Object message) {
		if (message != null)
			Log.v("Mapactivty", message.toString());

	}

}
