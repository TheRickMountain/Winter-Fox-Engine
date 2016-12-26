package com.wfe.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.animation.AnimatedEntity;
import com.wfe.components.ColliderComponent;
import com.wfe.core.Camera;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.StaticEntity;
import com.wfe.graph.Mesh;
import com.wfe.physics.AABB;
import com.wfe.renderEngine.RenderEngine;
import com.wfe.tileEngine.Terrain;
import com.wfe.utils.MousePicker;

public class World {
	
	private static World WORLD;
	
	private Camera camera;
	private Terrain terrain;
	private RenderEngine renderEngine;
	
	private List<StaticEntity> entities = new ArrayList<StaticEntity>();
	private List<StaticEntity> entitiesToRemove = new ArrayList<StaticEntity>();
	private List<StaticEntity> entitiesToAdd = new ArrayList<StaticEntity>();
	
	private Map<Mesh, List<StaticEntity>> entitiesToRender = new HashMap<Mesh, List<StaticEntity>>();
	
	private List<AABB> colliders = new ArrayList<AABB>();
	
	private World(Camera camera) throws Exception {
		this.camera = camera;
		this.terrain = new Terrain(2, 2, camera);
		this.renderEngine = RenderEngine.init(camera);
		MousePicker.setUpMousePicker(camera);
	}
	
	public static void createWorld(Camera camera) throws Exception {
		if(WORLD == null)
			WORLD = new World(camera);
	}
	
	public static World getWorld() {
		return WORLD;
	}
	
	public void clearWorld() {
		terrain.cleanup();
	}
	
	public void update(float dt) {
		camera.update(dt);
		MousePicker.update();
		
		for(StaticEntity entity : entities) {
			entity.update(dt);
		}
		
		if(!entitiesToAdd.isEmpty()) {
			for(StaticEntity entity : entitiesToAdd) {
				entities.add(entity);
			}
			entitiesToAdd.clear();
		}
		
		if(!entitiesToRemove.isEmpty()) {
			for(StaticEntity entity : entitiesToRemove) {
				entities.remove(entity);
			}
			entitiesToRemove.clear();
		}
		
	}
	
	public void render(AnimatedEntity entity) {
		renderEngine.render(entitiesToRender, entity);
		terrain.render();
	}
	
	public void addEntity(StaticEntity entity) {
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.add(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
		}
		this.entitiesToAdd.add(entity);
		
		List<StaticEntity> batch = entitiesToRender.get(entity.getMesh());
		if(batch != null) {
			batch.add(entity);
		} else {
			List<StaticEntity> newBatch = new ArrayList<StaticEntity>();
            newBatch.add(entity);
            entitiesToRender.put(entity.getMesh(), newBatch);
		}
	}
	
	public void removeEntity(StaticEntity entity) {
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.remove(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
		}
		this.entitiesToRemove.add(entity);
		
		List<StaticEntity> batch = entitiesToRender.get(entity.getMesh());
		batch.remove(entity);
		
		if(batch.isEmpty()) {
			batch.clear();
			batch = null;
		}
	}
	
	public List<AABB> getColliders() {
		return colliders;
	}
	
	public void setTile(int x, int y, int id) {
		terrain.setTile(x, y, id);
	}
	
	public boolean setTileEntity(int x, int y, StaticEntity entity) {
		return terrain.setTileEntity(x, y, entity);
	}
	
	public void removeTileEntity(int x, int y) {
		terrain.removeTileEntity(x, y);
	}
	
	public void cleanup() {
		terrain.cleanup();
		renderEngine.cleanup();
	}
	
}
