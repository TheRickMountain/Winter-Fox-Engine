package com.wfe.utils;

import com.wfe.input.Mouse;

public class Rect {

	public float x, y;
	public float width, height;
	
	public Rect(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
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
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > x && Mouse.getX() < x + width &&
				Mouse.getY() > y && Mouse.getY() < y + height;
	}
	
	public String toString() {
		return x + ", " + y + ", " + width + ", " + height;
	}
	
}
