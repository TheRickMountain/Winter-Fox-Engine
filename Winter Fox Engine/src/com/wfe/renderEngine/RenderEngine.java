package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.wfe.core.Camera;
import com.wfe.ecs.Entity;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIManager;
import com.wfe.utils.OpenglUtils;

public class RenderEngine {
	
	private static RenderEngine renderer;
	
	private StaticRenderer staticEntityRenderer;
	private GUIRenderer guiRenderer;
	private FontRenderer fontRenderer;
	
	private RenderEngine(Camera camera) throws Exception {
		this.staticEntityRenderer = new StaticRenderer(camera);
		this.guiRenderer = new GUIRenderer();
		this.fontRenderer = new FontRenderer();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.depthTest(true);
	}
	
	public void clear() {
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Map<Mesh, List<Entity>> entities) {
		staticEntityRenderer.render(entities);
		
		guiRenderer.prepare();
		GUIManager.render();
		guiRenderer.finish();
		
		fontRenderer.prebare();
		GUIManager.renderText();
		fontRenderer.finish();
		
		guiRenderer.prepare();
		GUIManager.renderPopUp();
		guiRenderer.finish();
		
		fontRenderer.prebare();
		GUIManager.renderPopUpText();
		fontRenderer.finish();
	}

	public static RenderEngine create(Camera camera) throws Exception {
		if(renderer == null) {
			renderer = new RenderEngine(camera);
		}
		return renderer;
	}
	
	public void cleanup() {
		staticEntityRenderer.cleanup();
		guiRenderer.cleanup();
		//fontRenderer.cleanup();
	}
	
}
