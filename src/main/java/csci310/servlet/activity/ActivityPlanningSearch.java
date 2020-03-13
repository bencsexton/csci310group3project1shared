package csci310.servlet.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
			
			
			
		}
		
		Gson gson = new Gson();
		String responseString = gson.toJson(responseObject);
		response.getWriter().write(responseString);
	}
}
