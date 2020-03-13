package csci310;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import csci310.servlet.favorites.FavoritesAddServlet;
import csci310.servlet.favorites.FavoritesListServlet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FavoritesServletTest {


    @Mock
    HttpServletRequest request1;
    @Mock
    HttpServletResponse response1;

    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEmptyGet() throws IOException, ServletException {

        when(request1.getParameter("user")).thenReturn("abhinav");
        when(request1.getParameter("password")).thenReturn("passw0rd");
        when(request1.getParameter("rememberMe")).thenReturn("Y");
        when(request1.getSession()).thenReturn(session);


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response1.getWriter()).thenReturn(pw);

        new FavoritesListServlet().doGet(request1, response1);

        //Verify the session attribute value
        verify(session).getAttribute("favorites");

        String result = sw.getBuffer().toString().trim();

        JsonObject expectedResponse = new JsonObject();
        expectedResponse.add("favorites", new JsonArray());

        Assert.assertEquals(expectedResponse.toString(), result);
    }

}
