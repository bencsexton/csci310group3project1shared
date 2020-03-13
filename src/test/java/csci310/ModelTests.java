package csci310;

import csci310.model.AnalysisForecast;
import csci310.model.FutureForecast;
import csci310.model.SearchForecast;
import csci310.model.WeatherHistory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Tests the functionality of all data model objects used on the backend server. Because these objects have very little
 * functionality of their own, we have decided to group the tests for ease of readibility and for conciseness.
 * The models include:
 *   SearchForecast
 *   FutureForecast
 *   AnalysisForecast
 *   WeatherHistory
 */
public class ModelTests {

    @Test
    public void testSearchForecast() {
        SearchForecast sf = new SearchForecast("San Francisco", new Date(1584050886L), 86.5f);
        Assert.assertEquals(1584050886L, sf.getDate().getTime());
        Assert.assertEquals("San Francisco", sf.getLocation());
        Assert.assertEquals(86.5f, sf.getTemperatureRange(), 1E-7);
    }

    @Test
    public void testFutureForecast() {
        FutureForecast ff = new FutureForecast(new Date(1584050886L), 86.5f, 84.5f, "Description", "iconpath");
        Assert.assertEquals(1584050886L, ff.getDate().getTime());
        Assert.assertEquals(86.5f, ff.getMaxTemp(), 1E-7);
        Assert.assertEquals(84.5f, ff.getMinTemp(), 1E-7);
        Assert.assertEquals("Description", ff.getDesc());
        Assert.assertEquals("iconpath", ff.getIcon());
    }

    @Test
    public void testWeatherHistory() {
        WeatherHistory wh = new WeatherHistory( 42.5f, 46.5f);
        Assert.assertEquals(46.5f, wh.getHigh(), 1E-7);
        Assert.assertEquals(42.5f, wh.getLow(), 1E-7);
    }

    @Test
    public void testAnalysisForecast() {
        ArrayList<WeatherHistory> histories = new ArrayList<>(Arrays.asList(
                new WeatherHistory( 42.5f, 46.5f),
                new WeatherHistory(47.0f, 48.0f)
                ));

        ArrayList<FutureForecast> futureForecasts = new ArrayList<>(Arrays.asList(
                new FutureForecast(new Date(1584050886L), 86.5f, 84.5f, "Description", "iconpath"),
                new FutureForecast(new Date(1584050887L), 84.5f, 82.5f, "Description2", "iconpath2")
        ));

        AnalysisForecast af = new AnalysisForecast("San Francisco", new Date(1584050886L), 86.5f, futureForecasts, histories);
        Assert.assertEquals(1584050886L, af.getDate().getTime());
        Assert.assertEquals("San Francisco", af.getLocation());
        Assert.assertEquals(86.5f, af.getTemperatureRange(), 1E-7);

        List<WeatherHistory> histListRef = af.getHistoricalTemps();
        Assert.assertEquals(46.5f, histListRef.get(0).getHigh(), 1E-7);
        Assert.assertEquals(42.5f, histListRef.get(0).getLow(), 1E-7);
        Assert.assertEquals(48.0f, histListRef.get(1).getHigh(), 1E-7);
        Assert.assertEquals(47.0f, histListRef.get(1).getLow(), 1E-7);

        List<FutureForecast> futureListRef = af.getNext5Days();
        Assert.assertEquals(1584050886L, futureListRef.get(0).getDate().getTime());
        Assert.assertEquals(86.5f, futureListRef.get(0).getMaxTemp(), 1E-7);
        Assert.assertEquals(84.5f, futureListRef.get(0).getMinTemp(), 1E-7);
        Assert.assertEquals("Description", futureListRef.get(0).getDesc());
        Assert.assertEquals("iconpath", futureListRef.get(0).getIcon());
        Assert.assertEquals(1584050887L, futureListRef.get(1).getDate().getTime());
        Assert.assertEquals(84.5f, futureListRef.get(1).getMaxTemp(), 1E-7);
        Assert.assertEquals(82.5f, futureListRef.get(1).getMinTemp(), 1E-7);
        Assert.assertEquals("Description2", futureListRef.get(1).getDesc());
        Assert.assertEquals("iconpath2", futureListRef.get(1).getIcon());

    }
}
