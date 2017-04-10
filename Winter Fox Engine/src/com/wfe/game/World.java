package com.wfe.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.components.ColliderComponent;
import com.wfe.components.MobComponent;
import com.wfe.core.Camera;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIManager;
import com.wfe.physics.AABB;
import com.wfe.renderEngine.RenderEngine;
import com.wfe.tileEngine.Terrain;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MousePicker;
import com.wfe.weather.Weather;

public class World {
	
	private static World WORLD;
	
	private Camera camera;
	private Terrain terrain;
	private RenderEngine renderEngine;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();
	private List<Entity> entitiesToAdd = new ArrayList<Entity>();
	
	private List<Entity> mobs = new ArrayList<Entity>();
	
	private Map<Mesh, List<Entity>> entitiesToRender = new HashMap<Mesh, List<Entity>>();
	
	private List<AABB> colliders = new ArrayList<AABB>();
	
	private float time = 12000;
	private Weather weather;
	
	private World(Camera camera) {
		this.camera = camera;
	}
	
	public void init() throws Exception {
		this.terrain = new Terrain(10, 10, camera);
		this.renderEngine = RenderEngine.create(camera);
		this.weather = new Weather();
		MousePicker.setUpMousePicker(camera);
		GUIManager.init();
	}
	
	public static World createWorld(Camera camera) throws Exception {
		if(WORLD == null)
			WORLD = new World(camera);
		
		return WORLD;
	}
	
	public static World getWorld() {
		return WORLD;
	}
	
	public void update(float dt) {
		camera.update(dt);
		MousePicker.update();
		updateWeather(dt);
		terrain.update(camera.getPosition().x, camera.getPosition().z);
		
		for(Entity entity : entities) {
			entity.update(dt);
		}
		
		if(!entitiesToAdd.isEmpty()) {
			entities.addAll(entitiesToAdd);
			entitiesToAdd.clear();
		}
		
		if(!entitiesToRemove.isEmpty()) {
			entities.removeAll(entitiesToRemove);
			entitiesToRemove.clear();
		}
		
		GUIManager.update();
	}
	
	public void render() {
		renderEngine.clear();
		terrain.render();
		renderEngine.render(entitiesToRender);	
	}
	
	public void addEntity(Entity entity) {
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.add(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
		}
		if(entity.hasComponent(ComponentType.MOB)) {
			colliders.add(((MobComponent)entity.getComponent(ComponentType.MOB)).getAABB());
			mobs.add(entity);
		}
		this.entitiesToAdd.add(entity);
		
		if(entity.getMesh() != null) {
			List<Entity> batch = entitiesToRender.get(entity.getMesh());
			if(batch == null) {
				batch = new ArrayList<Entity>();
	            entitiesToRender.put(entity.getMesh(), batch);
			} 
			batch.add(entity);
		}
	}
	
	public void removeEntity(Entity entity) {
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.remove(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
		}
		if(entity.hasComponent(ComponentType.MOB)) {
			colliders.remove(((MobComponent)entity.getComponent(ComponentType.MOB)).getAABB());
			mobs.remove(entity);
		}
		this.entitiesToRemove.add(entity);
		
		List<Entity> batch = entitiesToRender.get(entity.getMesh());
		batch.remove(entity);
		
		if(batch.isEmpty()) {
			entitiesToRender.remove(entity.getMesh());
		}
	}
	
	public List<AABB> getColliders() {
		return colliders;
	}
	
	public void setTile(int x, int y, int id) {
		terrain.setTile(x, y, id);
	}
	
	public Tile getTile(int x, int y) {
		return terrain.getTile(x, y);
	}
	
	public boolean addEntityToTile(Entity entity) {
		if(!terrain.getTile((int)entity.getTransform().getX(), (int)entity.getTransform().getZ()).isHasEntity()) {
			terrain.setTileEntity(
					(int)entity.getTransform().getX(),
					(int)entity.getTransform().getZ(), entity);
			addEntity(entity);
			return true;
		}
	
		return false;
	}
	
	public void removeEntityFromTile(int x, int y) {
		terrain.removeTileEntity(x, y);
	}
	
	public void updateWeather(float dt) {
		time += 15 * dt;
		if(time >= 24000) {
			time = 0;
		}
		
		weather.updateWeather(time, dt);
	}
	
	public void cleanup() {
		terrain.cleanup();
		renderEngine.cleanup();
	}
	
	public List<Entity> getMobs() {
		return mobs;
	}
	
}
