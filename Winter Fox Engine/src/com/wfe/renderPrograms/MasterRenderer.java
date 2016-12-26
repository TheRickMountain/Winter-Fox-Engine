package com.wfe.renderPrograms;

import com.wfe.core.Camera;
import com.wfe.ecs.Entity;

public class MasterRenderer {
	
	private StaticRenderer staticRenderer;
	
	public MasterRenderer(Camera camera) throws Exception {
		staticRenderer = new StaticRenderer(camera);
	}
	
	public void render(Entity entity) {
		staticRenderer.render(entity);
	}

}
