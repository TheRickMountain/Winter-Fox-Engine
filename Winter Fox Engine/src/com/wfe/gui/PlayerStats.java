package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.renderEngine.GUIRenderer;

public class PlayerStats {
	
	private GUITexture health;
	
	private float maxValue = 100;
	private float currentValue;
	
	private float spriteSizeOnePercent;
	
	protected PlayerStats() {
		health = new GUITexture(ResourceManager.getTexture("health_ui"), 10, 10, 0, 110, 15, false);
		
		currentValue = maxValue;
		spriteSizeOnePercent = health.getScaleX() / maxValue;
	}
	
	protected void update() {
		
	}
	
	protected void render() {
		GUIRenderer.render(health);
	}
	
	public void increase(int value) {
		currentValue += value;
		if(currentValue > maxValue)
			currentValue = maxValue;
		
		health.setScaleX(currentValue * spriteSizeOnePercent);
	}
	
	public void decrease(int value) {
		currentValue -= value;
		if(currentValue < 0)
			currentValue = 0;
		
		health.setScaleX(currentValue * spriteSizeOnePercent);
	}

}
