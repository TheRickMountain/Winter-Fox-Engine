package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.wfe.font.FontType;
import com.wfe.graph.Vao;
import com.wfe.gui.GUIText;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

public class FontRenderer {
	
	private static FontShader shader;
	
	private static Matrix4f modelMatrix = new Matrix4f();
	
	protected FontRenderer() throws Exception {	
		shader = new FontShader();
	}
	
	public void render(Map<FontType, List<GUIText>> texts) {
		prepare();	
		
		for(FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for(GUIText text : texts.get(font)) {
				float newX = (2 * text.getX()) + (-1 + text.getScaleX());
				float newY = (-2 * text.getY()) + (1 - text.getScaleY());
				shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, newX, newY, 0, 0, 0, 0,
						text.getScaleX(), text.getScaleY(), 1));
				shader.color.loadVec3(text.getColor());
				
				Vao vao = text.getVao();
				vao.bind(0, 1);
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());
				vao.unbind(0, 1);
			}
		}
		
		finish();
	}
	

	
	private void prepare() {
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(false);
		
		shader.start();
	}
	
	public static void render(GUIText text) {
		float newX = (2 * text.getX()) + (-1 + text.getScaleX());
		float newY = (-2 * text.getY()) + (1 - text.getScaleY());
		shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, newX, newY, 0, 0, 0, 0,
				text.getScaleX(), text.getScaleY(), 1));
		shader.color.loadVec3(text.getColor());
		
		Vao vao = text.getVao();
		vao.bind(0, 1);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());
		vao.unbind(0, 1);
	}
	
	private void finish() {
		shader.stop();
		
		OpenglUtils.alphaBlending(false);
		OpenglUtils.depthTest(true);
	}
	
	public void cleanup() {
		shader.cleanUp();
	}

}
