package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.ecs.Entity;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;
import com.wfe.graph.ShaderProgram;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;

public class StaticRenderer {
	
	private Camera camera;
	
	private ShaderProgram shader;
	
	private Matrix4f modelMatrix = new Matrix4f();
	
	public StaticRenderer(Camera camera) throws Exception {
		this.camera = camera;
		
		shader = new ShaderProgram();
		shader.createVertexShader("/shaders/static.vert");
		shader.createFragmentShader("/shaders/static.frag");
		shader.link();
		
		// Vertex Shader
		shader.createUniform("projectionMatrix");
		shader.createUniform("viewMatrix");
		shader.createUniform("modelMatrix");
		
		// Fragment Shader
		shader.createUniform("image");
		shader.createUniform("hasTexture");
		shader.createUniform("color");
		
		shader.bind();
		shader.setUniform("projectionMatrix", camera.getProjectionMatrix());
		shader.setUniform("image", 0);
		shader.unbind();
	}
	
	public void render(Map<Mesh, List<Entity>> entities) {
		shader.bind();
		if(Display.isResized()) {
			shader.setUniform("projectionMatrix", camera.getProjectionMatrix());
		}
		
		shader.setUniform("viewMatrix", camera.getViewMatrix());
		
		for(Mesh mesh : entities.keySet()) {
			GL30.glBindVertexArray(mesh.getVAO());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			
			List<Entity> batch = entities.get(mesh);
			for(Entity entity : batch) {
				Material material = entity.getMaterial();
				shader.setUniform("viewMatrix", camera.getViewMatrix());
				if(entity.building){
					shader.setUniform("modelMatrix", MathUtils.getBuildingModelMatrix(modelMatrix, 
							entity.getTransform()));
				} else {
					shader.setUniform("modelMatrix", MathUtils.getModelMatrix(modelMatrix, 
							entity.getTransform()));
				}
				
				if(material.isHasTexture()) {
					material.getTexture().bind(0);
				}
				
				shader.setUniform("hasTexture", material.isHasTexture());
				shader.setUniform("color", material.getColor());

				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndicesLength(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			GL30.glBindVertexArray(0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
		}
		
		shader.unbind();
	}
	
	public void cleanup() {
		shader.cleanup();
	}
	
}
