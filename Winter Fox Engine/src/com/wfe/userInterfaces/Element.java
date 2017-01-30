package com.wfe.userInterfaces;

import com.wfe.gui.GUIElement;
import com.wfe.gui.GUIText;
import com.wfe.gui.Item;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Element implements GUIElement {

	public Rect rect;
	public Color backgroundColor;
	public Item item;
	private GUIText text;
	
	public Element(Rect rect, Color backgroundColor, Item item) {
		this.rect = rect;
		this.backgroundColor = backgroundColor;
		this.item = item;
		this.text = new GUIText(this.item.title, 1.1f, FontRenderer.font, 0, 0, 1.0f, false)
				.setColor(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void render() {
		GUIRenderer.render(backgroundColor, rect.x, rect.y, 0, rect.width, rect.height, false);
		GUIRenderer.render(item.icon, rect.x, rect.y, 0, rect.height, rect.height, false);
	}

	@Override
	public void renderText() {
		FontRenderer.render(text, rect.x + rect.height + 5, rect.y);
	}

	public boolean isMouseOvered() {
		return rect.isMouseOvered();
	}
	
}
