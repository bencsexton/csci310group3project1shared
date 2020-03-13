package csci310;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import csci310.servlet.HelloServlet;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HelloServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHello() throws IOException, ServletException {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        new HelloServlet().doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        Assert.assertEquals("Hello", result);
    }

}
