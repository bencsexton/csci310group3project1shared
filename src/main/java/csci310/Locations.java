package csci310;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import csci310.model.SearchForecast;
import csci310.WeatherAPIConnector.*;

public class Locations {

	public Locations() {
		// TODO Auto-generated constructor stub
	}
	private static String filename = "src\\main\\java\\csci310\\cities.txt";
	
	public static List<String> getCityCountries(){
		File file = new File(filename); 
		List<String> cities = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			String st;
			while ((st = br.readLine()) != null) {
				cities.add(st.trim());
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return cities;
	}
	
	public static List<HashMap<String, Object>> getValidCities(float lowTemp, float highTemp, int n, String currentLocation){
		List<HashMap<String, Object>> locations = new ArrayList<HashMap<String, Object>>();
		try {
			JsonParser parser = new JsonParser();
			String jsonResult = WeatherAPIConnector.getLatLong(currentLocation);
			JsonArray jaLatLong = parser.parse(jsonResult).getAsJsonArray();
			JsonObject joLatLong =  jaLatLong.get(0).getAsJsonObject();
			System.out.println(jsonResult);
			double a_lat = joLatLong.get("lat").getAsDouble();
			double a_lon = joLatLong.get("lon").getAsDouble();
			
			
			List<String> cities = getCityCountries();
			int end = n;
			if (cities.size() < n) {
				end = cities.size();
			}
			for(int i = 0; i < cities.size(); i++) {
				if(locations.size() == end) {
					break;
				}
				SearchForecast sf = WeatherAPIConnector.getSearchForecast(cities.get(i), WeatherAPIConnector.getCurrentDate());
				float temp = sf.getTemperatureRange();
				System.out.println("temp: " + temp);
				System.out.println("city: " + cities.get(i));
				if(temp >= lowTemp && temp <= highTemp) {
					HashMap<String, Object> ret = new HashMap<String, Object>();
					String[] cc = cities.get(i).split(",");
					ret.put("city", cc[0]);
					ret.put("country", cc[1]);
					ret.put("currentTemp", (int)sf.getTemperatureRange());
					ret.put("avgMinTemp", (int)sf.getTemperatureRange() - 26);
					ret.put("avgMaxTemp", (int)sf.getTemperatureRange() + 26);
					ret.put("favorite", false);
					
					jsonResult = WeatherAPIConnector.getLatLong(cc[0]);
					try {
						jaLatLong = parser.parse(jsonResult).getAsJsonArray();
						joLatLong =  jaLatLong.get(0).getAsJsonObject();
						double b_lat = joLatLong.get("lat").getAsDouble();
						double b_lon = joLatLong.get("lon").getAsDouble();
						double d = WeatherAPIConnector.haversineDistance(a_lat, a_lon, b_lat, b_lon);
						ret.put("distance", (int)d);
						locations.add(ret);
					}
					catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return locations;
	}
	
	

}
