package csci310;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import csci310.servlet.search.SearchServlet;

public class SearchServletTest {
	private class ErrorResponse{
		public boolean success;
		public List<String> errors;
	}
	
	private class successResponse {
		public boolean success;
		public List<Map<String, Object> > results; 
	}
    // Multiple requests and responses are mocked to allow the tests to touch multiple endpoints
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    
    @Mock
    HttpSession session;
    
    private Gson gson;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gson = new Gson();
    }

    @Test
    public void testEmptySearch() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        when(request.getParameter("location")).thenReturn("");
        (new SearchServlet()).doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        System.out.println("result: " + result);
        JsonParser jp = new JsonParser();
        JsonObject root = jp.parse(result).getAsJsonObject();
        String errorMsg = root.get("errorMsg").getAsString();
        Assert.assertEquals(errorMsg, "Invalid location. Failed to retrieve weather data.");
    }
    @Test
    public void invalidLocation() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        when(request.getParameter("location")).thenReturn("aaaaaaaaaaaaaaaaaa");
        (new SearchServlet()).doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        System.out.println("result: " + result);
        JsonParser jp = new JsonParser();
        JsonObject root = jp.parse(result).getAsJsonObject();
        String errorMsg = root.get("errorMsg").getAsString();
        Assert.assertEquals(errorMsg, "Invalid location. Failed to retrieve weather data.");
    }
    @Test
    public void validLocation1() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        when(request.getParameter("location")).thenReturn("san francisco");
        (new SearchServlet()).doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        System.out.println("result: " + result);
        JsonParser jp = new JsonParser();
        JsonObject root = jp.parse(result).getAsJsonObject();
        String location = root.get("location").getAsString();
        String temp = root.get("temp").getAsString();
        boolean numIsFloat = true;
		int numResults = 0;
		try {
			Float.parseFloat(temp);
		}
		catch(NumberFormatException nfe) {
			numIsFloat = false;
		}
        Assert.assertEquals(location, "San Francisco");
        Assert.assertEquals(numIsFloat, true);
    }
    @Test
    public void validLocation2() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        when(request.getParameter("location")).thenReturn("New York");
        (new SearchServlet()).doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        System.out.println("result: " + result);
        JsonParser jp = new JsonParser();
        JsonObject root = jp.parse(result).getAsJsonObject();
        String location = root.get("location").getAsString();
        String temp = root.get("temp").getAsString();
        boolean numIsFloat = true;
		int numResults = 0;
		try {
			Float.parseFloat(temp);
		}
		catch(NumberFormatException nfe) {
			numIsFloat = false;
		}
        Assert.assertEquals(location, "New York");
        Assert.assertEquals(numIsFloat, true);
    }
    
}
