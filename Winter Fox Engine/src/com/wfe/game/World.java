package com.wfe.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.components.ColliderComponent;
import com.wfe.core.Camera;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Firewood;
import com.wfe.entities.Selection;
import com.wfe.entities.Stone;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.jobSystem.Job;
import com.wfe.jobSystem.JobType;
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
	private Map<Tile, Integer> garden = new HashMap<>();
	
	private List<Tile> selectedTiles = new ArrayList<>();
	
	private Selection selection;
	
	private Tile firstTile;
	
	private Tile secondTile;
	private Tile lastTile;
	
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
		
		selection = new Selection();
		selection.getTransform().setPosition(85.5f, 0.05f, 86.5f);
		selection.getMaterial().setColor(0.1f, 0.9f, 0.1f, 0.5f);
		addEntity(selection);
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
	
	private void jobModeSelection() {
		if(Keyboard.isKeyDown(Key.KEY_1)) {
			jobType = JobType.DEVELOPMENT;
			System.out.println(jobType.toString());
		} else if(Keyboard.isKeyDown(Key.KEY_2)) {
			jobType = JobType.GATHERING;
			System.out.println(jobType.toString());
		} else if(Keyboard.isKeyDown(Key.KEY_3)) {
			jobType = JobType.STOCKPILE;
			System.out.println(jobType.toString());
		} else if(Keyboard.isKeyDown(Key.KEY_4)) {
			jobType = JobType.PLOWING;
			System.out.println(jobType.toString());
		}
	}
	
	private void jobSelection() {
		if(!Mouse.isActiveInGUI()) {
			switch(jobType) {
			case DEVELOPMENT:
				developmentSelection();
				break;
			case GATHERING:
				gatheringSelection();
				break;
			case STOCKPILE:
				stockpileSelection();
				break;
			case PLOWING:
				plowingSelection();
				break;
			}
		}
	}
	
	private void developmentSelection() {
		if(Mouse.isButtonDown(0)) {
			Tile tile = getTile(MousePicker.getX(), MousePicker.getY());
			if(tile != null) {
				if(tile.isHasEntity()) {
					Entity resource = null;
					int sound = -1;

					switch(tile.getEntity().getTag()) {
					case "tree":
						resource = new Firewood(new Transformation());
						sound = ResourceManager.getSound("chop");
						jobList.add(new Job(jobType, tile, 10, resource, null,
								sound));
						break;
					case "rock":
						resource = new Stone(new Transformation());
						sound = ResourceManager.getSound("mine");
						jobList.add(new Job(jobType, tile, 10, resource, null,
								sound));
					}
				}
			}
		}
	}
	
	private void gatheringSelection() {
		if(Mouse.isButtonDown(0)) {
			Tile tile = getTile(MousePicker.getX(), MousePicker.getY());
			if(tile.isHasEntity()) {
				switch(tile.getEntity().getTag()) {
				case "firewood":
				case "stone":
					Tile stockpileTile = getEmptyStockpile();
					if(stockpileTile != null) {
						stockpile.put(stockpileTile, 0);
						jobList.add(new Job(jobType, tile, 0.0f, null, stockpileTile, 0));
					}
					break;
				}
			}
		}
	}
	
	private void stockpileSelection() {
		if(Mouse.isButtonDown(0)) {
			firstTile = getTile(MousePicker.getX(), MousePicker.getY());
		}
		
		if(Mouse.isButton(0)) {
			if(firstTile != null) {
				secondTile = getTile(MousePicker.getX(), MousePicker.getY());
				
				if(!secondTile.equals(lastTile)) {
					// Unselect early area
					if(lastTile != null) {
						for(Tile tile : selectedTiles) {
							tile.setSelected(false);
						}
						selectedTiles.clear();
					}
					
					int maxX = Math.max(firstTile.getX(), secondTile.getX());
					int maxY = Math.max(firstTile.getY(), secondTile.getY());
					
					int minX = Math.min(firstTile.getX(), secondTile.getX());
					int minY = Math.min(firstTile.getY(), secondTile.getY());
					
					for(int x = minX; x <= maxX; x++) {
						for(int y = minY; y <= maxY; y++) {
							Tile tile = getTile(x, y);
							tile.setSelected(true);
							selectedTiles.add(tile);
						}
					}
					
					lastTile = secondTile;
				}
			}
		}
		
		if(Mouse.isButtonUp(0)) {
			if(firstTile != null) {
				for(Tile tile : selectedTiles) {
					tile.setId(8);
					tile.setSelected(false);
					stockpile.put(tile, -1);
				}
				
				firstTile = null;
				secondTile = null;
				lastTile = null;
				
				selectedTiles.clear();
			}
		}
	}
	
	private void plowingSelection() {
		if(Mouse.isButtonDown(0)) {
			firstTile = getTile(MousePicker.getX(), MousePicker.getY());
		}
		
		if(Mouse.isButton(0)) {
			if(firstTile != null) {
				secondTile = getTile(MousePicker.getX(), MousePicker.getY());
				
				if(!secondTile.equals(lastTile)) {
					// Unselect early area
					if(lastTile != null) {
						for(Tile tile : selectedTiles) {
							tile.setSelected(false);
						}
						selectedTiles.clear();
					}
					
					int maxX = Math.max(firstTile.getX(), secondTile.getX());
					int maxY = Math.max(firstTile.getY(), secondTile.getY());
					
					int minX = Math.min(firstTile.getX(), secondTile.getX());
					int minY = Math.min(firstTile.getY(), secondTile.getY());
					
					for(int x = minX; x <= maxX; x++) {
						for(int y = minY; y <= maxY; y++) {
							Tile tile = getTile(x, y);
							tile.setSelected(true);
							selectedTiles.add(tile);
						}
					}
					
					lastTile = secondTile;
				}
			}
		}
		
		if(Mouse.isButtonUp(0)) {
			if(firstTile != null) {
				for(Tile tile : selectedTiles) {
					tile.setSelected(false);
					jobList.add(new Job(jobType, tile, 1, null, null, 
							ResourceManager.getSound("hoe")));
					garden.put(tile, -1);
				}
				
				firstTile = null;
				secondTile = null;
				lastTile = null;
				
				selectedTiles.clear();
			}
		}
	}
	
	private void updateController() {
		jobModeSelection();
		
		if(!Mouse.isActiveInGUI()) {
			selection.getTransform().setPosition(MousePicker.getX() + 0.5f, 0.05f, MousePicker.getY() + 0.5f);
		}
		
		jobSelection();
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
			System.err.println("World:setTile(...)����� �� ������� �������");
			return;
		}
		
		tiles[x][y].setId(id);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			System.err.println("World:getTile(...)����� �� ������� �������");
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
	
	public Map<Tile, Integer> getStockpile() {
		return stockpile;
	}
	
	public Map<Tile, Integer> getGarden() {
		return garden;
	}
	
}
