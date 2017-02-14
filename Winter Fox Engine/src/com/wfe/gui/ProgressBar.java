package com.wfe.gui;

import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class ProgressBar {

	public Rect rect;
	public Color frontColor;
	private Color backColor;
	
	private int maxValue = 100;
	private int currentValue;
	
	private float perPixelSize;
	
	public ProgressBar(Rect rect) {
		this.rect = rect;
		frontColor = new Color(0.1f, 0.9f, 0.1f);
		backColor = new Color(0.3f, 0.3f, 0.3f);
		
		perPixelSize = rect.width / (float)maxValue;
	}
	
	public void render() {
		GUIRenderer.render(backColor, rect.x, rect.y, 0, rect.width, rect.height, false);
		GUIRenderer.render(frontColor, rect.x, rect.y, 0, currentValue * perPixelSize, rect.height, false);
	}
	
	public void setCurrentValue(int value) {
		this.currentValue = value;
	}
	
	public int getCurrentVaule() {
		return currentValue;
	}
	
}
