package com.wfe.gui;

import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class Slot {

	private float x, y;
	private float scaleX, scaleY;
	private Texture texture;
	private Item item;
	private boolean hasItem;
	
	public Slot(float x, float y, float scaleX, float scaleY, Texture texture) {
		this.x = x;
		this.y = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.texture = texture;
	}
	
	public void render() {
		GUIRenderer.render(texture, x, y, 0, scaleX, scaleY, false);
		if(hasItem) {
			GUIRenderer.render(item.icon, x + 2.5f, y + 2.5f, 0, scaleX - 5, scaleY - 5, false);
		}
	}
	
	public void addItem(Item item) {
		this.item = item;
		this.hasItem = true;
	}
	
	public void removeItem() {
		this.item = null;
		this.hasItem = false;	
	}
	
	public Item getItem() {
		return item;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public boolean isHasItem() {
		return hasItem;
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > x && Mouse.getX() < x + scaleX &&
				Mouse.getY() > y && Mouse.getY() < y + scaleY;
	}
	
}
