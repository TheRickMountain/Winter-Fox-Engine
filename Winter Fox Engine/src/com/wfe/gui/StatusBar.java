package com.wfe.gui;

import com.wfe.math.Vector3f;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class StatusBar {
	
	private GUITexture health;
	private GUITexture icon;
	
	private float maxValue = 100;
	private float currentValue;
	
	private float spriteSizeOnePercent;
	
	protected StatusBar(Texture iconTexture, Vector3f color, float posX, float posY) {
		health = new GUITexture(color, posX, posY, 0, 110, 15, false);
		icon = new GUITexture(iconTexture, posX - 10, posY - 5, 0, 25, 25, false);
		
		currentValue = maxValue;
		spriteSizeOnePercent = health.getScaleX() / maxValue;
	}
	
	protected void update() {
		
	}
	
	protected void render() {
		GUIRenderer.render(health);
		GUIRenderer.render(icon);
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
