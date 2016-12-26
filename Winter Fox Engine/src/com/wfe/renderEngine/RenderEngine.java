package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.wfe.core.Camera;
import com.wfe.ecs.Entity;
import com.wfe.graph.Mesh;
import com.wfe.utils.OpenglUtils;

public class RenderEngine {
	
	private static RenderEngine renderer;
	
	private StaticEntityRenderer staticRenderer;
	
	private RenderEngine(Camera camera) throws Exception {
		this.staticRenderer = new StaticEntityRenderer(camera);
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(true);
	}
	
	private void clear() {
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Map<Mesh, List<Entity>> entities) {
		clear();
		
		staticRenderer.render(entities);
	}

	public static RenderEngine init(Camera camera) throws Exception {
		if(renderer == null) {
			renderer = new RenderEngine(camera);
		}
		return renderer;
	}
	
	public void cleanup() {
		staticRenderer.cleanup();
	}
	
}
