package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformMat4Array;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.shaders.UniformVec3;
import com.wfe.utils.MyFile;

public class AnimatedEntityShader extends ShaderProgram {

	private static final int MAX_JOINTS = 50;

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/animated.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/animated.frag");

	protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
	protected UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	protected UniformMat4Array jointTransforms = new UniformMat4Array("jointTransforms",
			MAX_JOINTS);
	
	private UniformSampler diffuseMap = new UniformSampler("diffuseMap");
	protected UniformVec3 lightDirection = new UniformVec3("lightDirection");
	protected UniformVec3 lightColor = new UniformVec3("lightColor");

	public AnimatedEntityShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal",
				"in_jointIndices", "in_weights");
		super.storeAllUniformLocations(projectionViewMatrix, modelMatrix, diffuseMap, lightDirection,
				jointTransforms, lightColor);
		connectTextureUnits();
	}

	protected void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(0);
		super.stop();
	}

}
