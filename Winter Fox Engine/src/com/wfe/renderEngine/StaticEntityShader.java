package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformBoolean;
import com.wfe.shaders.UniformInt;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.shaders.UniformVec2;
import com.wfe.shaders.UniformVec3;
import com.wfe.shaders.UniformVec4;
import com.wfe.utils.MyFile;

public class StaticEntityShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/static.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/static.frag");
	
	protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	protected UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	protected UniformInt numberOfRows = new UniformInt("numberOfRows");
	protected UniformVec2 offset = new UniformVec2("offset");
	
	private UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	protected UniformVec4 color = new UniformVec4("color");
	protected UniformVec3 lightDirection = new UniformVec3("lightDirection");
	protected UniformVec3 lightColor = new UniformVec3("lightColor");
	protected UniformBoolean hasFakeLighting = new UniformBoolean("hasFakeLighting");
	protected UniformVec3 ambientLight = new UniformVec3("ambientLight");
	
	public StaticEntityShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal");
		super.storeAllUniformLocations(projectionMatrix, viewMatrix, modelMatrix, numberOfRows,
				offset, diffuseMap, color, lightDirection, lightColor, hasFakeLighting, ambientLight);
		connectTextureUnits();
	}

	protected void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		super.stop();
	}
	
}
