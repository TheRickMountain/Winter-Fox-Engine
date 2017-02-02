package com.wfe.game;

import com.wfe.audio.AudioMaster;
import com.wfe.components.InventoryComponent;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Fern;
import com.wfe.entities.Flint;
import com.wfe.entities.Grass;
import com.wfe.entities.Pine;
import com.wfe.entities.Player;
import com.wfe.entities.Rock;
import com.wfe.entities.Shroom;
import com.wfe.entities.Stick;
import com.wfe.entities.Wheat;
import com.wfe.graph.OBJLoader;
import com.wfe.gui.Item;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;
import com.wfe.utils.MyRandom;

public class Game implements IGameLogic {
	
	public static Player player;
	
	@Override
	public void loadResources() throws Exception {
		/*** Audio Initialization ***/
		AudioMaster.init();
		AudioMaster.setListenerData(0, 0, 0);
		
		ResourceManager.loadSound("eating", AudioMaster.loadSound("audio/eat.wav"));
		ResourceManager.loadSound("taking", AudioMaster.loadSound("audio/take.wav"));
		ResourceManager.loadSound("chopping", AudioMaster.loadSound("audio/chop.wav"));
		ResourceManager.loadSound("equip", AudioMaster.loadSound("audio/equip.wav"));
		ResourceManager.loadSound("inventory", AudioMaster.loadSound("audio/inventory.wav"));
		/*** *** ***/
		
		
		/*** Textures ***/	
		ResourceManager.loadTexture("wall", Texture.newTexture(new MyFile("textures/wall.png"))
				.normalMipMap(-0.4f).create());
		
		ResourceManager.loadTexture("primitiveFont", Texture.newTexture(new MyFile("font/primitiveFont.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("myFont", Texture.newTexture(new MyFile("font/myFont.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("apple_ui", Texture.newTexture(new MyFile("gui/items/apple.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("slot_ui", Texture.newTexture(new MyFile("gui/elements/slot.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("background_frame_ui", Texture.newTexture(new MyFile("gui/background_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("corner_frame_ui", Texture.newTexture(new MyFile("gui/corner_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("h_edge_frame_ui", Texture.newTexture(new MyFile("gui/h_edge_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("v_edge_frame_ui", Texture.newTexture(new MyFile("gui/v_edge_frame.png"))
				.normalMipMap().create());
		
		
		/*** Meshes ***/
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/models/wall.obj"));
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/models/cross_wall.obj"));
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/models/door_wall.obj"));
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/models/window_wall.obj"));
		
		/*** Fern ***/
		ResourceManager.loadTexture("fern", Texture.newTexture(new MyFile("entity/fern/fern.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("fern", OBJLoader.loadMesh("/entity/fern/fern.obj"));
		/*** *** ***/
		
		/*** Grass ***/
		ResourceManager.loadTexture("grass", Texture.newTexture(new MyFile("entity/grass/grass.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("grass", OBJLoader.loadMesh("/entity/grass/grass.obj"));
		/*** *** ***/
		
		/*** Pine ***/
		ResourceManager.loadTexture("pine_bark", Texture.newTexture(new MyFile("entity/pine/bark.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadTexture("pine_leaves", Texture.newTexture(new MyFile("entity/pine/leaves.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("pine_bark", OBJLoader.loadMesh("/entity/pine/bark.obj"));
		ResourceManager.loadMesh("pine_leaves", OBJLoader.loadMesh("/entity/pine/leaves.obj"));
		/*** *** ***/
		
		/*** Willow ***/		
		ResourceManager.loadTexture("willow", Texture.newTexture(new MyFile("entity/willow/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("willow", OBJLoader.loadMesh("/entity/willow/model.obj"));
		/*** *** ***/
		
		/*** Shroom ***/
		ResourceManager.loadTexture("shroom", Texture.newTexture(new MyFile("entity/shroom/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("shroom", OBJLoader.loadMesh("/entity/shroom/model.obj"));
		/*** *** ***/
		
		/*** Bush ***/
		ResourceManager.loadTexture("bush", Texture.newTexture(new MyFile("entity/bush/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("bush", OBJLoader.loadMesh("/entity/bush/model.obj"));
		/*** *** ***/
		
		/*** Amanita ***/
		ResourceManager.loadTexture("amanita_ui", Texture.newTexture(new MyFile("entity/amanita/icon.png"))
				.create());
		ResourceManager.loadTexture("amanita", Texture.newTexture(new MyFile("entity/amanita/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("amanita", OBJLoader.loadMesh("/entity/amanita/model.obj"));
		/*** *** ***/
		
		/*** Axe ***/
		ResourceManager.loadTexture("axe_ui", Texture.newTexture(new MyFile("entity/axe/icon.png"))
				.create());
		ResourceManager.loadTexture("axe", Texture.newTexture(new MyFile("entity/axe/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("axe", OBJLoader.loadMesh("/entity/axe/model.obj"));
		/*** *** ***/
		
		/*** Campfire ***/
		ResourceManager.loadTexture("campfire_ui", Texture.newTexture(new MyFile("entity/campfire/icon.png"))
				.create());
		ResourceManager.loadTexture("campfire", Texture.newTexture(new MyFile("entity/campfire/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("campfire", OBJLoader.loadMesh("/entity/campfire/model.obj"));
		/*** *** ***/
		
		/*** Furnace ***/
		ResourceManager.loadTexture("furnace_ui", Texture.newTexture(new MyFile("entity/furnace/icon.png"))
				.create());
		ResourceManager.loadTexture("furnace", Texture.newTexture(new MyFile("entity/furnace/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("furnace", OBJLoader.loadMesh("/entity/furnace/model.obj"));
		/*** *** ***/
		
		/*** Barrel ***/
		ResourceManager.loadTexture("barrel_ui", Texture.newTexture(new MyFile("entity/barrel/icon.png"))
				.create());
		ResourceManager.loadTexture("barrel", Texture.newTexture(new MyFile("entity/barrel/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("barrel", OBJLoader.loadMesh("/entity/barrel/model.obj"));
		/*** *** ***/
		
		/*** Flint ***/
		ResourceManager.loadTexture("flint_ui", Texture.newTexture(new MyFile("entity/flint/icon.png"))
				.create());
		ResourceManager.loadTexture("flint", Texture.newTexture(new MyFile("entity/flint/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("flint", OBJLoader.loadMesh("/entity/flint/model.obj"));
		/*** *** ***/
		
		/*** Stick ***/
		ResourceManager.loadTexture("stick_ui", Texture.newTexture(new MyFile("entity/stick/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("stick", Texture.newTexture(new MyFile("entity/stick/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("stick", OBJLoader.loadMesh("/entity/stick/model.obj"));
		/*** *** ***/
		
		/*** Wheat ***/
		ResourceManager.loadTexture("wheat_ui", Texture.newTexture(new MyFile("entity/wheat/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("wheat", Texture.newTexture(new MyFile("entity/wheat/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("wheat", OBJLoader.loadMesh("/entity/wheat/model.obj"));
		/*** *** ***/
		
		/*** Rock ***/
		ResourceManager.loadTexture("rock", Texture.newTexture(new MyFile("entity/rock/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("rock", OBJLoader.loadMesh("/entity/rock/rock_1.obj"));
		/*** *** ***/
		
		/*** Player ***/
		ResourceManager.loadTexture("player", Texture.newTexture(new MyFile("entity/player/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("eyes", Texture.newTexture(new MyFile("entity/player/eyes.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("body", OBJLoader.loadMesh("/entity/player/body.obj"));
		ResourceManager.loadMesh("head", OBJLoader.loadMesh("/entity/player/head.obj"));
		ResourceManager.loadMesh("eyes", OBJLoader.loadMesh("/entity/player/eyes.obj"));
		/*** *** ***/
	}
	
	@Override
	public void onEnter(Display display) throws Exception {
		Camera camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);
		World.getWorld().init();
		
		player = new Player(camera, new Transformation(80, 0.4f, 80));
		World.getWorld().addEntity(player);
		
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(81 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(82 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(83 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(84 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(85 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(86 + 0.5f, 0, 82 + 0.5f)));
		
		World.getWorld().addEntityToTile(new Rock(player, new Transformation(80 + 0.5f, 0, 84 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(80 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(81 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(82 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(83 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(84 + 0.5f, 0, 83 + 0.5f)));
		
		World.getWorld().addEntityToTile(new Pine(player, new Transformation(90.5f, 0, 88.5f)));
		World.getWorld().addEntityToTile(new Pine(player, new Transformation(70.5f, 0, 85.5f)));
		
		for(int i = 0; i < 100; i++) {		
			Shroom shroom = new Shroom(player, new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			World.getWorld().addEntityToTile(shroom);
			
			Fern fern = new Fern(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			fern.setTextureIndex(MyRandom.nextInt(4));
			World.getWorld().addEntityToTile(fern);
		
			World.getWorld().addEntityToTile(new Flint(player, new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
		}
		
		for(int i = 60; i < 120; i++) {
			for(int j = 60; j < 120; j++) {
				int send = MyRandom.nextInt(5);
				if(send == 1) {
					Grass grass = new Grass(player, new Transformation(i, 0, j));
					grass.setTextureIndex(MyRandom.nextInt(3, 4, 5, 6));		
					World.getWorld().addEntityToTile(grass);
				}
			}
		}
		
		InventoryComponent inventory = (InventoryComponent) player.getComponent(ComponentType.INVENTORY);
		inventory.addItem(Item.APPLE, 5);
		inventory.addItem(Item.FLINT, 7);
		inventory.addItem(Item.AXE, 2);
	}
	
	@Override
	public void update(float dt) {	
		if(Keyboard.isKeyDown(Key.KEY_F)) {
			InventoryComponent inventory = (InventoryComponent) player.getComponent(ComponentType.INVENTORY);
			inventory.addItem(Item.APPLE, 5);
		}
		
		World.getWorld().update(dt, player);
	}

	@Override
	public void render() {
		World.getWorld().render();
	}

	@Override
	public void onExit() {
		World.getWorld().cleanup();
		AudioMaster.cleanup();
	}

}
