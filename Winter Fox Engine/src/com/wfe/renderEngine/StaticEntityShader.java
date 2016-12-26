package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.shaders.UniformVec3;
import com.wfe.shaders.UniformVec4;
import com.wfe.utils.MyFile;

public class StaticEntityShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/static.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/static.frag");
	
	public UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	public UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	public UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	
	public UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	public UniformVec4 color = new UniformVec4("color");
	public UniformVec3 lightDirection = new UniformVec3("lightDirection");
	public UniformVec3 lightColor = new UniformVec3("lightColor");
	
	public StaticEntityShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal");
		super.storeAllUniformLocations(projectionMatrix, viewMatrix, modelMatrix,
				diffuseMap, color, lightDirection, lightColor);
		connectTextureUnits();
	}

	protected void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		super.stop();
	}
	
}
