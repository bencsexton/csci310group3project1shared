package csci310;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.*;
import csci310.servlet.favorites.FavoritesAddServlet;
import csci310.servlet.favorites.FavoritesListServlet;
import csci310.servlet.favorites.FavoritesRemoveServlet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FavoritesServletTest {

    // Multiple requests and responses are mocked to allow the tests to touch multiple endpoints
    @Mock
    HttpServletRequest request1;
    @Mock
    HttpServletResponse response1;
    @Mock
    HttpServletRequest request2;
    @Mock
    HttpServletResponse response2;
    @Mock
    HttpServletRequest request3;
    @Mock
    HttpServletResponse response3;

    // Only one session is mocked because all requests and responses should be tested as coming from the same session
    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEmptyGet() throws IOException, ServletException {
        when(request1.getSession()).thenReturn(session);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response1.getWriter()).thenReturn(pw);

        new FavoritesListServlet().doGet(request1, response1);

        //Verify interaction with the session
        verify(session).getAttribute("favorites");

        String result = sw.getBuffer().toString().trim();

        JsonObject expectedResponse = new JsonObject();
        expectedResponse.add("favorites", new JsonArray());

        Assert.assertEquals(expectedResponse.toString(), result);
    }

    @Test
    public void testAddAndList() throws IOException, ServletException {

        // Session mocking properties
        HashSet<String> favs = new HashSet<>();
        when(session.getAttribute("favorites")).thenReturn(favs);

        // Add the object with the addition endpoint
        JsonObject postBody = new JsonObject();
        postBody.addProperty("cityId", "San Francisco");

        when(request1.getSession()).thenReturn(session);
        when(request2.getSession()).thenReturn(session);

        when(request1.getReader()).thenReturn(new BufferedReader(new StringReader(postBody.toString())));

        new FavoritesAddServlet().doPost(request1, response1);

        verify(session).getAttribute("favorites");

        // Verify list contents with list endpoint
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response2.getWriter()).thenReturn(pw);
        new FavoritesListServlet().doGet(request2, response2);

        String result = sw.getBuffer().toString().trim();

        JsonObject expectedResponse = new JsonObject();
        expectedResponse.add("favorites", new JsonArray());
        expectedResponse.getAsJsonArray("favorites").add(new JsonPrimitive("San Francisco"));
        JsonParser parser = new JsonParser();
        Assert.assertEquals(expectedResponse, parser.parse(result));
    }

    @Test
    public void testRemoveBasic() throws IOException, ServletException {

        // Session mocking properties
        HashSet<String> favs = new HashSet<>();
        when(session.getAttribute("favorites")).thenReturn(favs);

        // Add the object with the addition endpoint
        JsonObject postBody = new JsonObject();
        postBody.addProperty("cityId", "San Francisco");

        when(request1.getSession()).thenReturn(session);
        when(request2.getSession()).thenReturn(session);
        when(request3.getSession()).thenReturn(session);
        when(request1.getReader()).thenReturn(new BufferedReader(new StringReader(postBody.toString())));
        new FavoritesAddServlet().doPost(request1, response1);


        // Remove the object with the removal endpoint
        when(request2.getReader()).thenReturn(new BufferedReader(new StringReader(postBody.toString())));
        new FavoritesRemoveServlet().doPost(request2, response2);

        // Verify list contents with list endpoint
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response3.getWriter()).thenReturn(pw);
        new FavoritesListServlet().doGet(request3, response3);


        String result = sw.getBuffer().toString().trim();

        JsonObject expectedResponse = new JsonObject();
        expectedResponse.add("favorites", new JsonArray());
        JsonParser parser = new JsonParser();
        Assert.assertEquals(expectedResponse, parser.parse(result));
    }

}
