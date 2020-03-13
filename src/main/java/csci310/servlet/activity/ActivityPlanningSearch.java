package csci310.servlet.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import csci310.WeatherAPIConnector;

/**
 * Servlet implementation class ActivityPlanningSearch
 */
public class ActivityPlanningSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String paramNames[] = {"activity", "numResults", "location"};

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		ArrayList<String> params = new ArrayList<String>();
		for(String param : paramNames) {
			params.add(request.getParameter(param).trim());
		}
		HashSet<String> errors = new HashSet<String>();
		for(int i = 0; i < 3; i++) {
			if (params.get(i).isEmpty()) {
				errors.add(paramNames[i]);
			}
		}
		String activityCategory;
		if (!params.get(0).isEmpty()) {
			activityCategory = Activities.getCategory(params.get(0));
			if(activityCategory.equals("none")) {
				errors.add(paramNames[0]);
			}
		}
		
		boolean numResultsIsAnInt = true;
		int numResults = 0;
		try {
			Integer.parseInt(params.get(1));
		}
		catch(NumberFormatException nfe) {
			numResultsIsAnInt = false;
		}
		if(numResultsIsAnInt) {
			numResults = Integer.parseInt(params.get(1));
			if(numResults < 0) {
				errors.add(paramNames[1]);
			}
		}
		else {
			errors.add(paramNames[1]);
		}
		
		ArrayList<String> errorList = new ArrayList<String>();
		for(String e : errors) {
			errorList.add(e);
		}
		if(errorList.size() > 0) {
			responseObject.put("success", false);
			responseObject.put("errors", errorList);
		}
		else {
			// valid
			responseObject.put("success", true);
			String currentLocation = params.get(2);
			// get lat/long of current city - currentLocation 
			JsonParser parser = new JsonParser();
			String jsonResult = WeatherAPIConnector.getLatLong(params.get(2));
			JsonArray jaLatLong = parser.parse(jsonResult).getAsJsonArray();
			JsonObject joLatLong =  jaLatLong.get(0).getAsJsonObject();
			double a_lat = joLatLong.get("lat").getAsDouble();
			double a_lon = joLatLong.get("lon").getAsDouble();
			
			
			String filename = "\\src\\test\\java\\csci310\\cities.txt";
			File file = new File(filename); 
			List<String> cityList = new ArrayList<String>();
			try {
				BufferedReader br = new BufferedReader(new FileReader(file)); 
				  
				String st = "";
				br.readLine();
				st = br.readLine();
				
				while ((st = br.readLine()) != null) {
					String[] spl = st.split(",");
					String city = spl[0];
					cityList.add(city);
				}
			}
			catch(IOException e) {
				System.out.println(e);
			}
			for(String city : cityList) {
				jsonResult = WeatherAPIConnector.getLatLong(city);
				jaLatLong = parser.parse(jsonResult).getAsJsonArray();
				joLatLong =  jaLatLong.get(0).getAsJsonObject();
				double b_lat = joLatLong.get("lat").getAsDouble();
				double b_lon = joLatLong.get("lon").getAsDouble();
				
				/* a_lat/a_lon is the center cities' coords
				 * b_lat/b_lon is test cities' coords, the one we want distance of 
				 * I don't know what you want to do with this distance
				 */
				double d = WeatherAPIConnector.haversineDistance(a_lat, a_lon, b_lat, b_lon);
			}
		}
		
		Gson gson = new Gson();
		String responseString = gson.toJson(responseObject);
		response.getWriter().write(responseString);
	}
}
