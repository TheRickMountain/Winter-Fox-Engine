package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;
import com.wfe.textures.Texture;
import com.wfe.utils.Rect;

public class GUIFrame {
	
	private static final int LEFT_TOP_CORNER = 0, RIGHT_TOP_CORNER = 1, LEFT_BOTTOM_CORNER = 2, RIGHT_BOTTOM_CORNER = 3;
	private static final int LEFT_EDGE = 4, RIGHT_EDGE = 5, TOP_EDGE = 6, BOTTOM_EDGE = 7;
	private static final int BACKGROUND = 8;
	public static final int RIM_SIZE = 5;
	
	private static Texture cornerTexture;
	private static Texture hEdgeTexture;
	private static Texture vEdgeTexture;
	private static Texture backgroundTexture;
	
	public final Rect rect;
	
	private List<GUITexture> frameTextures = new ArrayList<GUITexture>();
	
	private boolean active = true;
	
	public GUIFrame(Rect rect, boolean popUp) {
		this.rect = rect;
		
		if(popUp) {
			cornerTexture = ResourceManager.getTexture("popUp_corner_frame_ui");
			hEdgeTexture = ResourceManager.getTexture("popUp_h_edge_frame_ui");
			vEdgeTexture = ResourceManager.getTexture("popUp_v_edge_frame_ui");
			backgroundTexture = ResourceManager.getTexture("popUp_background_frame_ui");
		} else {
			cornerTexture = ResourceManager.getTexture("corner_frame_ui");
			hEdgeTexture = ResourceManager.getTexture("h_edge_frame_ui");
			vEdgeTexture = ResourceManager.getTexture("v_edge_frame_ui");
			backgroundTexture = ResourceManager.getTexture("background_frame_ui");
		}
		
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
	
	public void setTexturePositions() {
		float xPlusXScaleXMinusRim = rect.x + (rect.width - RIM_SIZE);
		float yPlusYScaleYMinusRim = rect.y + (rect.height - RIM_SIZE);
		float xPlusRim = rect.x + RIM_SIZE;
		float yPlusRim = rect.y + RIM_SIZE;
		
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).rect.setPosition(rect.x, rect.y);	
		frameTextures.get(RIGHT_TOP_CORNER).rect.setPosition(xPlusXScaleXMinusRim, rect.y);	
		frameTextures.get(LEFT_BOTTOM_CORNER).rect.setPosition(rect.x, yPlusYScaleYMinusRim);
		frameTextures.get(RIGHT_BOTTOM_CORNER).rect.setPosition(xPlusXScaleXMinusRim, yPlusYScaleYMinusRim);
		
		// Edges	
		frameTextures.get(LEFT_EDGE).rect.setPosition(rect.x, yPlusRim);
		frameTextures.get(RIGHT_EDGE).rect.setPosition(xPlusXScaleXMinusRim, yPlusRim);	
		frameTextures.get(TOP_EDGE).rect.setPosition(xPlusRim, rect.y);
		frameTextures.get(BOTTOM_EDGE).rect.setPosition(xPlusRim, yPlusYScaleYMinusRim);
		
		// Background
		frameTextures.get(BACKGROUND).rect.setPosition(xPlusRim, yPlusRim);
	}
	
	private void setTextureRotations() {
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).rect.setRotation(0);
		frameTextures.get(RIGHT_TOP_CORNER).rect.setRotation(90);
		frameTextures.get(LEFT_BOTTOM_CORNER).rect.setRotation(-90);
		frameTextures.get(RIGHT_BOTTOM_CORNER).rect.setRotation(180);
		
		// Edges
		frameTextures.get(LEFT_EDGE).rect.setRotation(0);
		frameTextures.get(RIGHT_EDGE).rect.setRotation(180);
		frameTextures.get(TOP_EDGE).rect.setRotation(0);
		frameTextures.get(BOTTOM_EDGE).rect.setRotation(180);
	}
	
	private void setTextureSizes() {
		float longX = rect.width - (2 * RIM_SIZE);
		float longY = rect.height - (2 * RIM_SIZE);
		
		// Corners
		frameTextures.get(LEFT_TOP_CORNER).rect.setSize(RIM_SIZE, RIM_SIZE);
		frameTextures.get(RIGHT_TOP_CORNER).rect.setSize(RIM_SIZE, RIM_SIZE);
		frameTextures.get(LEFT_BOTTOM_CORNER).rect.setSize(RIM_SIZE, RIM_SIZE);
		frameTextures.get(RIGHT_BOTTOM_CORNER).rect.setSize(RIM_SIZE, RIM_SIZE);
		
		// Edges
		frameTextures.get(LEFT_EDGE).rect.setSize(RIM_SIZE, longY);
		frameTextures.get(RIGHT_EDGE).rect.setSize(RIM_SIZE, longY);
		frameTextures.get(TOP_EDGE).rect.setSize(longX, RIM_SIZE);
		frameTextures.get(BOTTOM_EDGE).rect.setSize(longX, RIM_SIZE);
		
		// Background
		frameTextures.get(BACKGROUND).rect.setSize(longX, longY);
	}
	
	public List<GUITexture> getFrameTextures() {
		return frameTextures;
	}
	
	public float getX() {
		return rect.x + RIM_SIZE * 2;
	}
	
	public float getY() {
		return rect.y + RIM_SIZE * 2;
	}
	
	public float getWidth() {
		return rect.width;
	}
	
	public float getHeight() {
		return rect.height;
	}
	
	public void setPosition(float x, float y) {
		this.rect.setPosition(x, y);
		setTexturePositions();
	}
	
	public void setSize(float width, float height) {
		this.rect.setSize(width, height);
		setTextureSizes();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		for(GUITexture texture : frameTextures) {
			texture.setActive(active);
		}
	}

}
