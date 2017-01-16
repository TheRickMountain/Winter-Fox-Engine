package com.wfe.renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.wfe.core.Display;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUITexture;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

public class GUIRenderer {
	
	private static GUIShader shader;
	
	private Mesh quadMesh;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private static Matrix4f modelMatrix = new Matrix4f();
	
	public GUIRenderer() {
		shader = new GUIShader();
		initRenderData();
		MathUtils.getOrthoProjectionMatrix(projectionMatrix, 0, Display.getWidth(), Display.getHeight(), 0);
		shader.start();
		shader.projectionMatrix.loadMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<GUITexture> textures) {
		for(GUITexture texture : textures) {
			if(!texture.isCentered()) {
				shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
						texture.getX(), texture.getY(), texture.getRot(), texture.getScaleX(), texture.getScaleY()));
			} else {
				shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
						texture.getX() - texture.getScaleX() / 2, texture.getY() - texture.getScaleY() / 2, 
						texture.getRot(), texture.getScaleX(), texture.getScaleY()));
			}
			
			if(texture.isSolidColor()) {
				shader.color.loadVec3(texture.getColor());
				shader.solidColor.loadBoolean(true);
			} else {
				shader.solidColor.loadBoolean(false);
				shader.active.loadBoolean(false);
				texture.getTexture().bind(0);
			}
			
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		}
	}
	
	public static void render(GUITexture texture) {
		if(!texture.isCentered()) {
			shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
					texture.getX(), texture.getY(), texture.getRot(), texture.getScaleX(), texture.getScaleY()));
		} else {
			shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
					texture.getX() - texture.getScaleX() / 2, texture.getY() - texture.getScaleY() / 2, 
					texture.getRot(), texture.getScaleX(), texture.getScaleY()));
		}
		
		if(texture.isSolidColor()) {
			shader.color.loadVec3(texture.getColor());
			shader.solidColor.loadBoolean(true);
		} else {
			shader.solidColor.loadBoolean(false);
			texture.getTexture().bind(0);
		}
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public static void render(Texture texture, float x, float y, float rot, float scaleX, float scaleY, boolean centered, boolean active) {
		if(!centered) {
			shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
					x, y, rot, scaleX, scaleY));
		} else {
			shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
					x - scaleX / 2, y - scaleY / 2, 
					rot, scaleX, scaleY));
		}
		
		shader.solidColor.loadBoolean(false);
		shader.active.loadBoolean(!active);
		texture.bind(0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public static void render(Vector3f color, float x, float y, float rot, float scaleX, float scaleY, boolean centered) {
		if(!centered) {
			shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
					x, y, rot, scaleX, scaleY));
		} else {
			shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
					x - scaleX / 2, y - scaleY / 2, 
					rot, scaleX, scaleY));
		}
		
		shader.color.loadVec3(color);
		shader.solidColor.loadBoolean(true);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public void prepare() {
		OpenglUtils.cullBackFaces(false);
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(false);
		
		shader.start();
		
		if(Display.isResized()) {
			MathUtils.getOrthoProjectionMatrix(projectionMatrix, 0, Display.getWidth(), Display.getHeight(), 0);
			shader.projectionMatrix.loadMatrix(projectionMatrix);
		}
		
		quadMesh.bind(0);
	}
	
	public void finish() {
		quadMesh.unbind(0);
		
		shader.stop();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.alphaBlending(false);
		OpenglUtils.depthTest(true);
	}
	
	private void initRenderData() {
        float[] data = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };

        quadMesh = new Mesh(data, 2);
    }
	
	public void cleanup() {
		quadMesh.delete();
	}

}
