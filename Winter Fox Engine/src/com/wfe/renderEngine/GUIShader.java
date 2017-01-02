package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformBoolean;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.shaders.UniformVec3;
import com.wfe.utils.MyFile;

public class GUIShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/gui.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/gui.frag");
	
	protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	protected UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	
	private UniformSampler image = new UniformSampler("image");
	protected UniformVec3 color = new UniformVec3("color");
	protected UniformBoolean solidColor = new UniformBoolean("solidColor");
	
	public GUIShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_data");
		storeAllUniformLocations(projectionMatrix, modelMatrix, image, color, solidColor);
		connectTextureUnits();
	}

	@Override
	protected void connectTextureUnits() {
		start();
		image.loadTexUnit(0);
		stop();
	}

}
