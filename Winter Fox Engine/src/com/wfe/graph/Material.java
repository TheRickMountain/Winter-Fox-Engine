package com.wfe.graph;

import com.wfe.math.Vector4f;
import com.wfe.textures.Texture;

public class Material {
	
	private Texture texture;
	private Vector4f color;
	private int numberOfRows = 1;
	private boolean hasTransparency = false;
	
	public Material(Texture texture) {
		this(texture, new Vector4f(1, 1, 1, 1));
	}
	
	public Material(Texture texture, Vector4f color) {
		this.texture = texture;
		this.color = color;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public Vector4f getColor() {
		return color;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		if(numberOfRows <= 0) {
            numberOfRows = 1;
		} else {
			this.numberOfRows = numberOfRows;
		}
	}
	
	public void delete() {
		texture.delete();
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

}
