package csci310;

public class WeatherHistory {
	private static float low;
	private static float high;
	
	public WeatherHistory(float _low, float _high) {
		low = _low;
		high = _high;
	}
	
	public float getLow() {
		return low;
	}
	public float getHigh() {
		return high;
	}
}
