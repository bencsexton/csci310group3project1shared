package csci310.model;

import java.util.Date;
import java.util.List;

public class AnalysisForecast extends SearchForecast {
	private List<FutureForecast> next5Days;
	private List<WeatherHistory> historicalTemps;
	public AnalysisForecast(String location, Date date, float temp, List<FutureForecast> n5D, List<WeatherHistory> hTs, String desc, String icon) {
		super(location, date, temp, desc, icon);
		next5Days = n5D;
		historicalTemps = hTs;
	}
	
	public List<FutureForecast> getNext5Days(){
		return next5Days;
	}
	public List<WeatherHistory> getHistoricalTemps(){
		return historicalTemps;
	}
	
}
