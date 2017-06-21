package com.wfe.font;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformColor;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.utils.MyFile;

public class TextShader extends ShaderProgram {

	private static final MyFile VERTEX = new MyFile("shaders/text.vs");
	private static final MyFile FRAGMENT = new MyFile("shaders/text.fs");
	
	public UniformMatrix projection = new UniformMatrix("projection");
	public UniformMatrix model = new UniformMatrix("model");
	private UniformSampler image = new UniformSampler("image");
	public UniformColor color = new UniformColor("color");
	
	public TextShader() {
		super(VERTEX, FRAGMENT, "data");
		storeAllUniformLocations(projection, model, image, color);
		connectTextureUnits();
	}

	@Override
	protected void connectTextureUnits() {
		start();
		image.loadTexUnit(0);
		stop();
	}

}
