package com.wfe.weather;

import com.wfe.math.Vector3f;

public class Weather {

	private WeatherFrame[] weatherFrames = {
		new WeatherFrame(0, new Vector3f(0, 0, 0.1f)),
		new WeatherFrame(500, new Vector3f(0, 0, 0.1f)),
		new WeatherFrame(3000, new Vector3f(0, 0, 0.1f)),
		new WeatherFrame(4000, new Vector3f(1.2f, 0.1f, 0.2f)),
		new WeatherFrame(7500, new Vector3f(1, 1, 0.9f)),
		new WeatherFrame(12000, new Vector3f(1.1f, 1.1f, 0.9f)),
		new WeatherFrame(16500, new Vector3f(1, 1, 0.9f)),
		new WeatherFrame(21000, new Vector3f(0.94f, 0.23f, 0)),
		new WeatherFrame(23500, new Vector3f(0, 0, 0.1f)),
		new WeatherFrame(24001, new Vector3f(0, 0, 0.1f))
	};
	
	public void updateWeather(float time, float delta) {
		interpolateOtherVariables(time, delta);
	}
	
	private void interpolateOtherVariables(float time, float delta) {
		WeatherFrame frame1 = weatherFrames[0];
		WeatherFrame frame2 = null;
		int pointer = 1;
		while(true) {
			frame2 = weatherFrames[pointer++];
			if(time < frame2.getTime()) {
				break;
			} else {
				frame1 = frame2;
			}
		}
		float timeFactor = WeatherFrame.getTimeFactor(frame1, frame2, time);
		updateSunColor(timeFactor, frame1, frame2);
	}
	
	private void updateSunColor(float timeFactor, WeatherFrame frame1, WeatherFrame frame2) {
		DirectionalLight.LIGHT_COLOR = WeatherFrame.getInterpolatedSunLightColour(frame1, frame2, timeFactor);
	}
	
}
