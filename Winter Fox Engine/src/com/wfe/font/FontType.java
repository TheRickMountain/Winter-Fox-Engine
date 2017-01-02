package com.wfe.font;

import com.wfe.graph.Mesh;
import com.wfe.gui.GUIText;
import com.wfe.utils.MyFile;

public class FontType {
	
	private int textureAtlas;
	private TextMeshCreator loader;
	
	public FontType(int textureAtlas, MyFile fontFile) {
		this.textureAtlas = textureAtlas;
		this.loader = new TextMeshCreator(fontFile);
	}
	
	public int getTextureAtlas() {
		return textureAtlas;
	}
	
	public Mesh loadText(GUIText text) {
		return loader.createTextMesh(text);
	}

}
