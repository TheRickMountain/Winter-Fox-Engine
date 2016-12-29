package com.wfe.renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Display;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUITexture;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

public class GUIRenderer {
	
	private GUIShader shader;
	
	private Mesh quadMesh;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f modelMatrix = new Matrix4f();
	
	public GUIRenderer() {
		shader = new GUIShader();
		initRenderData();
		MathUtils.getOrthoProjectionMatrix(projectionMatrix, 0, Display.getWidth(), Display.getHeight(), 0);
		shader.start();
		shader.projectionMatrix.loadMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<GUITexture> textures) {
		prepare();
		
		for(GUITexture texture : textures) {
			if(!texture.isCentered()) {
				shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
						texture.getX(), texture.getY(), texture.getRot(), texture.getScaleX(), texture.getScaleY()));
			} else {
				shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
						texture.getX() - texture.getScaleX() / 2, texture.getY() - texture.getScaleY() / 2, 
						texture.getRot(), texture.getScaleX(), texture.getScaleY()));
			}
			
			texture.getTexture().bind(0);
			
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		}
		
		finish();
	}
	
	private void prepare() {
		OpenglUtils.cullBackFaces(false);
		OpenglUtils.alphaBlending(true);
		
		shader.start();
		
		if(Display.isResized()) {
			shader.projectionMatrix.loadMatrix(projectionMatrix);
		}
		
		GL30.glBindVertexArray(quadMesh.getVAO());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void finish() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.stop();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.alphaBlending(false);
	}
	
	private void initRenderData() {
        float[] data = new float[] {
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 1.0f
        };

        quadMesh = new Mesh(data, 4);
    }
	
	public void cleanup() {
		quadMesh.delete();
	}

}
