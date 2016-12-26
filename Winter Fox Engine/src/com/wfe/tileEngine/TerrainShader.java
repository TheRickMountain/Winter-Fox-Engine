package com.wfe.tileEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.utils.MyFile;

public class TerrainShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/terrain.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/terrain.frag");
	
	public UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	public UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	public UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	
	public TerrainShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords");
		super.storeAllUniformLocations(projectionMatrix, viewMatrix, diffuseMap);
		connectTextureUnits();
	}

	protected void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		super.stop();
	}
	
}
