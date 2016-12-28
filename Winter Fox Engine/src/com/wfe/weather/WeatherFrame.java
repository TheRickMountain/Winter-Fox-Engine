package com.wfe.weather;

import com.wfe.math.Vector3f;

public class WeatherFrame {

	private float time;
	private Vector3f sunLightColor;
	
	protected WeatherFrame(float time, Vector3f sunLightColor) {
		this.time = time;
		this.sunLightColor = sunLightColor;
	}
	
	protected static float getTimeFactor(WeatherFrame frame1, WeatherFrame frame2, float currentTime) {
		float full = frame2.time - frame1.time;
		float progress = currentTime - frame1.time;
		return progress / full;
	}
	
	protected static Vector3f getInterpolatedSunLightColour(WeatherFrame frame1, WeatherFrame frame2,
			float timeFactor){
		return interpolateColor(frame1.sunLightColor,frame2.sunLightColor,timeFactor);
	}
	
	protected float getTime() {
		return time;
	}
	
	private static Vector3f interpolateColor(Vector3f color1, Vector3f color2, float timeFactor) {
		float r1 = color1.getX() * (1-timeFactor);
		float g1 = color1.getY() * (1-timeFactor);
		float b1 = color1.getZ() * (1-timeFactor);
		float r2 = color2.getX() * timeFactor;
		float g2 = color2.getY() * timeFactor;
		float b2 = color2.getZ() * timeFactor;
		return new Vector3f(r1+r2,g1+g2,b1+b2);
	}
	
}
