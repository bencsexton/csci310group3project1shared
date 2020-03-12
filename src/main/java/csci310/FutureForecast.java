package csci310;

import java.util.Date;

public class FutureForecast {
	private static Date date = new Date(0);
	private static float maxTemp;
	private static float minTemp;
	private static String desc;
	private static String icon;
	
	
	public FutureForecast(Date _date, float _maxT, float _minT, String _desc, String _icon) {
		date = _date;
		maxTemp = _maxT;
		minTemp = _minT;
		desc = _desc;
		icon = _icon;
	}
	
	public Date getDate() {
		return date;
	}
	public float getMaxTemp() {
		return maxTemp;
	}
	public float getMinTemp() {
		return minTemp;
	}
	public String getDesc() {
		return desc;
	}
	public String getIcon() {
		return icon;
	}
}
