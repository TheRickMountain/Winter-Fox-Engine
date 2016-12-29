package com.wfe.gui;

import com.wfe.input.Mouse;

public class GUIButton extends GUIComponent {

	private GUITexture texture;
	private float scaleX, scaleY;
	
	public GUIButton(GUITexture texture) {
		this.texture = texture;
		this.scaleX = texture.getScaleX();
		this.scaleY = texture.getScaleY();
	}
	
	@Override
	public void update() {
		Mouse.setActiveInGUI(false);
		
		if(Mouse.getX() > texture.getX() - texture.getScaleX() / 2 && Mouse.getX() < texture.getX() + texture.getScaleX() / 2 &&
				Mouse.getY() > texture.getY() - texture.getScaleY() / 2 && Mouse.getY() < texture.getY() + texture.getScaleY() / 2) {
			Mouse.setActiveInGUI(true);
			texture.setScale(scaleX + 10, scaleY + 10);
		} else {
			texture.setScale(scaleX, scaleY);
		}
	}

}
