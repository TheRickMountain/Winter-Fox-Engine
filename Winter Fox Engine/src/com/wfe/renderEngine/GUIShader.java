package com.wfe.renderEngine;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformColor;
import com.wfe.shaders.UniformInt;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.utils.MyFile;

public class GUIShader extends ShaderProgram {

	private static final MyFile VERTEX_SHADER = new MyFile("shaders/gui.vert");
	private static final MyFile FRAGMENT_SHADER = new MyFile("shaders/gui.frag");
	
	protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	protected UniformMatrix modelMatrix = new UniformMatrix("modelMatrix");
	
	private UniformSampler image = new UniformSampler("image");
	protected UniformColor color = new UniformColor("color");
	protected UniformInt mode = new UniformInt("mode");
	
	public GUIShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_data");
		storeAllUniformLocations(projectionMatrix, modelMatrix, image, color, mode);
		connectTextureUnits();
	}

	@Override
	protected void connectTextureUnits() {
		start();
		image.loadTexUnit(0);
		stop();
	}

}
