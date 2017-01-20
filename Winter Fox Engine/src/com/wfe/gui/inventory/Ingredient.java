package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.gui.GUIElement;
import com.wfe.gui.GUIText;
import com.wfe.gui.Item;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Ingredient implements GUIElement {

	public Rect rect;
	public Color backgroundColor;
	public Item item;
	
	private Color red;
	private Color green;
	
	private GUIText text;
	
	public boolean completed = false;
	
	public boolean active = true;
	
	private int required = 0;
	private int current = 0;
	
	public Ingredient(Rect rect, Color backgroundColor, Item item) {
		this.rect = rect;
		this.backgroundColor = backgroundColor;
		this.item = item;
		
		this.red = new Color(227, 60, 57, 255).convert();
		this.green = new Color(86, 198, 46, 255).convert();
		
		this.text = new GUIText("", 0.9f, FontRenderer.font, 0.0f, 0.0f, 
				(1.0f / Display.getWidth()) * rect.width, true)
				.setColor(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void render() {
		if(active) {
			GUIRenderer.render(backgroundColor, rect.x, rect.y, 0, rect.width, rect.height, false);
			GUIRenderer.render(item.icon, rect.x, rect.y, 0, rect.width, rect.width, false);
			if(completed) {
				GUIRenderer.render(green, rect.x, rect.y + rect.height - (rect.height - rect.width), 0, 
						rect.width, rect.height - rect.width, false);
			} else {
				GUIRenderer.render(red, rect.x, rect.y + rect.height - (rect.height - rect.width), 0, 
						rect.width, rect.height - rect.width, false);
			}
		}
	}

	@Override
	public void renderText() {
		if(active) {
			FontRenderer.render(text, rect.x, rect.y + rect.height - (rect.height - rect.width));
		}
	}
	
	public void set(int current, int required) {
		this.current = current;
		this.required = required;
		this.text.setText(current + "/" + required);
		if(current >= required) {
			completed = true;
		} else {
			completed = false;
		}
	}

}
