package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIButton implements GUIElement {
	
	public Color color;
	public Rect rect;
	private GUIText text;
	
	public GUIButton(Rect rect, Color color, String text) {
		this.rect = rect;
		this.color = color;
		
		this.text = new GUIText(text, 1.1f, FontRenderer.font, 
				0, 0, (1.0f / Display.getWidth()) * rect.width, true);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public void render() {
		GUIRenderer.render(color, rect.x, rect.y, 0, rect.width, rect.height, false);
	}
	
	public void renderText() {
		FontRenderer.render(text, rect.x, rect.y + (rect.height / 6));
	}

}
