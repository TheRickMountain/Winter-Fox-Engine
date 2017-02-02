package com.wfe.gui;

import com.wfe.font.FontType;
import com.wfe.graph.Mesh;
import com.wfe.math.Vector3f;

public class GUIText {

	private String textString;
	private float fontSize;
	
	private Mesh mesh;
	
	private Vector3f color = new Vector3f(0, 0, 0);
	
	private float x, y;
	private float scaleX, scaleY;
	private float lineMaxSize;
	private int numberOfLines;
	
	private FontType font;
	
	private boolean centerText = false;
	
	public GUIText(String text, float fontSize, FontType font, float x, float y, float maxLineLength,
			boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.x = x;
		this.y = y;
		this.scaleX = 1;
		this.scaleY = 1;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		
		rebuild();
	}
	
	public FontType getFont() {
		return font;
	}
	
	public GUIText setColor(float r, float g, float b) {
		color.set(r, g, b);
		return this;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public int getNumberOfLines() {
		return numberOfLines;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
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
	
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public void setText(String text) {
		if(!this.textString.equals(text)) {
			this.textString = text;
			rebuild();
		}
	}
	
	public void cleanup() {
		mesh.delete();
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	public boolean isCentered() {
		return centerText;
	}

	public float getMaxLineSize() {
		return lineMaxSize;
	}

	public String getTextString() {
		return textString;
	}
	
	private void rebuild() {
		this.mesh = font.loadText(this);
	}
	
}
