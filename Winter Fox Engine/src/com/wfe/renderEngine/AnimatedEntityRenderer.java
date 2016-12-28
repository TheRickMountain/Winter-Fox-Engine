package com.wfe.renderEngine;

import org.lwjgl.opengl.GL11;

import com.wfe.animation.AnimatedEntity;
import com.wfe.core.Camera;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;
import com.wfe.weather.DirectionalLight;

public class AnimatedEntityRenderer {

	private Camera camera;
	
	private AnimatedEntityShader shader;
	
	private Matrix4f modelMatrix = new Matrix4f();
	
	public AnimatedEntityRenderer(Camera camera) {
		this.camera = camera;
		
		this.shader = new AnimatedEntityShader();
	}
	
	public void render(AnimatedEntity entity) {
		prepare();
		entity.getTexture().bind(0);
		entity.getModel().bind(0, 1, 2, 3, 4);
		shader.jointTransforms.loadMatrixArray(entity.getJointTransforms());
		shader.modelMatrix.loadMatrix(MathUtils.getModelMatrix(modelMatrix, entity.getTransform()));
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getIndexCount(),
				GL11.GL_UNSIGNED_INT, 0);
		entity.getModel().unbind(0, 1, 2, 3, 4);
		finish();
	}
	
	private void prepare() {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(DirectionalLight.LIGHT_DIR);
		shader.lightColor.loadVec3(DirectionalLight.LIGHT_COLOR);
	}
	
	private void finish() {
		shader.stop();
	}
	
	public void cleanup() {
		shader.cleanUp();
	}
	
}
