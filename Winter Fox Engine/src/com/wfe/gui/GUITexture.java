package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.input.Mouse;
import com.wfe.textures.Texture;

public class GUITexture {
	
	private Texture texture;
	private float x, y;
	private float rot;
	private float scaleX, scaleY;
	private boolean centered = false;
	
	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public GUITexture(Texture texture, float x, float y, float rot, float scaleX, float scaleY, boolean centered) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.rot = rot;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.centered = centered;
	}
	
	public GUITexture(Texture texture) {
		this(texture, 0, 0, 0, 0, 0, false);
	}

	public void update() {
		for(GUIComponent component : components) {
			component.update();
		}
	}
	
	public Texture getTexture() {
		return texture;
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

	public float getRot() {
		return rot;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
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
		return Mouse.getX() > x - scaleX / 2 && Mouse.getX() < x + scaleX / 2 &&
				Mouse.getY() > y - scaleX / 2 && Mouse.getY() < y + scaleY / 2;
	}

}
