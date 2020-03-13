package csci310;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.*;

import csci310.model.AnalysisForecast;
import csci310.model.FutureForecast;
import csci310.model.SearchForecast;
import csci310.model.WeatherHistory;

public class WeatherAPIConnector {
	private static HttpURLConnection connection;
	private static String darkSkyKey = "7c9bbcb42f3bd632b00f1f675d44b9db";
	private static String geocodingKey = "5852d9a8de2a42";
	
	// MAIN IS PURELY FOR TESTING PURPOSES
	public static void main(String[] args) {
		/*
		String latlong = getLatLong("San Francisco");
		String date = getCurrentDate();
		JsonElement je = JsonParser.parseString(latlong);
		JsonArray ja = je.getAsJsonArray();
		JsonObject jo = (JsonObject) ja.get(0);
		String latitude = jo.get("lat").getAsString();
		String longitude = jo.get("lon").getAsString();
		System.out.println(latitude);
		System.out.println(longitude);
		String currentweatherdata = makeSearch(date, latitude, longitude);
		*/
		SearchForecast sf = getSearchForecast("san francisco", getCurrentDate());
		System.out.println(sf.getLocation());
		System.out.println(sf.getTemperatureRange());
		System.out.println(formatDate(sf.getDate()));
		Date d = getDateFromString(getCurrentDate());
	}
	
	// Turns location/city name into latitude and longitude as input for API calls
	public static String getLatLong(String location) {
		BufferedReader br;
		String line;
		StringBuffer sb = new StringBuffer();
		// Date Format: "YYYY-MM-DDTHH:MM:SS";
		try {
			//set variables
			URL url = new URL("https://us1.locationiq.com/v1/search.php?key="+geocodingKey+"&q="+location+"&format=json");
			connection = (HttpURLConnection) url.openConnection();
			
			//connection setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			// get status code
			int status = connection.getResponseCode();
			if(status>299) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			} else {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			}
			
		} catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			connection.disconnect();
		}
		return sb.toString();
	}
	
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	// capitalizes first letter of every word in string
	public static String capitalize(String s) {
		String words[]=s.split("\\s");  
	    String capitalizeWord="";  
	    for(String w:words){  
	        String first=w.substring(0,1);  
	        String afterfirst=w.substring(1);  
	        capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
	    }  
	    return capitalizeWord.trim();  
	}
	
	// Returns Date Format: "YYYY-MM-DDTHH:MM:SS";
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		
		// delimit using space and put back together with T in between
		String[] dateStringSplit = dateString.split(" ");
		String ymd = dateStringSplit[0];
		String time = dateStringSplit[1];
		dateString = ymd+"T"+time;
		return dateString;
	}
	
	// returns a Java Date class from a pre-formatted String
	public static Date getDateFromString(String dateString) {
		
		int y = Integer.parseInt(dateString.substring(0, 4));
		int mon = Integer.parseInt(dateString.substring(5, 7));
		int d = Integer.parseInt(dateString.substring(8, 10));
		int h = Integer.parseInt(dateString.substring(11, 13));
		int min = Integer.parseInt(dateString.substring(14, 16));
		int s = Integer.parseInt(dateString.substring(17, 19));
		@SuppressWarnings("deprecation")
		Date date = new Date(y,mon,d,h,min,s);
		return date;
	}
	
	// formats date into correct format
	@SuppressWarnings("deprecation")
	public static String formatDate(Date date) {
		String dateString;
		int y = date.getYear();
		int mon = date.getMonth();
		int day = date.getDate();
		int h = date.getHours();
		int min = date.getMinutes();
		int s = date.getSeconds();
		dateString = y + "-" + String.format("%02d", mon) + "-" + String.format("%02d", day) + "T" + String.format("%02d", h)+":"+String.format("%02d", min) + ":" + String.format("%02d", s);
		return dateString;
	}
	
	// returns JSON
	// Input: location, date
	// Output: errorMsg, results object:
	//   city name
	//   country name
	//   avgMaxTemp
	//
	public static SearchForecast getSearchForecast(String location, String dateString) {
		location = capitalize(location);
		String latlong = getLatLong(location);
		JsonParser parser = new JsonParser();
		JsonElement jeLatLong = parser.parse(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		Date filler = new Date(0);
		SearchForecast sf = new SearchForecast("error",filler,0);
		try {
			joLatLong = (JsonObject) jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong = (JsonObject) jaLatLong.get(0);
			
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			float temp = joCurrently.get("temperature").getAsFloat();
			Date date = getDateFromString(dateString);
			sf = new SearchForecast(location,date,temp);
		}
		
		return sf;
	}
	
	// returns JSON
	// Input: location, date
	// Output: errorMsg, results object:
	//   city name
	//   country name
	//   avgMaxTemp
	//   avgMinTemp
	//   distance
	//
	public static AnalysisForecast getAnalysisForecast(String location) {
		location = capitalize(location);
		String dateString = getCurrentDate();
		String latlong = getLatLong(location);
		JsonParser parser = new JsonParser();
		JsonElement jeLatLong = parser.parse(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		Date filler = new Date(0);
		List<FutureForecast> n5D = new ArrayList<FutureForecast>();
		List<WeatherHistory> hTs = new ArrayList<WeatherHistory>();
		AnalysisForecast af = new AnalysisForecast("error",filler,0,n5D,hTs);
		try {
			joLatLong = (JsonObject) jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong = (JsonObject) jaLatLong.get(0);
			
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			float temp = joCurrently.get("temperature").getAsFloat();
			Date date = getDateFromString(dateString);
			
			af = new AnalysisForecast(location,date,temp, n5D, hTs);
		}
		
		return af;
	}
	
	/*
	// returns JSON
	public static FutureForecast getFutureForecast(String location, int daysFromNow, String dateString) {
		location = capitalize(location);
		String latlong = getLatLong(location);
		//System.out.println(latlong);
		//String date = getCurrentDate();
		JsonElement jeLatLong = JsonParser.parseString(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		Date filler = new Date(0);
		SearchForecast sf = new SearchForecast("error",filler,0);
		try {
			joLatLong = (JsonObject) jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong = (JsonObject) jaLatLong.get(0);
			
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = JsonParser.parseString(currentWeatherData).getAsJsonObject();
			
			// in 'currently' category
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			float temp = joCurrently.get("temperature").getAsFloat();
			String desc = joCurrently.get("summary").getAsString();
			String icon = joCurrently.get("icon").getAsString();
			
			// in 'daily' category
			JsonObject joDaily = joWeatherData.get("daily").getAsJsonObject();
			JsonArray joData = joWeatherData.get("data").getAsJsonArray();
			float maxT = joCurrently.get("temperature").getAsFloat();
			float minT = joCurrently.get("temperature").getAsFloat();
			
			Date date = getDateFromString(dateString);
			
			ff = new FutureForecast(date,maxT,minT,desc,icon);
		}
		
		return ff;
	}
	*/
	
	
	public static String makeSearch(String date, String latitude, String longitude) {
		BufferedReader br;
		String line;
		StringBuffer sb = new StringBuffer();
		// Date Format: "YYYY-MM-DDTHH:MM:SS";
		try {
			//set variables
			URL url = new URL("https://api.darksky.net/forecast/"+darkSkyKey+"/"+latitude+","+longitude+","+date);
			connection = (HttpURLConnection) url.openConnection();
			
			//connection setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			//System.out.println(status);
			if(status>299) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			} else {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			}
			System.out.println(sb.toString());
			
		} catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			connection.disconnect();
		}
		return sb.toString();
	}
	
}
