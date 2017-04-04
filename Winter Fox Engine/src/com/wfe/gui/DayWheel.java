package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.core.World;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;

public class DayWheel {
	
	private World world;
	
	private GUITexture wheel;
	
	private Texture arrow;

	private float rotation;
	private static final float MAX_TIME = 24000;
	
	public DayWheel() {
		world = World.getWorld();
		
		wheel = new GUITexture(ResourceManager.getTexture("day_wheel"));		
		wheel.rect.setSize(128, 128);
		arrow = ResourceManager.getTexture("arrow");
		
		updatePosition();
	}
	
	public void update(float dt) {
		rotation = (world.getTime() * 360) / MAX_TIME;
	}
	
	public void render() {
		GUIRenderer.render(wheel);
		GUIRenderer.render(arrow, Color.WHITE, 
				wheel.rect.getXPlusWidthHalf(), wheel.rect.getYPlusHeightHalf(), 
				rotation, 80, 80, true);
	}
	
	public void updatePosition() {
		wheel.rect.setPosition(Display.getWidth() - wheel.rect.width, 0);
	}

}
