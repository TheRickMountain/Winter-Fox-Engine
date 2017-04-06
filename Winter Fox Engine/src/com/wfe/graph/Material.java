package com.wfe.graph;

import com.wfe.math.Vector4f;
import com.wfe.textures.Texture;

public class Material {
	
	private Texture texture;
	private Vector4f color;
	private int numberOfRows = 1;
	private boolean hasTransparency = false;
	private boolean hasFakeLighting = false;
	private boolean hasTexture = false;
	
	public Material(Texture texture) {
		this(texture, new Vector4f(1, 1, 1, 1));
	}
	
	public Material(Texture texture, Vector4f color) {
		this.texture = texture;
		this.color = color;
		
		hasTexture = this.texture != null;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public Vector4f getColor() {
		return color;
	}
	
	public Vector4f setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
		return color;
	}
	
	public Vector4f setColor(int r, int g, int b, int a) {
		color.set(r / 255f, g / 255f, b / 255f, a / 255f);
		return color;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public Material setNumberOfRows(int numberOfRows) {
		if(numberOfRows <= 0) {
            numberOfRows = 1;
		} else {
			this.numberOfRows = numberOfRows;
		}
		return this;
	}
	
	public void delete() {
		texture.delete();
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public Material setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
		return this;
	}

	public boolean isHasFakeLighting() {
		return hasFakeLighting;
	}

	public Material setHasFakeLighting(boolean hasFakeLighting) {
		this.hasFakeLighting = hasFakeLighting;
		return this;
	}

	public boolean isHasTexture() {
		return hasTexture;
	}

}
