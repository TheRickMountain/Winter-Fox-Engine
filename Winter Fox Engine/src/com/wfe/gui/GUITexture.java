package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.input.Mouse;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;

public class GUITexture {
	
	private Texture texture;
	private Color color;
	private float posX, posY;
	private float rot;
	private float scaleX, scaleY;
	private boolean centered = false;
	private boolean isSolidColor = false;
	public boolean active = true;
	
	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public GUITexture(Texture texture, float posX, float posY, float scaleX, float scaleY, boolean centered) {
		this.texture = texture;
		this.posX = posX;
		this.posY = posY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.centered = centered;
	}
	
	public GUITexture(Color color, float posX, float posY, float scaleX, float scaleY, boolean centered) {
		this.color = color;
		this.posX = posX;
		this.posY = posY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.centered = centered;
		this.isSolidColor = true;
	}
	
	public GUITexture(Texture texture) {
		this(texture, 0, 0, 0, 0, false);
	}

	public void update() {
		for(GUIComponent component : components) {
			component.update();
		}
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(float r, float g, float b) {
		this.color.set(r, g, b);
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getRot() {
		return rot;
	}
	
	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;
	}

	public void setRot(float rot) {
		this.rot = rot;
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

	public boolean isCentered() {
		return centered;
	}
	
	public void addComponent(GUIComponent component) {
		components.add(component);
	}
	
	public boolean isMouseOvered() {
		if(centered) {
			return Mouse.getX() > posX - scaleX / 2 && Mouse.getX() < posX + scaleX / 2 &&
					Mouse.getY() > posY - scaleX / 2 && Mouse.getY() < posY + scaleY / 2;
		} else {
			return Mouse.getX() > posX && Mouse.getX() < posX + scaleX &&
					Mouse.getY() > posY && Mouse.getY() < posY + scaleY;
		}
	}

	public boolean isSolidColor() {
		return isSolidColor;
	}
	
}
