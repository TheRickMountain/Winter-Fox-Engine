package com.wfe.graph;

import com.wfe.math.Vector4f;
import com.wfe.textures.Texture;

public class Material {
	
	private Texture texture;
	private Vector4f color;
	private boolean hasTexture;
	
	public Material(Texture texture) {
		this.texture = texture;
		this.color = new Vector4f(1, 1, 1, 1);
		this.hasTexture = true;
	}
	
	public Material(Vector4f color) {
		this.color = color;
		this.hasTexture = false;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public Vector4f getColor() {
		return color;
	}

	public boolean isHasTexture() {
		return hasTexture;
	}

}
