package com.wfe.utils;

import java.util.Random;

public class MyRandom {
	
	private static Random rand = new Random();
	
	public static float nextFloat(float min, float max) {
		return min + (max - min) * rand.nextFloat();
	}

}
