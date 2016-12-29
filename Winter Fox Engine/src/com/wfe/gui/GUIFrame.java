package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;
import com.wfe.textures.Texture;

public class GUIFrame {
	
	private static final int LEFT_TOP_CORNER = 0, RIGHT_TOP_CORNER = 1, LEFT_BOTTOM_CORNER = 2, RIGHT_BOTTOM_CORNER = 3;
	private static final int LEFT_EDGE = 4, RIGHT_EDGE = 5, TOP_EDGE = 6, BOTTOM_EDGE = 7;
	private static final int BACKGROUND = 8;
	private static final int RIM_SIZE = 10;
	private static Texture cornerTexture = ResourceManager.getTexture("corner_frame_ui");
	private static Texture hEdgeTexture = ResourceManager.getTexture("h_edge_frame_ui");
	private static Texture vEdgeTexture = ResourceManager.getTexture("v_edge_frame_ui");
	private static Texture backgroundTexture = ResourceManager.getTexture("background_frame_ui");
	private float x;
	private float y;
	private float scaleX;
	private float scaleY;
	
	private List<GUITexture> frameTextures = new ArrayList<GUITexture>();
	
	public GUIFrame(float x, float y, float scaleX, float scaleY) {
		this.x = x;
		this.y = y;
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
		float xPlusXScaleXMinusRim = x + (scaleX - RIM_SIZE);
		float yPlusYScaleYMinusRim = y + (scaleY - RIM_SIZE);
		float xPlusRim = x + RIM_SIZE;
		float yPlusRim = y + RIM_SIZE;
		
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).setPosition(x, y);	
		frameTextures.get(RIGHT_TOP_CORNER).setPosition(xPlusXScaleXMinusRim, y);	
		frameTextures.get(LEFT_BOTTOM_CORNER).setPosition(x, yPlusYScaleYMinusRim);
		frameTextures.get(RIGHT_BOTTOM_CORNER).setPosition(xPlusXScaleXMinusRim, yPlusYScaleYMinusRim);
		
		// Edges	
		frameTextures.get(LEFT_EDGE).setPosition(x, yPlusRim);
		frameTextures.get(RIGHT_EDGE).setPosition(xPlusXScaleXMinusRim, yPlusRim);	
		frameTextures.get(TOP_EDGE).setPosition(xPlusRim, y);
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
	
	public List<GUITexture> getFrameTextures() {
		return frameTextures;
	}

}
