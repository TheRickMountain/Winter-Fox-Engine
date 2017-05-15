package com.wfe.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfe.components.ColliderComponent;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.EntityCache;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Mesh;
import com.wfe.gui.GUIManager;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.Slot;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
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
	
	private Tile[][] tiles;
	
	public List<Entity> npc = new ArrayList<Entity>();
	
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
		
		tiles = new Tile[width][height];
		
		terrain = new Terrain(tiles, w, h, camera);
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
		if(Keyboard.isKeyDown(Key.KEY_F5)) {
			save();
		}
		
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
		
		GUIManager.update(dt);
	}
	
	public void render() {
		renderEngine.clear();
		terrain.render();
		renderEngine.render(entitiesToRender);	
	}
	
	public void addEntity(Entity entity) {
		entity.init();
		
		if(entity.hasComponent(ComponentType.NPC)) {
			npc.add(entity);
		}
		
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
		
		for(Entity child : entity.children) {
			addEntity(child);
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
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return;
		}
		
		tiles[x][y].setId(id);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return null;
		}
		
		return tiles[x][y];
	}
	
	public boolean addEntityToTile(Entity entity) {
		int x = (int)entity.getTransform().getX();
		int y = (int)entity.getTransform().getZ();
		
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		}
		
		Tile tile = tiles[x][y];
		if(!tile.isHasEntity()) {
			tile.setEntity(entity);
			addEntity(entity);
			return true;
		}
		return false;
	}
	
	public void removeEntityFromTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return;
		}
		
		tiles[x][y].removeEntity();
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

	public void save() {
		System.out.println("Saving...");
		
		try {
			FileWriter fstream = new FileWriter("saves/player.dat");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("[stats]\n");
			out.write("health:" + GUIManager.stats.getHealth() + "\n");
			out.write("hunger:" + GUIManager.stats.getHunger() + "\n");
			out.write("thirst:" + GUIManager.stats.getThirst() + "\n");
			out.write("cowry:" + GUIManager.stats.getCowry() + "\n");
			
			out.write("[inventory]\n");
			List<Slot> slots = GUIManager.inventory.slots;
			for(int i = 0; i < slots.size(); i++) {
				Slot slot = slots.get(i);
				out.write(i + ":" + slot.getItem().id + ":" + slot.getCount() + "\n");
			}
			
			out.close();
			
			fstream = new FileWriter("saves/world.dat");
			out = new BufferedWriter(fstream);
			
			for(Entity entity : entities) {
				if(entity.getId() != 0) {
					Transformation transform = entity.getTransform();
					out.write(entity.getId() + "," + 
					transform.getX() + "," + 
					transform.getY() + "," + 
					transform.getZ() + "," +
					transform.getRotX() + "," + 
					transform.getRotY() + "," + 
					transform.getRotZ() + "," +
					transform.getScaleX() + "," +
					transform.getScaleY() + "," +
					transform.getScaleZ() + "," +
					entity.getTextureIndex() + "\n");
				}
			}
			
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Successful saved!");
	}
	
	public void loadPlayer(String saveName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(saveName));
			String line = "";
			while((line = reader.readLine()) != null) {
				if(line.equals("[inventory]")) {
					break;
				}
				
				String[] stats = line.split(":");
				
				switch(stats[0]) {
				case "health":
					GUIManager.stats.setHealth(Integer.parseInt(stats[1]));
					break;
				case "hunger":
					GUIManager.stats.setHunger(Integer.parseInt(stats[1]));
					break;
				case "thirst":
					GUIManager.stats.setThirst(Integer.parseInt(stats[1]));
					break;
				case "cowry":
					GUIManager.stats.setCowry(Integer.parseInt(stats[1]));
					break;
				}
			}
			
			while((line = reader.readLine()) != null) {
				String[] item = line.split(":");
				if(item[1] != "") {
					GUIManager.inventory.slots.get(Integer.parseInt(item[0])).addItem(
							ItemDatabase.getItem(Integer.parseInt(item[1])), Integer.parseInt(item[2]));
				}
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWorld(String saveName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(saveName));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				Entity entity = EntityCache.getEntityById(Integer.parseInt(values[0]));
				Transformation transform = entity.getTransform();
				transform.setPosition(
						Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
				transform.setRotation(
						Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6]));
				transform.setScale(
						Float.parseFloat(values[7]), Float.parseFloat(values[8]), Float.parseFloat(values[9]));
				entity.setTextureIndex(Integer.parseInt(values[10]));
				addEntityToTile(entity);
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
