package csci310.model;

import java.util.Date;

public class SearchForecast {
	private String location;
	private Date date;
	private float temp;
	
	public SearchForecast(String location, Date date, float temp) {
		this.location = location;
		this.date = date;
		this.temp = temp;
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
