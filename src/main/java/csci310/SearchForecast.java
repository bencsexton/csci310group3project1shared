package csci310;

import java.util.Date;

public class SearchForecast {
	private static String location;
	private static Date date;
	private static float temp;
	
	public SearchForecast(String _location, Date _date, float _temp) {
		location = _location;
		date = _date;
		temp = _temp;
	}
	
	public float getTemperatureRange() {
		return temp;
	}
	
	public String getLocation() {
		return location;
	}
	public Date getDate() {
		return date;
	}
	
}
