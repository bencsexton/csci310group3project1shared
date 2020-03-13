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
import csci310.Locations;

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
		String activityCategory = null;
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
			float tempLow;
			float tempHigh;
			if(activityCategory.equals("hot")) {
				tempLow = (float) 80.0;
				tempHigh = (float) 200;
			}
			else if(activityCategory.equals("temperate")) {
				tempLow = (float) 80.0;
				tempHigh = (float) 40.0;
			}
			else {
				tempLow = (float) -200.0;
				tempHigh = (float) 40;
			}
			List<HashMap<String, Object>> h = Locations.getValidCities(tempLow, tempHigh, numResults, currentLocation);
			HashMap<String, Object> deg = new HashMap<String, Object>();
			deg.put("farenheit", h);
			deg.put("celsius", h);
			HashMap<String, Object> results = new HashMap<String, Object>();
//			results.put("results", )
//			JsonReader.setLenient(true);
			responseObject.put("results", deg);
		}
		String responseString = "";
		Gson gson = new Gson();
		try {
			responseString = gson.toJson(responseObject);
		}
		catch(Exception e) {response.getWriter().write("error");}
		response.getWriter().write(responseString);
		
	}
}
