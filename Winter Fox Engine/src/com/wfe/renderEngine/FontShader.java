package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.shaders.UniformVec3;
import com.wfe.utils.MyFile;

public class FontShader extends ShaderProgram{

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/font.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/font.frag");
	
	public UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	public UniformVec3 color = new UniformVec3("color");
	
	private UniformSampler fontAtlas = new UniformSampler("fontAtlas");
	
	public FontShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_data");
		storeAllUniformLocations(modelMatrix, fontAtlas, color);
		connectTextureUnits();
	}

	@Override
	protected void connectTextureUnits() {
		start();
		fontAtlas.loadTexUnit(0);
		stop();
	}

}
