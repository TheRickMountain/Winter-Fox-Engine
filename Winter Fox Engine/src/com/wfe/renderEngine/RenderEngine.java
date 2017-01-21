package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.wfe.core.Camera;
import com.wfe.ecs.StaticEntity;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.gui.inventory.GUIManager;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.TerrainBlock;
import com.wfe.utils.OpenglUtils;

public class RenderEngine {
	
	private static RenderEngine renderer;
	
	private StaticEntityRenderer staticEntityRenderer;
	private TerrainRenderer terrainRenderer;
	private GUIRenderer guiRenderer;
	private FontRenderer fontRenderer;
	
	private RenderEngine(Camera camera) throws Exception {
		this.staticEntityRenderer = new StaticEntityRenderer(camera);
		this.terrainRenderer = new TerrainRenderer(camera);
		this.guiRenderer = new GUIRenderer();
		this.fontRenderer = new FontRenderer();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.depthTest(true);
	}
	
	public void clear() {
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Map<Mesh, List<StaticEntity>> entities, Map<Terrain, List<TerrainBlock>> terrains, 
			List<GUIText> texts, List<GUITexture> textures, GUIManager guiManager) {
		staticEntityRenderer.render(entities);
		terrainRenderer.render(terrains);
		
		guiRenderer.prepare();
		guiRenderer.render(textures);
		guiManager.render();
		guiRenderer.finish();
		
		fontRenderer.prepare();
		fontRenderer.render(texts);
		guiManager.renderText();
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
		terrainRenderer.cleanup();
		guiRenderer.cleanup();
		fontRenderer.cleanup();
	}
	
}
