package com.wfe.font;

import com.wfe.math.Vector3f;

public class GUIText {

	private String textString;
	private float fontSize;
	
	private int textMeshVao;
	private int vertexCount;
	private Vector3f color = new Vector3f(0, 0, 0);
	
	private float x, y;
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
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
	}
	
	public FontType getFont() {
		return font;
	}
	
	public void setColor(float r, float g, float b) {
		color.set(r, g, b);
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
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getMesh() {
		return textMeshVao;
	}
	
	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}
	
	public int getVertexCount() {
		return this.vertexCount;
	}

	protected float getFontSize() {
		return fontSize;
	}

	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	protected boolean isCentered() {
		return centerText;
	}

	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	protected String getTextString() {
		return textString;
	}
	
}
