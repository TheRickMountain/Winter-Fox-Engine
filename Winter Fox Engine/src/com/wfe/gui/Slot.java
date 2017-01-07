package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class Slot {

	private float x, y;
	private float scaleX, scaleY;
	private Texture texture;
	private GUIText text;
	private Item item;
	private boolean hasItem;
	private int itemsAmount = 0;
	
	private float tempX = 1.0f / Display.getWidth();
	private float tempY = 1.0f / Display.getHeight();
	
	public Slot(float x, float y, float scaleX, float scaleY, Texture texture) {
		this.x = x;
		this.y = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.texture = texture;
		this.text = new GUIText("", 1.2f, FontRenderer.font, 0, 0, 1.0f, false);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public void render() {
		GUIRenderer.render(texture, x, y, 0, scaleX, scaleY, false);
		if(hasItem) {
			GUIRenderer.render(item.icon, x + 2.5f, y + 2.5f, 0, scaleX - 5, scaleY - 5, false);
		}
	}
	
	public void renderText() {
		FontRenderer.render(text);
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
		updateTextPosition();
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		updateTextPosition();
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		updateTextPosition();
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
	
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	public boolean isHasItem() {
		return hasItem;
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > x && Mouse.getX() < x + scaleX &&
				Mouse.getY() > y && Mouse.getY() < y + scaleY;
	}
	
	public int getItemsAmount() {
		return itemsAmount;
	}
	
	public void setItemsAmount(int itemsAmount) {
		this.itemsAmount = itemsAmount;
		if(itemsAmount == 0) {
			text.setText("");
		} else {
			text.setText(String.valueOf(itemsAmount));
		}
	}
	
	private void updateTextPosition() {
		this.text.setPosition(tempX * (x + 5), tempY * y);
	}
	
}
