package com.wfe.gui;

import com.wfe.font.FontType;
import com.wfe.font.TextMeshData;
import com.wfe.graph.Vao;
import com.wfe.math.Vector3f;

public class GUIText {

	private String textString;
	private float fontSize;
	
	private Vao vao;
	
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
	
	public void setVao(Vao vao) {
		this.vao = vao;
	}
	
	public Vao getVao() {
		return vao;
	}
	
	public void setText(String text) {
		this.textString = text;
		
		rebuild();
	}
	
	public void cleanup() {
		vao.delete();
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
		TextMeshData data = font.loadText(this);
		Vao vao = Vao.create();
		vao.bind();
		vao.createFloatAttribute(0, data.getVertexPositions(), 2);
		vao.createFloatAttribute(1, data.getTextureCoords(), 2);
		vao.setVertexCount(data.getVertexPositions().length / 2);
		vao.unbind();
		setVao(vao);
	}
	
}
