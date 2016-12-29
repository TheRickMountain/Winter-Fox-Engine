package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.wfe.animation.AnimatedEntity;
import com.wfe.core.Camera;
import com.wfe.ecs.StaticEntity;
import com.wfe.font.FontType;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.utils.OpenglUtils;

public class RenderEngine {
	
	private static RenderEngine renderer;
	
	private StaticEntityRenderer staticEntityRenderer;
	private AnimatedEntityRenderer animatedEntityRenderer;
	private GUIRenderer guiRenderer;
	private FontRenderer fontRenderer;
	
	private RenderEngine(Camera camera) throws Exception {
		this.staticEntityRenderer = new StaticEntityRenderer(camera);
		this.animatedEntityRenderer = new AnimatedEntityRenderer(camera);
		this.guiRenderer = new GUIRenderer();
		this.fontRenderer = new FontRenderer();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.depthTest(true);
	}
	
	public void clear() {
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Map<Mesh, List<StaticEntity>> entities, AnimatedEntity entity, 
			Map<FontType, List<GUIText>> texts, List<GUITexture> textures) {
		staticEntityRenderer.render(entities);
		animatedEntityRenderer.render(entity);
		guiRenderer.render(textures);
		fontRenderer.render(texts);
	}

	public static RenderEngine init(Camera camera) throws Exception {
		if(renderer == null) {
			renderer = new RenderEngine(camera);
		}
		return renderer;
	}
	
	public void cleanup() {
		staticEntityRenderer.cleanup();
		animatedEntityRenderer.cleanup();
		guiRenderer.cleanup();
		fontRenderer.cleanup();
	}
	
}
