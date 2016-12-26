package com.wfe.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.wfe.utils.MyFile;

public class Texture {
	
	private int textureID;
	private int size;
	
	public Texture(int textureID, int size) {
		this.textureID = textureID;
		this.size = size;
	}
	
	public void bind(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public void delete() {
		GL11.glDeleteTextures(textureID);
	}

	public static TextureBuilder newTexture(MyFile textureFile) {
		return new TextureBuilder(textureFile);
	}
	
	public int getID() {
		return textureID;
	}
	
	public int getSize() {
		return size;
	}

}
