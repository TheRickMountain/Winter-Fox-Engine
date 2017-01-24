package com.wfe.gui;

import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class ProgressBar implements GUIElement{

	public Rect rect;
	public Color color;
	
	private int maxValue = 100;
	private int currentValue;
	
	private float spriteSizeOnePercent;
	
	public ProgressBar(Rect rect, Color color) {
		this.rect = rect;
		this.color = color;
		
		currentValue = maxValue;
		spriteSizeOnePercent = rect.width / (float)maxValue;
	}
	
	@Override
	public void render() {
		GUIRenderer.render(color, rect.x, rect.y, 0, rect.width, rect.height, false);
	}

	@Override
	public void renderText() {
		
	}
	
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
		rect.width = this.currentValue * spriteSizeOnePercent;
	}

}
