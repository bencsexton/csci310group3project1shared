package csci310.servlet.vacation;

import com.google.gson.Gson;

import csci310.Locations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Servlet implementation class VacationPlanningSearch
 */
//@WebServlet(name = "VacationPlanningSearch", urlPatterns = { "/api/vacationPlanning/search" })
public class VacationPlanningSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String paramNames[] = {"tempRangeLow", "tempRangeHigh", "numResults", "location", "precipitation"};
//	private static final String[] parameters = {"tempRangeLow", "tempRangeHigh"};
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public VacationPlanningSearch() {
//        super(); 
//        // TODO Auto-generated constructor stub 
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		ArrayList<String> params = new ArrayList<String>();
		for(String param : paramNames) {
			params.add(request.getParameter(param).trim());
		}
		HashSet<String> errors = new HashSet<String>();
		for(int i = 0; i < 5; i++) {
			if (params.get(i).isEmpty()) {
				errors.add(paramNames[i]);
			}
		}
		if(!errors.contains(paramNames[0]) && !errors.contains(paramNames[1])) {
			int tempRangeLow = Integer.parseInt(params.get(0));
			int tempRangeHigh = Integer.parseInt(params.get(1));
			
			if(tempRangeLow > tempRangeHigh) {
				errors.add(paramNames[0]);
				errors.add(paramNames[1]);
			}
		}
		
		boolean numResultsIsAnInt = true;
		int numResults = 0;
		try {
			Integer.parseInt(params.get(2));
		}
		catch(NumberFormatException nfe) {
			numResultsIsAnInt = false;
		}
		if(numResultsIsAnInt) {
			numResults = Integer.parseInt(params.get(2));
			if(numResults < 0) {
				errors.add(paramNames[2]);
			}
		}
		else {
			errors.add(paramNames[2]);
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
			responseObject.put("success", true);
			
//			List<String> cities = Locations.getCi();
			List<HashMap<String, Object>> h = Locations.getValidCities(Float.parseFloat(params.get(0)), Float.parseFloat(params.get(1)), numResults, params.get(3));
			HashMap<String, Object> deg = new HashMap<String, Object>();
			deg.put("farenheit", h);
			deg.put("celsius", h);
			HashMap<String, Object> results = new HashMap<String, Object>();
//			results.put("results", )
//			JsonReader.setLenient(true);
			responseObject.put("results", deg);
		}
		
		Gson gson = new Gson();
		String responseString = gson.toJson(responseObject);
		response.getWriter().write(responseString);
	}
}






