package com.wfe.gui;

import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Ingredient {
	
	public Rect rect;
	//private GUIText nameText;
	
	private Item item;
	
	private int current;
	private int required;
	
	public Ingredient(Rect rect) {
		this.rect = rect;
		//this.nameText = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, Display.getWidth(), false);
		//this.nameText.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public void render() {
		if(item != null) {
			GUIRenderer.render(item.icon, rect.x, rect.y, 0, rect.width, rect.height, false);
		}
	}
	
	public void renderText() {
		if(item != null) {
			//FontRenderer.render(nameText, rect.x + rect.width + 5, rect.y);
		}
	}
	
	public void setItem(Item item, int current, int required) {
		this.item = item;
		this.current = current;
		this.required = required;
		if(item != null) {
			//this.nameText.setText(item.name + " (" + current + "/" + required + ")");
		}
	}
	
	public boolean isCompleted() {
		return current >= required;
	}

}
