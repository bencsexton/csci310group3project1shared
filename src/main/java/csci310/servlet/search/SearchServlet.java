package csci310.servlet.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import csci310.WeatherAPIConnector;
import csci310.model.SearchForecast;
import csci310.servlet.activity.Activities;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String paramNames[] = {"location"};
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		ArrayList<String> params = new ArrayList<String>();
		for(String param : paramNames) {
			params.add(request.getParameter(param).trim());
		}
		HashSet<String> errors = new HashSet<String>();
		for(int i = 0; i < 1; i++) {
			if (params.get(i).isEmpty()) {
				errors.add(paramNames[i]);
			}
		}
		Date filler = new Date();
		SearchForecast sf = new SearchForecast("",filler,0,"","");
		if (!params.get(0).isEmpty()) {
			sf = WeatherAPIConnector.getSearchForecast(params.get(0),WeatherAPIConnector.getCurrentDate());
			if(sf.getLocation().contentEquals("")) {
				errors.add(paramNames[0]);
			}
		}
		Map<String, String> out = new HashMap<String, String>();
		if(errors.size()>0) {
			out.put("errorMsg", "Invalid location. Failed to retrieve weather data.");
		}else {
			out.put("temp", Float.toString(sf.getTemperatureRange()));
			out.put("location", sf.getLocation());
			out.put("desc", sf.getDesc());
			out.put("icon", sf.getIcon());
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(out);
		response.getWriter().write(json);
	}

}
