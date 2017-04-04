package com.wfe.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.audio.SoundManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Firewood;
import com.wfe.entities.Selection;
import com.wfe.entities.Stone;
import com.wfe.graph.Mesh;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.jobSystem.Job;
import com.wfe.jobSystem.JobType;
import com.wfe.pathfinding.PathTileGraph;
import com.wfe.renderEngine.RenderEngine;
import com.wfe.terrain.Chunk;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.Tile;
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
	
	private Map<Mesh, List<Entity>> entitiesToRender = new HashMap<Mesh, List<Entity>>();
	
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
	
	private World(Camera camera, RenderEngine renderEngine) {
		this.camera = camera;
		this.renderEngine = renderEngine;
		
		init();
	}
	
	private void init() {
		this.terrain = new Terrain(10, 10, camera);
		this.tiles = new Tile[10 * Chunk.SIZE][10 * Chunk.SIZE];
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles.length; j++) {
				tiles[i][j] = terrain.getTile(i, j);
			}
		}
		
		this.weather = new Weather();
		
		MousePicker.setUpMousePicker(camera);
		
		selection = new Selection();
		selection.getTransform().setPosition(85.5f, 0.05f, 86.5f);
		selection.getMaterial().setColor(0.1f, 0.9f, 0.1f, 0.5f);
		addEntity(selection);
	}
	
	public static World create(Camera camera, RenderEngine renderEngine) throws Exception {
		if(WORLD == null)
			WORLD = new World(camera, renderEngine);
		
		return WORLD;
	}
	
	public static World getWorld() {
		return WORLD;
	}
	
	public void update(float dt) {
		camera.update(dt);
		SoundManager.setListenerData(camera.getX(), camera.getY(), camera.getZ());
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
						tile.setSelected(true, 255, 255, 128, 128);
						resource = new Firewood(new Transformation());
						sound = ResourceManager.getSound("chop");
						jobList.add(new Job(jobType, tile, 10, resource, null,
								sound));
						break;
					case "rock":
						tile.setSelected(true, 255, 255, 128, 128);
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
				tile.setSelected(true, 255, 255, 128, 128);
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
							tile.setSelected(false, 0, 0, 0, 0);
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
							tile.setSelected(true, 82, 22, 180, 64);
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
					tile.setSelected(false, 0, 0, 0, 0);
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
							tile.setSelected(false, 0, 0, 0, 0);
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
							tile.setSelected(true, 82, 22, 180, 64);
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
					tile.setSelected(false, 0, 0, 0, 0);
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
		//AudioMaster.setListenerData(camera.getX(), camera.getY(), camera.getZ());
		
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
		this.entitiesToRemove.add(entity);
		
		List<Entity> batch = entitiesToRender.get(entity.getMesh());
		batch.remove(entity);
		
		if(batch.isEmpty()) {
			entitiesToRender.remove(entity.getMesh());
		}
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
		time += 500 * dt;
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
	
	public float getTime() {
		return time;
	}
	
}
