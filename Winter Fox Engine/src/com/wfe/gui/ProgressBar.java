package com.wfe.gui;

import com.wfe.font.GUIText;
import com.wfe.font.TextRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class ProgressBar {

	public Rect rect;
	public Color frontColor;
	private Color backColor;
	
	private int maxValue;
	private int currentValue;
	
	private float perPixelSize;
	
	private GUIText text;
	
	public ProgressBar(Rect rect, Color color) {
		this.rect = rect;
		this.frontColor = color;
		this.backColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		this.text = new GUIText("", 96);
		
		setMaxValue(100);
	}
	
	public void render() {
		GUIRenderer.render(backColor, rect.x - 2, rect.y - 2, 0, rect.width + 4, rect.height + 4, false);
		GUIRenderer.render(frontColor, rect.x, rect.y, 0, currentValue * perPixelSize, rect.height, false);
	}
	
	public void renderText() {
		text.setPosition(rect.x + 5, rect.y);
		TextRenderer.render(text);
	}
	
	public int getCurrentVaule() {
		return currentValue;
	}
	
	public void setCurrentValue(int value) {
		this.currentValue = value;
		this.text.setText("" + currentValue);
	}
	
	public int getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
		perPixelSize = rect.width / (float)maxValue;
	}
	
}
