package csci310;

import java.util.Date;
import java.util.List;

public class AnalysisForecast extends SearchForecast {
	private static List<FutureForecast> next5Days;
	private static List<WeatherHistory> historicalTemps;
	public AnalysisForecast(String _location, Date _date, float _temp, List<FutureForecast> _n5D, List<WeatherHistory> _hTs) {
		super(_location, _date, _temp);
		next5Days = _n5D;
		historicalTemps = _hTs;
	}
	
	public List<FutureForecast> getNext5Days(){
		return next5Days;
	}
	public List<WeatherHistory> getHistoricalTemps(){
		return historicalTemps;
	}
	
}
