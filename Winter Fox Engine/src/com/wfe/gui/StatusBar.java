package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.game.World;
import com.wfe.math.Vector3f;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class StatusBar {
	
	private float x, y;
	
	private GUITexture line;
	private GUITexture icon;
	private GUIText text;
	
	private int maxValue = 100;
	private int currentValue;
	
	private float spriteSizeOnePercent;
	
	private float screenSizeXPerPixel;
	private float screenSizeYPerPixel;
	
	protected StatusBar(Texture iconTexture, Vector3f color, float posX, float posY) {
		this.x = posX;
		this.y = posY;
		line = new GUITexture(color, posX, posY, 0, 110, 15, false);
		icon = new GUITexture(iconTexture, posX - 10, posY - 5, 0, 25, 25, false);
		text = new GUIText(maxValue + "/" + maxValue, 1.1f, FontRenderer.font, 0, 0, 1.0f, false);
		text.setColor(1.0f, 1.0f, 1.0f);
		World.getWorld().addGUIText(text);
		
		setPosition();
		
		currentValue = maxValue;
		spriteSizeOnePercent = line.getScaleX() / (float)maxValue;
	}
	
	protected void update() {
		if(Display.isResized()) {
			setPosition();
		}
	}
	
	protected void render() {
		GUIRenderer.render(line);
		GUIRenderer.render(icon);
	}
	
	public void increase(int value) {
		currentValue += value;
		if(currentValue > maxValue)
			currentValue = maxValue;
		
		line.setScaleX(currentValue * spriteSizeOnePercent);
		updateText();
	}
	
	public void decrease(int value) {
		currentValue -= value;
		if(currentValue < 0)
			currentValue = 0;
		
		line.setScaleX(currentValue * spriteSizeOnePercent);
		updateText();
	}
	
	private void updateText() {
		text.setText(currentValue + "/" + maxValue);
	}
	
	private void setPosition() {
		screenSizeXPerPixel = 1.0f / (float)Display.getWidth();
		screenSizeYPerPixel = 1.0f / (float)Display.getHeight();
		text.setPosition((x + 20) * screenSizeXPerPixel, (y - 2.5f) * screenSizeYPerPixel);
	}
	
	public boolean isFull() {
		return currentValue == maxValue;
	}
	
	public int getCurrentValue() {
		return currentValue;
	}
	
	public int getMaxValue() {
		return maxValue;
	}

}
