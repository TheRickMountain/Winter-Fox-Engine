package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;
import com.wfe.input.Mouse;
import com.wfe.textures.Texture;

public class GUIFrame {
	
	private static final int LEFT_TOP_CORNER = 0, RIGHT_TOP_CORNER = 1, LEFT_BOTTOM_CORNER = 2, RIGHT_BOTTOM_CORNER = 3;
	private static final int LEFT_EDGE = 4, RIGHT_EDGE = 5, TOP_EDGE = 6, BOTTOM_EDGE = 7;
	private static final int BACKGROUND = 8;
	private static final int RIM_SIZE = 0;
	private static Texture cornerTexture = ResourceManager.getTexture("corner_frame_ui");
	private static Texture hEdgeTexture = ResourceManager.getTexture("background_frame_ui");
	private static Texture vEdgeTexture = ResourceManager.getTexture("background_frame_ui");
	private static Texture backgroundTexture = ResourceManager.getTexture("background_frame_ui");
	private float posX;
	private float posY;
	private float scaleX;
	private float scaleY;
	
	private List<GUITexture> frameTextures = new ArrayList<GUITexture>();
	
	public GUIFrame(float x, float y, float scaleX, float scaleY) {
		this.posX = x;
		this.posY = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		createFrame();
		setTexturePositions();
		setTextureRotations();
		setTextureSizes();
	}
	
	private void createFrame() {
		frameTextures.add(new GUITexture(cornerTexture));
		frameTextures.add(new GUITexture(cornerTexture));
		frameTextures.add(new GUITexture(cornerTexture));
		frameTextures.add(new GUITexture(cornerTexture));
		
		frameTextures.add(new GUITexture(vEdgeTexture));
		frameTextures.add(new GUITexture(vEdgeTexture));
		frameTextures.add(new GUITexture(hEdgeTexture));
		frameTextures.add(new GUITexture(hEdgeTexture));
		
		frameTextures.add(new GUITexture(backgroundTexture));
	}
	
	private void setTexturePositions() {
		float xPlusXScaleXMinusRim = posX + (scaleX - RIM_SIZE);
		float yPlusYScaleYMinusRim = posY + (scaleY - RIM_SIZE);
		float xPlusRim = posX + RIM_SIZE;
		float yPlusRim = posY + RIM_SIZE;
		
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).setPosition(posX, posY);	
		frameTextures.get(RIGHT_TOP_CORNER).setPosition(xPlusXScaleXMinusRim, posY);	
		frameTextures.get(LEFT_BOTTOM_CORNER).setPosition(posX, yPlusYScaleYMinusRim);
		frameTextures.get(RIGHT_BOTTOM_CORNER).setPosition(xPlusXScaleXMinusRim, yPlusYScaleYMinusRim);
		
		// Edges	
		frameTextures.get(LEFT_EDGE).setPosition(posX, yPlusRim);
		frameTextures.get(RIGHT_EDGE).setPosition(xPlusXScaleXMinusRim, yPlusRim);	
		frameTextures.get(TOP_EDGE).setPosition(xPlusRim, posY);
		frameTextures.get(BOTTOM_EDGE).setPosition(xPlusRim, yPlusYScaleYMinusRim);
		
		// Background
		frameTextures.get(BACKGROUND).setPosition(xPlusRim, yPlusRim);
	}
	
	private void setTextureRotations() {
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).setRot(0);
		frameTextures.get(RIGHT_TOP_CORNER).setRot(90);
		frameTextures.get(LEFT_BOTTOM_CORNER).setRot(-90);
		frameTextures.get(RIGHT_BOTTOM_CORNER).setRot(180);
		
		// Edges
		frameTextures.get(LEFT_EDGE).setRot(0);
		frameTextures.get(RIGHT_EDGE).setRot(180);
		frameTextures.get(TOP_EDGE).setRot(0);
		frameTextures.get(BOTTOM_EDGE).setRot(180);
	}
	
	private void setTextureSizes() {
		float longX = scaleX - (2 * RIM_SIZE);
		float longY = scaleY - (2 * RIM_SIZE);
		
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).setScale(RIM_SIZE, RIM_SIZE);
		frameTextures.get(RIGHT_TOP_CORNER).setScale(RIM_SIZE, RIM_SIZE);
		frameTextures.get(LEFT_BOTTOM_CORNER).setScale(RIM_SIZE, RIM_SIZE);
		frameTextures.get(RIGHT_BOTTOM_CORNER).setScale(RIM_SIZE, RIM_SIZE);
		
		// Edges
		frameTextures.get(LEFT_EDGE).setScale(RIM_SIZE, longY);
		frameTextures.get(RIGHT_EDGE).setScale(RIM_SIZE, longY);
		frameTextures.get(TOP_EDGE).setScale(longX, RIM_SIZE);
		frameTextures.get(BOTTOM_EDGE).setScale(longX, RIM_SIZE);
		
		// Background
		frameTextures.get(BACKGROUND).setScale(longX, longY);
	}

	public float getX() {
		return posX;
	}

	public void setX(float x) {
		this.posX = x;
		setTexturePositions();
	}

	public float getY() {
		return posY;
	}

	public void setY(float y) {
		this.posY = y;
		setTexturePositions();
	}

	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;
		setTexturePositions();
	}
	
	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
		setTextureSizes();
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
		setTextureSizes();
	}
	
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		setTextureSizes();
	}
	
	public List<GUITexture> getFrameTextures() {
		return frameTextures;
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > posX && Mouse.getX() < posX + scaleX &&
				Mouse.getY() > posY && Mouse.getY() < posY + scaleY;
	}

}
