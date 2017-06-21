package com.wfe.font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Display;
import com.wfe.graph.Mesh;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

public class TextRenderer {

	private static TextShader shader;
	
	private Matrix4f projection = new Matrix4f();
	private static Matrix4f model = new Matrix4f();
	
	private FontInfo fontInfo;
	
	public TextRenderer() {
		shader = new TextShader();
		
		fontInfo = new FontInfo("primitiveFont");
		
		updateProjection();
	}
	
	public void update() {
		if(Display.isResized()) {
			updateProjection();
		}
	}
	
	public void prepare() {
		OpenglUtils.cullBackFaces(false);
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(false);
		
		fontInfo.getTexture().bind(0);
		
		shader.start();
	}
	
	public static void render(GUIText text) {		
		shader.model.loadMatrix(MathUtils.getModelMatrix(model, text.getX(), text.getY(), 0, 
				text.getScaleX(), text.getScaleY()));
		
		shader.color.loadColor(text.getColor());
		
		Mesh mesh = text.getMesh();
		
		GL30.glBindVertexArray(mesh.getVAO());
		GL20.glEnableVertexAttribArray(0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void finish() {
		shader.stop();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.alphaBlending(false);
		OpenglUtils.depthTest(true);
	}
	
	public void cleanup() {
		shader.cleanUp();
	}
	
	private void updateProjection() {
		MathUtils.getOrthoProjectionMatrix(projection, 0, Display.getWidth(), Display.getHeight(), 0);
		shader.start();
		shader.projection.loadMatrix(projection);
		shader.stop();
	}
	
}
