package com.wfe.gui;

import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;

public class Slot {

	private float posX, posY;
	private float scaleX, scaleY;
	private Texture texture;
	private Color color;
	private boolean hasTexture = true;
	private GUIText text;
	private Item item;
	private boolean hasItem;
	private int itemsAmount = 0;
	
	public Slot(float posX, float posY, float scaleX, float scaleY, Texture texture) {
		this.posX = posX;
		this.posY = posY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.texture = texture;
		this.text = new GUIText("", 1.1f, FontRenderer.font, 0, 0, 1.0f, false);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public Slot(float posX, float posY, float scaleX, float scaleY, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.color = color;
		this.text = new GUIText("", 1.1f, FontRenderer.font, 0, 0, 1.0f, false);
		this.text.setColor(1.0f, 1.0f, 1.0f);
		this.hasTexture = false;
	}
	
	public void render() {
		if(hasTexture)
			GUIRenderer.render(texture, posX, posY, 0, scaleX, scaleY, false);
		else
			GUIRenderer.render(color, posX, posY, 0, scaleX, scaleY, false);
		
		if(hasItem) {
			GUIRenderer.render(item.icon, posX + scaleX / 2, posY + scaleY / 2, 0, scaleX, scaleY, true);
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
		setItemsAmount(0);
	}
	
	public Item getItem() {
		return item;
	}
	
	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
		updateTextPosition();
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
		updateTextPosition();
	}
	
	public void setPosition(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
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
		return Mouse.getX() > posX && Mouse.getX() < posX + scaleX &&
				Mouse.getY() > posY && Mouse.getY() < posY + scaleY;
	}
	
	public int getItemsAmount() {
		return itemsAmount;
	}
	
	public void setItemsAmount(int itemsAmount) {
		this.itemsAmount = itemsAmount;
		if(itemsAmount <= 1) {
			text.setText("");
		} else {
			text.setText(String.valueOf(itemsAmount));
		}
	}
	
	private void updateTextPosition() {
		this.text.setPosition(posX, posY);
	}
	
}
