package com.wfe.newFont;

import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;

public class FontInfo {
	
	private MetaFile metaFile;
	
	private Texture texture;
	
	public FontInfo(String fontName) {
		metaFile = new MetaFile(new MyFile("font/" + fontName + ".fnt"));
		
		texture = Texture.newTexture(new MyFile("font/" + fontName + ".png"))
				.normalMipMap().create();
	}

	public MetaFile getMetaFile() {
		return metaFile;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
}
