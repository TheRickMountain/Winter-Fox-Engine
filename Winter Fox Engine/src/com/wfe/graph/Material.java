package com.wfe.graph;

import com.wfe.math.Vector4f;
import com.wfe.textures.Texture;

public class Material {
	
	private Texture texture;
	private Vector4f color;
	
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
	
	public void delete() {
		texture.delete();
	}

}
