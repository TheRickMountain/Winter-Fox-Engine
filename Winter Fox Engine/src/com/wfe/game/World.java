package com.wfe.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.components.ColliderComponent;
import com.wfe.core.Camera;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Firewood;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.jobSystem.Job;
import com.wfe.math.Vector3f;
import com.wfe.pathfinding.PathTileGraph;
import com.wfe.physics.AABB;
import com.wfe.renderEngine.RenderEngine;
import com.wfe.tileEngine.Chunk;
import com.wfe.tileEngine.Terrain;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MousePicker;
import com.wfe.weather.Weather;
import com.wfe.jobSystem.JobType;


public class World {
	
	private static World WORLD;
	
	private Camera camera;
	private Terrain terrain;
	private RenderEngine renderEngine;
	
	private PathTileGraph tileGraph;
	private Tile[][] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();
	private List<Entity> entitiesToAdd = new ArrayList<Entity>();
	
	private List<Entity> mobs = new ArrayList<Entity>();
	
	private Map<Mesh, List<Entity>> entitiesToRender = new HashMap<Mesh, List<Entity>>();
	
	private List<AABB> colliders = new ArrayList<AABB>();
	
	private float time = 12000;
	private Weather weather;
	
	private JobType jobType = JobType.DEVELOPMENT;
	private List<Job> jobList = new ArrayList<Job>();
	
	private Map<Tile, Integer> stockpile = new HashMap<>();
	
	private World(Camera camera) {
		this.camera = camera;
	}
	
	public void init() throws Exception {
		this.terrain = new Terrain(10, 10, camera);
		this.tiles = new Tile[10 * Chunk.SIZE][10 * Chunk.SIZE];
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles.length; j++) {
				tiles[i][j] = terrain.getTile(i, j);
			}
		}
		
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
		//TODO:
		terrain.update(camera.getPosition().x, camera.getPosition().z);
		
		updateController();
		
		if(!entitiesToAdd.isEmpty()) {
			entities.addAll(entitiesToAdd);
			entitiesToAdd.clear();
		}
		
		for(Entity entity : entities) {
			entity.update(dt);
		}
		
		if(!entitiesToRemove.isEmpty()) {
			entities.removeAll(entitiesToRemove);
			entitiesToRemove.clear();
		}
		
		GUIManager.update();
	}
	
	private void updateController() {
		if(Keyboard.isKeyDown(Key.KEY_1)) {
			jobType = JobType.DEVELOPMENT;
			System.out.println(jobType.toString());
		} else if(Keyboard.isKeyDown(Key.KEY_2)) {
			jobType = JobType.GATHERING;
			System.out.println(jobType.toString());
		} else if(Keyboard.isKeyDown(Key.KEY_3)) {
			jobType = JobType.STOCKPILE;
			System.out.println(jobType.toString());
		}
		
		if(Mouse.isButtonDown(0)) {
			if(!Mouse.isActiveInGUI()) {
				Vector3f tp = MousePicker.getCurrentTerrainPoint();
				if(tp != null) {
					Tile tile = getTile((int)tp.getX(), (int)tp.getZ());
					// If tile has any entity (development, gatherable, etc...)
					if(tile != null) {
						switch(jobType) {
						case DEVELOPMENT:
							if(tile.isHasEntity()) {
								jobList.add(new Job(jobType, tile, 10, new Firewood(new Transformation()), null));
							}
							break;
						case GATHERING:
							if(tile.isHasEntity()) {
								Tile stockpileTile = getEmptyStockpile();
								if(stockpileTile != null) {
									stockpile.put(stockpileTile, 0);
									jobList.add(new Job(jobType, tile, 0.0f, null, stockpileTile));
								}
							}
							break;
						case STOCKPILE:
							if(!tile.isHasEntity()) {
								if(tile.getId() != 8) {
									tile.setId(8);
									stockpile.put(tile, -1);
								}
							}
							break;
						}
					}
				}
			}
		}
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
		/*if(entity.hasComponent(ComponentType.MOB)) {
			colliders.add(((MobComponent)entity.getComponent(ComponentType.MOB)).getAABB());
			mobs.add(entity);
		}*/
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
		/*if(entity.hasComponent(ComponentType.MOB)) {
			colliders.remove(((MobComponent)entity.getComponent(ComponentType.MOB)).getAABB());
			mobs.remove(entity);
		}*/
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
		if(x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			System.err.println("World:setTile(...)Выход за пределы массива");
			return;
		}
		
		tiles[x][y].setId(id);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			System.err.println("World:getTile(...)Выход за пределы массива");
			return null;
		}
		
		return tiles[x][y];
	}
	
	public boolean addEntityToTile(Entity entity) {
		Tile tile = tiles[(int)entity.getTransform().getX()] [(int)entity.getTransform().getZ()];
		if(!tile.isHasEntity()) {
			tile.addEntity(entity);
			addEntity(entity);
			return true;
		}
		
		return false;
	}
	
	public void removeEntityFromTile(int x, int y) {
		tiles[x][y].removeEntityPermanently();
	}
	
	public void updateWeather(float dt) {
		time += 15 * dt;
		if(time >= 24000) {
			time = 0;
		}
		
		weather.updateWeather(time, dt);
	}
	
	public int getWidth() {
		return terrain.getWidth();
	}
	
	public int getHeight() {
		return terrain.getHeight();
	}
	
	public PathTileGraph getTileGraph() {
		return tileGraph;
	}
	
	public void setTileGraph(PathTileGraph tileGraph) {
		this.tileGraph = tileGraph;
	}
	
	public List<Job> getJobList() {
		return jobList;
	}
	
	public List<Entity> getMobs() {
		return mobs;
	}
	
	public void cleanup() {
		terrain.cleanup();
		renderEngine.cleanup();
	}
	
	private Tile getEmptyStockpile() {
		for(Tile tile : stockpile.keySet()) {
			// "-1" hasn't entity
			// ">=0" has entity
			if(stockpile.get(tile) == -1) {
				return tile;
			}
		}
		
		return null;
	}
	
}
