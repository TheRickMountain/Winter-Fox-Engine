package com.wfe.game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.wfe.components.ColliderComponent;
import com.wfe.core.Camera;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.physics.AABB;
import com.wfe.renderPrograms.MasterRenderer;
import com.wfe.tileEngine.Terrain;
import com.wfe.utils.MousePicker;
import com.wfe.utils.OpenglUtils;

public class World {
	
	private static World WORLD;
	
	private Camera camera;
	private Terrain terrain;
	private MasterRenderer masterRenderer;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();
	private List<Entity> entitiesToAdd = new ArrayList<Entity>();
	
	private List<AABB> colliders = new ArrayList<AABB>();
	
	private World(Camera camera) throws Exception {
		this.camera = camera;
		this.terrain = new Terrain(2, 2, camera);
		this.masterRenderer = new MasterRenderer(camera);
		MousePicker.setUpMousePicker(camera);
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(true);
		
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
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
		
		for(Entity entity : entities) {
			entity.update(dt);
		}
		
		if(!entitiesToAdd.isEmpty()) {
			for(Entity entity : entitiesToAdd) {
				entities.add(entity);
			}
			entitiesToAdd.clear();
		}
		
		if(!entitiesToRemove.isEmpty()) {
			for(Entity entity : entitiesToRemove) {
				entities.remove(entity);
			}
			entitiesToRemove.clear();
		}
		
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		for(Entity entity : entities) {
			masterRenderer.render(entity);
		}
		
		terrain.render();
	}
	
	public void addEntity(Entity entity) {
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.add(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
		}
		this.entitiesToAdd.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.remove(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
		}
		this.entitiesToRemove.add(entity);
	}
	
	public List<AABB> getColliders() {
		return colliders;
	}
	
	public void setTile(int x, int y, int id) {
		terrain.setTile(x, y, id);
	}
	
	public boolean setTileEntity(int x, int y, Entity entity) {
		return terrain.setTileEntity(x, y, entity);
	}
	
	public void removeTileEntity(int x, int y) {
		terrain.removeTileEntity(x, y);
	}
	
}
