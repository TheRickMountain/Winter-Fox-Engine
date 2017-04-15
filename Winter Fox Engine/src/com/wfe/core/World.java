package com.wfe.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.components.ColliderComponent;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIManager;
import com.wfe.pathfinding.PathTileGraph;
import com.wfe.physics.AABB;
import com.wfe.renderEngine.RenderEngine;
import com.wfe.tileEngine.Chunk;
import com.wfe.tileEngine.Terrain;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MousePicker;
import com.wfe.weather.Weather;

public class World {
	
	private static World WORLD;
	
	private int width, height;
	
	private Camera camera;
	private Terrain terrain;
	private RenderEngine renderEngine;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();
	private List<Entity> entitiesToAdd = new ArrayList<Entity>();
	
	private Map<Mesh, List<Entity>> entitiesToRender = new HashMap<Mesh, List<Entity>>();
	
	private List<AABB> colliders = new ArrayList<AABB>();
	
	private PathTileGraph tileGraph;
	
	private float time = 12000;
	private Weather weather;
	
	private World(Camera camera) {
		this.camera = camera;
	}
	
	public void init(int w, int h) throws Exception {
		width = w * Chunk.SIZE;
		height = h * Chunk.SIZE;
		
		terrain = new Terrain(w, h, camera);
		renderEngine = RenderEngine.create(camera);
		weather = new Weather();
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
		entity.init();
		
		if(entity.hasComponent(ComponentType.COLLIDER)) {
			colliders.add(((ColliderComponent)entity.getComponent(ComponentType.COLLIDER)).getAABB());
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
	
	public PathTileGraph getTileGraph() {
		return tileGraph;
	}
	
	public void setTileGraph(PathTileGraph tileGraph) {
		this.tileGraph = tileGraph;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void cleanup() {
		terrain.cleanup();
		renderEngine.cleanup();
	}

	
}
