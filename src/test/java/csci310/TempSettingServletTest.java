package csci310;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import csci310.servlet.TempSettingServlet;
import csci310.servlet.favorites.FavoritesAddServlet;
import csci310.servlet.favorites.FavoritesListServlet;
import csci310.servlet.favorites.FavoritesRemoveServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TempSettingServletTest {

    // Multiple requests and responses are mocked to allow the tests to touch multiple endpoints
    @Mock
    HttpServletRequest request1;
    @Mock
    HttpServletResponse response1;
    @Mock
    HttpServletRequest request2;
    @Mock
    HttpServletResponse response2;

    // Only one session is mocked because all requests and responses should be tested as coming from the same session
    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDefaultGet() throws IOException, ServletException {
        when(request1.getSession()).thenReturn(session);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response1.getWriter()).thenReturn(pw);

        new TempSettingServlet().doGet(request1, response1);

        //Verify interaction with the session
        verify(session).getAttribute("tempUnits");

        String result = sw.getBuffer().toString().trim();

        JsonObject expectedResponse = new JsonObject();
        expectedResponse.add("tempUnits", new JsonPrimitive("F")); // Verify default value
        JsonParser parser = new JsonParser();
        Assert.assertEquals(expectedResponse, parser.parse(result));
    }

    @Test
    public void testModify() throws IOException, ServletException {

        // Session mocking properties
        when(session.getAttribute("tempUnits")).thenReturn(null);

        // Add the object with the addition endpoint
        JsonObject postBody = new JsonObject();
        postBody.addProperty("tempUnits", "C");

        when(request1.getSession()).thenReturn(session);
        when(request2.getSession()).thenReturn(session);

        when(request1.getReader()).thenReturn(new BufferedReader(new StringReader(postBody.toString())));

        new TempSettingServlet().doPost(request1, response1);

        verify(session).getAttribute("tempUnits");
        verify(session).setAttribute("tempUnits", "C");
        when(session.getAttribute("tempUnits")).thenReturn("C");

        // Verify list contents with list endpoint
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response2.getWriter()).thenReturn(pw);
        new TempSettingServlet().doGet(request2, response2);

        String result = sw.getBuffer().toString().trim();

        JsonObject expectedResponse = new JsonObject();
        expectedResponse.add("tempUnits", new JsonPrimitive("C"));
        JsonParser parser = new JsonParser();
        Assert.assertEquals(expectedResponse, parser.parse(result));
    }

    @Test
    public void testInvalidModify() throws IOException, ServletException {

        // Session mocking properties
        when(session.getAttribute("tempUnits")).thenReturn(null);

        // Add the object with the addition endpoint
        JsonObject postBody = new JsonObject();
        postBody.addProperty("tempUnits", "invalid unit!!!");

        when(request1.getSession()).thenReturn(session);

        when(request1.getReader()).thenReturn(new BufferedReader(new StringReader(postBody.toString())));

        new TempSettingServlet().doPost(request1, response1);

        verify(response1).setStatus(400);
    }
}
