package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class GUIButton {
	
	private Texture texture;
	private float posX, posY;
	private float scaleX, scaleY;
	private GUIText text;
	private boolean renderTexture = true;
	
	public GUIButton(String text, float posX, float posY, float scaleX, float scaleY) {
		this(null, text, posX, posY, scaleX, scaleY);
	}
	
	public GUIButton(Texture texture, String text, float posX, float posY, float scaleX, float scaleY) {
		if(texture == null) {
			renderTexture = false;
		} else {
			this.texture = texture;
		}
		this.posX = posX;
		this.posY = posY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.text = new GUIText(text, 1.2f, FontRenderer.font, 
				(1.0f / Display.getWidth()) * posX, (1.0f / Display.getHeight()) * posY, 1.0f, false);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public void render() {
		if(renderTexture)
			GUIRenderer.render(texture, posX, posY, 0, scaleX, scaleY, false, true);
	}
	
	public void renderText() {
		FontRenderer.render(text);
	}
	
	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;
		
		this.text.setPosition((1.0f / Display.getWidth()) * (posX + scaleX / 4), 
				(1.0f / Display.getHeight()) * (posY + scaleY / 6));
	}
	
	public float getScaleX() {
		return scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > posX && Mouse.getX() < posX + scaleX &&
				Mouse.getY() > posY && Mouse.getY() < posY + scaleY;
	}
	
}
