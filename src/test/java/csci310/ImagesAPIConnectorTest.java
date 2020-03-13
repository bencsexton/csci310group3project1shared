package csci310;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ImagesAPIConnectorTest {

    @Mock
    HttpURLConnection connection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImageParsing() throws IOException {

        JsonObject mockedResponse = new JsonObject();
        mockedResponse.add("items", new JsonArray());

        for (int i = 0; i < 10; i++) {
            JsonObject arrayElem = new JsonObject();
            arrayElem.addProperty("link", "testlinknumber" + i);
            mockedResponse.getAsJsonArray("items").add(arrayElem);
        }

        when(connection.getResponseCode()).thenReturn(200);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(mockedResponse.toString().getBytes()));

        List<String> result = ImagesAPIConnector.getImageLinks(connection);

        Assert.assertEquals(5, result.size()); // Only 5 results should be given
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals("testlinknumber" + i, result.get(i)); // Ensure each one matches
        }
    }
}
