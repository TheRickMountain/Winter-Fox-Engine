package com.wfe.textures;

import com.wfe.utils.MyFile;

public class TextureBuilder {

	private boolean clampEdges = false;
	private boolean mipmap = false;
	private boolean anisotropic = true;
	private boolean nearest = false;
	private float lodBias = 0.0f;
	
	private MyFile file;
	
	protected TextureBuilder(MyFile textureFile){
		this.file = textureFile;
	}
	
	public Texture create(){
		TextureData textureData = TextureUtils.decodeTextureFile(file);
		int textureId = TextureUtils.loadTextureToOpenGL(textureData, this);
		return new Texture(textureId, textureData.getWidth());
	}
	
	public TextureBuilder clampEdges(){
		this.clampEdges = true;
		return this;
	}
	
	public TextureBuilder normalMipMap(float lodBias){
		this.lodBias = lodBias;
		this.mipmap = true;
		this.anisotropic = false;
		return this;
	}
	
	public TextureBuilder normalMipMap(){
		this.mipmap = true;
		this.anisotropic = false;
		return this;
	}
	
	public TextureBuilder nearestFiltering(){
		this.anisotropic = false;
		this.nearest = true;
		return this;
	}
	
	public TextureBuilder anisotropic(){
		this.mipmap = true;
		this.anisotropic = true;
		return this;
	}
	
	protected boolean isClampEdges() {
		return clampEdges;
	}

	protected boolean isMipmap() {
		return mipmap;
	}

	protected boolean isAnisotropic() {
		return anisotropic;
	}

	protected boolean isNearest() {
		return nearest;
	}
	
	protected float getLodBias() {
		return lodBias;
	}
	
}
