package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.gui.GUIText;
import com.wfe.gui.Item;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class Element {
	
	private Item item;
	private Texture icon;
	private Texture texture;
	private float posX, posY;
	private float scaleX, scaleY;
	private GUIText text;
	
	public Element(Item item, Texture texture, float posX, float posY, float scaleX, float scaleY) {
		this.item = item;
		this.icon = item.icon;
		this.texture = texture;
		this.posX = posX;
		this.posY = posY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		GUIText text = new GUIText("Winter Fox Engine", 1.1f, 
				FontRenderer.font, 0.875f, 0.0f, 0.125f, true);
		text.setColor(1, 1, 1);
		
		this.text = new GUIText(item.name, 1.1f, FontRenderer.font, 
				(1.0f / Display.getWidth()) * (posX + 50), (1.0f / Display.getHeight()) * (posY + (scaleY / 3)), 
				1.0f, false);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public void render() {
		GUIRenderer.render(texture, posX, posY, 0, scaleX, scaleY, false);
		GUIRenderer.render(icon, posX + 5f, posY + 5f, 0, 30, 30, false);
	}
	
	public void renderText() {
		FontRenderer.render(text);
	}
	
	public Item getItem() {
		return item;
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > posX && Mouse.getX() < posX + scaleX &&
				Mouse.getY() > posY && Mouse.getY() < posY + scaleY;
	}
	
	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;
		this.text.setPosition(
				(1.0f / Display.getWidth()) * (posX + 50), 
				(1.0f / Display.getHeight()) * (posY + (scaleY / 3)));
	}

}
