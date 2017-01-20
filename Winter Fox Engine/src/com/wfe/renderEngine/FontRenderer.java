package com.wfe.renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.font.FontType;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIText;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MyFile;
import com.wfe.utils.OpenglUtils;

public class FontRenderer {
	
	private static FontShader shader;
	
	private static Matrix4f modelMatrix = new Matrix4f();
	
	public static FontType font;
	
	protected FontRenderer() throws Exception {	
		shader = new FontShader();
		font = new FontType(ResourceManager.getTexture("myFont").getID(),
				new MyFile("font/myFont.fnt"));
	}
	
	public void render(List<GUIText> texts) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
		for(GUIText text : texts) {
			render(text);
		}
	}
	
	public void prepare() {
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(false);
		
		shader.start();
	}
	
	public static void render(GUIText text) {
		float newX = (2 * (1.0f / Display.getWidth()) * text.getX()) + (-1 + text.getScaleX());
		float newY = (-2 * (1.0f / Display.getHeight()) * text.getY()) + (1 - text.getScaleY());
		shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, newX, newY, 0, 0, 0, 0,
				text.getScaleX(), text.getScaleY(), 1));
		shader.color.loadVec3(text.getColor());
		
		Mesh mesh = text.getMesh();
		mesh.bind(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());
		mesh.unbind(0);
	}
	
	public static void render(GUIText text, float x, float y) {
		float newX = (2 * (1.0f / Display.getWidth()) * x) + (-1 + text.getScaleX());
		float newY = (-2 * (1.0f / Display.getHeight()) * y) + (1 - text.getScaleY());
		shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, newX, newY, 0, 0, 0, 0,
				text.getScaleX(), text.getScaleY(), 1));
		shader.color.loadVec3(text.getColor());
		
		Mesh mesh = text.getMesh();
		mesh.bind(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());
		mesh.unbind(0);
	}
	
	public void finish() {
		shader.stop();
		
		OpenglUtils.alphaBlending(false);
		OpenglUtils.depthTest(true);
	}
	
	public void cleanup() {
		shader.cleanUp();
	}

}
