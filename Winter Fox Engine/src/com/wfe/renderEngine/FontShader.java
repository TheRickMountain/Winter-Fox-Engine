package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformBoolean;
import com.wfe.shaders.UniformColor;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.utils.MyFile;

public class FontShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/hud_vertex.vs");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/hud_fragment.fs");
	
	public UniformMatrix projModelMatrix = new UniformMatrix("projModelMatrix");
	public UniformColor colour = new UniformColor("colour");
	public UniformBoolean hasTexture = new UniformBoolean("hasTexture");
	
	private UniformSampler fontAtlas = new UniformSampler("fontAtlas");
	
	public FontShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "position", "texCoord");
		storeAllUniformLocations(projModelMatrix, colour, hasTexture, fontAtlas);
		connectTextureUnits();
	}

	@Override
	protected void connectTextureUnits() {
		start();
		fontAtlas.loadTexUnit(0);
		stop();
	}

}
