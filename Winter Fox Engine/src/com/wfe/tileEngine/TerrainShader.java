package com.wfe.tileEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.shaders.UniformVec3;
import com.wfe.utils.MyFile;

public class TerrainShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/terrain.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/terrain.frag");
	
	public UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	public UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	public UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	public UniformVec3 lightDirection = new UniformVec3("lightDirection");
	public UniformVec3 lightColor = new UniformVec3("lightColor");
	
	public TerrainShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords");
		super.storeAllUniformLocations(projectionMatrix, viewMatrix, diffuseMap, lightDirection, lightColor);
		connectTextureUnits();
	}

	protected void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		super.stop();
	}
	
}
