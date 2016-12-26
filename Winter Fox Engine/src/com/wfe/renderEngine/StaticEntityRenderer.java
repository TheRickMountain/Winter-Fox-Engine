package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.GeneralSettings;
import com.wfe.ecs.StaticEntity;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;

public class StaticEntityRenderer {
	
	private Camera camera;
	
	private StaticEntityShader shader;
	
	private Matrix4f modelMatrix = new Matrix4f();
	
	protected StaticEntityRenderer(Camera camera) throws Exception {
		this.camera = camera;
		
		shader = new StaticEntityShader();
		
		shader.start();
		shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		shader.stop();
	}
	
	public void render(Map<Mesh, List<StaticEntity>> entities) {
		shader.start();
		if(Display.isResized()) {
			shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		}
		
		shader.viewMatrix.loadMatrix(camera.getViewMatrix());
		shader.lightDirection.loadVec3(GeneralSettings.LIGHT_DIR);
		shader.lightColor.loadVec3(GeneralSettings.LIGHT_COLOR);
		
		for(Mesh mesh : entities.keySet()) {
			GL30.glBindVertexArray(mesh.getVAO());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			
			List<StaticEntity> batch = entities.get(mesh);
			for(StaticEntity entity : batch) {
				Material material = entity.getMaterial();
				if(entity.building){
					shader.modelMatrix.loadMatrix(MathUtils.getBuildingModelMatrix(modelMatrix, 
							entity.getTransform()));
				} else {
					shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, 
							entity.getTransform()));
				}
				
				material.getTexture().bind(0);

				shader.color.loadVec4(material.getColor());

				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndicesLength(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			GL30.glBindVertexArray(0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
		}
		
		shader.stop();
	}
	
	public void cleanup() {
		shader.cleanUp();
	}
	
}
