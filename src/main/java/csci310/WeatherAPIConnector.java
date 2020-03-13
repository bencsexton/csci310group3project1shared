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
	
	/*
	// MAIN IS PURELY FOR TESTING PURPOSES
	public static void main(String[] args) {
		
	}
	*/
	
	// Turns location/city name into latitude and longitude as input for API calls
	public static String getLatLong(String location) {
		BufferedReader br;
		String line;
		StringBuffer sb = new StringBuffer();
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
			
			// check status, return error stream if error
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
	
	// increment Date by n days
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
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
	
	// Returns Date Format: "yyyy-MM-ddTHH:mm:ss"
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
		
		int y = Integer.parseInt(dateString.substring(0, 4))-1900;
		int mon = Integer.parseInt(dateString.substring(5, 7))-1;
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
		int y = date.getYear()+1900;
		int mon = date.getMonth()+1;
		int day = date.getDate();
		int h = date.getHours();
		int min = date.getMinutes();
		int s = date.getSeconds();
		dateString = y + "-" + String.format("%02d", mon) + "-" + String.format("%02d", day) + "T" + String.format("%02d", h)+":"+String.format("%02d", min) + ":" + String.format("%02d", s);
		return dateString;
	}
	
	// returns SearchForecast, containing location, date, and current temperature
	public static SearchForecast getSearchForecast(String location, String dateString) {
		//capitalize location name
		location = capitalize(location);
		
		//obtain latitude/longitude JSON
		String latlong = getLatLong(location);
		
		// get first element in JSON
		JsonParser parser = new JsonParser();
		JsonElement jeLatLong = parser.parse(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		
		// make filler SearchForecast in case of error
		Date filler = new Date(0);
		SearchForecast sf = new SearchForecast("error",filler,0);
		try {
			// an incredibly roundabout way of checking if the city/zip is found
			joLatLong =  jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			// by default a successful API call returns a jsonarray as opposed to a jsonobject
			// although this is poor design, simply checking if it is a json object will cause an exception,
			// which puts us into the catch block, indicating a successful call
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong =  jaLatLong.get(0).getAsJsonObject();
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			
			// make search using lat/lon
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			
			// enter 'currently'
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			float temp = joCurrently.get("temperature").getAsFloat();
			Date date = getDateFromString(dateString);
			
			// create new searchforecast
			sf = new SearchForecast(location,date,temp);
		}
		
		return sf;
	}
	
	// returns AnalysisForecast, which contains same data as SearchForecast but with next 5 days of FutureForecasts and last 12 months of weatherHistory
	public static AnalysisForecast getAnalysisForecast(String location) {
		location = capitalize(location);
		String dateString = getCurrentDate();
		String latlong = getLatLong(location);
		JsonParser parser = new JsonParser();
		JsonElement jeLatLong = parser.parse(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		
		// make filler AnalysisForecast in case of error
		Date filler = new Date(0);
		List<FutureForecast> n5D = new ArrayList<FutureForecast>();
		List<WeatherHistory> hTs = new ArrayList<WeatherHistory>();
		AnalysisForecast af = new AnalysisForecast("error",filler,0,n5D,hTs);
		
		try {
			// an incredibly roundabout way of checking if the city/zip is found
			joLatLong = jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			// by default a successful API call returns a jsonarray as opposed to a jsonobject
			// although this is poor design, simply checking if it is a json object will cause an exception,
			// which puts us into the catch block, indicating a successful call
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong = jaLatLong.get(0).getAsJsonObject();
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			float currentTemp = 0;
			
			// current temperature
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			currentTemp = joCurrently.get("temperature").getAsFloat();
			
			//iterate 5 times for 5 day forecast
			for(int i=0;i<5;i++) {
				FutureForecast ff = getFutureForecast(latitude,longitude, dateString);
				n5D.add(ff);
				dateString = formatDate(addDays(getDateFromString(dateString),1));
			}
			
			//iterate 12 times for 12 months of weather history
			for(int i=1;i<13;i++) {
				WeatherHistory wh = getWeatherHistory(latitude,longitude, i);
				hTs.add(wh);
			}
			
			// get current date and create analysisforecast
			Date currentDate = getDateFromString(getCurrentDate());
			af = new AnalysisForecast(location,currentDate,currentTemp, n5D, hTs);
		}
		
		return af;
	}
	
	// returns FutureForecast, contains high an low for a day with decription and icon for a forecast
	public static FutureForecast getFutureForecast(String latitude, String longitude, String dateString) {
		JsonParser parser = new JsonParser();
		String currentWeatherData = makeSearch(dateString, latitude, longitude);
		JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
		
		// in 'currently' category
		JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
		String desc = joCurrently.get("summary").getAsString();
		String icon = joCurrently.get("icon").getAsString();
		
		// in 'daily' category
		JsonObject joDaily = joWeatherData.get("daily").getAsJsonObject();
		JsonArray joData = joDaily.get("data").getAsJsonArray();
		JsonObject firstData = joData.get(0).getAsJsonObject();
		float maxT = firstData.get("temperatureHigh").getAsFloat();
		float minT = firstData.get("temperatureLow").getAsFloat();
		
		Date date = getDateFromString(dateString);
		FutureForecast ff = new FutureForecast(date,maxT,minT,desc,icon);
		
		return ff;
	}
	
	// returns WeatherHistory, contains high and low for a month
	public static WeatherHistory getWeatherHistory(String latitude, String longitude, int month) {
		float avgHigh = 0;
		float avgLow = 0;
		JsonParser parser = new JsonParser();
		String m = "";
		if(month>=10) {
			m += Integer.toString(month);
		} else {
			m += "0";
			m += Integer.toString(month);
		}
		for(int i=1;i<29;i++) { // runs 28 times, ranging from 1 to 28. This avoids going over february and still gets a good idea of lows/highs for the month
			String dayString = "";
			if(i>=10) {
				dayString += Integer.toString(i);
			} else {
				dayString += "0";
				dayString += Integer.toString(i);
			}
			// Date Format: "YYYY-MM-DDTHH:MM:SS", checks at the end in case there are record highs and lows for the day
			String dateString = "2019-"+m+"-"+dayString+"T23:59:59";
			// Note: this will only work for 2020 looking back a single year, this will only be problematic in later years
			String currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			
			// in 'daily' category
			JsonObject joDaily = joWeatherData.get("daily").getAsJsonObject();
			JsonArray joData = joDaily.get("data").getAsJsonArray();
			JsonObject firstData = joData.get(0).getAsJsonObject();
			float maxT = firstData.get("temperatureMax").getAsFloat();
			float minT = firstData.get("temperatureMin").getAsFloat();
			
			// running total
			avgHigh += maxT;
			avgLow += minT;
		}
		avgHigh = avgHigh/28; //average all 28 days
		avgLow = avgLow/28;
		
		WeatherHistory wh = new WeatherHistory(avgLow,avgHigh);
		
		return wh;
	}
	
	// returns JSON for weather search with lat/lon and date as inputs
	public static String makeSearch(String date, String latitude, String longitude) {
		BufferedReader br;
		String line;
		StringBuffer sb = new StringBuffer();
		// Date Format: "YYYY-MM-DDTHH:MM:SS"
		try {
			//set variables
			URL url = new URL("https://api.darksky.net/forecast/"+darkSkyKey+"/"+latitude+","+longitude+","+date);
			connection = (HttpURLConnection) url.openConnection();
			
			//connection setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			// store connection status
			int status = connection.getResponseCode();
			
			// store error streams in case of unsuccessful connection
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
	
	
}
