package com.wfe.gui;

import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUITexture {
	
	public final Rect rect;
	private Texture texture;
	private Color color;
	
	private boolean centered = false;
	private boolean isSolidColor = false;
	
	public GUITexture(Texture texture, Rect rect, boolean centered) {
		this.texture = texture;
		this.rect = rect;
		this.centered = centered;
	}
	
	public GUITexture(Color color, Rect rect, boolean centered) {
		this.color = color;
		this.rect = rect;
		this.centered = centered;
		this.isSolidColor = true;
	}
	
	public GUITexture(Texture texture) {
		this(texture, new Rect(0, 0, 0, 0), false);
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

	public boolean isCentered() {
		return centered;
	}

	public boolean isSolidColor() {
		return isSolidColor;
	}
	
}
