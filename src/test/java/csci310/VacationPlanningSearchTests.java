package csci310;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import csci310.servlet.vacation.VacationPlanningSearch;

public class VacationPlanningSearchTests {
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
    public void testAllEmptyFields() throws IOException, ServletException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        String params[] = {"tempRangeLow", "tempRangeHigh", "numResults", "location"};
        for(String p : params) {
        	when(request.getParameter(p)).thenReturn("");
        }

        (new VacationPlanningSearch()).doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        ErrorResponse er = gson.fromJson(result, ErrorResponse.class);
        Set<String> responseErrorSet = new HashSet<String>();
        for(String e : er.errors) {
        	responseErrorSet.add(e);
        }
        Set<String> errors = new HashSet<String>();
        errors.add("tempRangeLow");
        errors.add("tempRangeHigh");
        errors.add("numResults");
        errors.add("location");
        Assert.assertEquals(errors, responseErrorSet);
    }
    @Test
    public void lowTempHigherThanHighTemp() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        when(request.getParameter("tempRangeLow")).thenReturn("69");
        when(request.getParameter("tempRangeHigh")).thenReturn("42");
        when(request.getParameter("numResults")).thenReturn("5");
        when(request.getParameter("location")).thenReturn("Las Vegas");
        
        (new VacationPlanningSearch()).doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        ErrorResponse er = gson.fromJson(result, ErrorResponse.class);
        Set<String> responseErrorSet = new HashSet<String>();
        for(String e : er.errors) {
        	responseErrorSet.add(e);
        }
        Set<String> errors = new HashSet<String>();
        errors.add("tempRangeLow");
        errors.add("tempRangeHigh");
        Assert.assertEquals(errors, responseErrorSet);
    }
    @Test
    public void lowTempLowerThanHighTemp() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        when(request.getParameter("tempRangeLow")).thenReturn("42");
        when(request.getParameter("tempRangeHigh")).thenReturn("43");
        when(request.getParameter("numResults")).thenReturn("");
        when(request.getParameter("location")).thenReturn("Las Vegas");
        
        (new VacationPlanningSearch()).doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        ErrorResponse er = gson.fromJson(result, ErrorResponse.class);
        Set<String> responseErrorSet = new HashSet<String>();
        for(String e : er.errors) {
        	responseErrorSet.add(e);
        }
        Set<String> errors = new HashSet<String>();
        errors.add("numResults");
        Assert.assertEquals(errors, responseErrorSet);
    }
    @Test
    public void nonIntNumResults() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        when(request.getParameter("tempRangeLow")).thenReturn("42");
        when(request.getParameter("tempRangeHigh")).thenReturn("43");
        when(request.getParameter("numResults")).thenReturn("fish food");
        when(request.getParameter("location")).thenReturn("Las Vegas");
        
        (new VacationPlanningSearch()).doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        ErrorResponse er = gson.fromJson(result, ErrorResponse.class);
        Set<String> responseErrorSet = new HashSet<String>();
        for(String e : er.errors) {
        	responseErrorSet.add(e);
        }
        Set<String> errors = new HashSet<String>();
        errors.add("numResults");
        Assert.assertEquals(errors, responseErrorSet);
    }
    @Test
    public void negativeNumResults() throws IOException, ServletException {
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        when(request.getParameter("tempRangeLow")).thenReturn("42");
        when(request.getParameter("tempRangeHigh")).thenReturn("43");
        when(request.getParameter("numResults")).thenReturn("-100");
        when(request.getParameter("location")).thenReturn("Las Vegas");
        
        (new VacationPlanningSearch()).doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        ErrorResponse er = gson.fromJson(result, ErrorResponse.class);
        Set<String> responseErrorSet = new HashSet<String>();
        for(String e : er.errors) {
        	responseErrorSet.add(e);
        }
        Set<String> errors = new HashSet<String>();
        errors.add("numResults");
        Assert.assertEquals(errors, responseErrorSet);
    }
}


















