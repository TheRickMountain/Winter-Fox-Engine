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
import com.wfe.entities.Mushroom;
import com.wfe.entities.Pine;
import com.wfe.entities.Player;
import com.wfe.entities.Rock;
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
		
		ResourceManager.loadSound("hoe", AudioMaster.loadSound("audio/hoe.wav"));
		ResourceManager.loadSound("chop", AudioMaster.loadSound("audio/chop.wav"));
		ResourceManager.loadSound("mine", AudioMaster.loadSound("audio/mine.wav"));
		ResourceManager.loadSound("eating", AudioMaster.loadSound("audio/eat.wav"));
		ResourceManager.loadSound("tick", AudioMaster.loadSound("audio/tick.wav"));
		ResourceManager.loadSound("taking", AudioMaster.loadSound("audio/take.wav"));
		ResourceManager.loadSound("chopping", AudioMaster.loadSound("audio/chop.wav"));
		ResourceManager.loadSound("equip", AudioMaster.loadSound("audio/equip.wav"));
		ResourceManager.loadSound("inventory", AudioMaster.loadSound("audio/inventory.wav"));
		ResourceManager.loadSound("footstep1", AudioMaster.loadSound("audio/footstep1.wav"));
		ResourceManager.loadSound("footstep2", AudioMaster.loadSound("audio/footstep2.wav"));
		
		ResourceManager.loadSound("hills", AudioMaster.loadSound("audio/hills.wav"));
		/*** *** ***/
		
		/*** Font ***/
		ResourceManager.loadTexture("primitiveFont", Texture.newTexture(new MyFile("font/primitiveFont.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("myFont", Texture.newTexture(new MyFile("font/myFont.png"))
				.normalMipMap().create());
		/*** *** ***/
		
		/*** GUI Elements ***/							
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
		
		/* Icons */
		ResourceManager.loadTexture("apple_ui", Texture.newTexture(new MyFile("gui/items/apple.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("fiber_ui", Texture.newTexture(new MyFile("gui/items/fiber.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("log_ui", Texture.newTexture(new MyFile("gui/items/log.png"))
				.normalMipMap().create());
		/* * */
		
		/*** *** ***/
		
		/*** Fern ***/
		ResourceManager.loadTexture("fern", Texture.newTexture(new MyFile("entity/fern/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("fern", OBJLoader.loadMesh("/entity/fern/model.obj"));
		/*** *** ***/
		
		/*** Walls ***/
		ResourceManager.loadTexture("walls", Texture.newTexture(new MyFile("entity/walls/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadTexture("cross_wall_ui", Texture.newTexture(new MyFile("entity/walls/cross_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/entity/walls/cross_wall.obj"));
		
		ResourceManager.loadTexture("door_wall_ui", Texture.newTexture(new MyFile("entity/walls/door_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/entity/walls/door_wall.obj"));
		
		ResourceManager.loadTexture("window_wall_ui", Texture.newTexture(new MyFile("entity/walls/window_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/entity/walls/window_wall.obj"));
		
		ResourceManager.loadTexture("wall_ui", Texture.newTexture(new MyFile("entity/walls/wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/entity/walls/wall.obj"));
		/*** *** ***/
		
		/*** Rock ***/
		ResourceManager.loadTexture("rock", Texture.newTexture(new MyFile("entity/rock/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("rock", OBJLoader.loadMesh("/entity/rock/model.obj"));
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
		
		/*** Mushroom ***/
		ResourceManager.loadTexture("mushroom_ui", Texture.newTexture(new MyFile("entity/mushroom/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("mushroom", Texture.newTexture(new MyFile("entity/mushroom/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("mushroom", OBJLoader.loadMesh("/entity/mushroom/model.obj"));
		/*** *** ***/
		
		/*** Pickaxe ***/
		ResourceManager.loadTexture("hoe_ui", Texture.newTexture(new MyFile("entity/hoe/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("hoe", Texture.newTexture(new MyFile("entity/hoe/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("hoe", OBJLoader.loadMesh("/entity/hoe/model.obj"));
		/*** *** ***/
		
		/*** Pickaxe ***/
		ResourceManager.loadTexture("pickaxe_ui", Texture.newTexture(new MyFile("entity/pickaxe/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("pickaxe", Texture.newTexture(new MyFile("entity/pickaxe/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("pickaxe", OBJLoader.loadMesh("/entity/pickaxe/model.obj"));
		/*** *** ***/
		
		/*** Axe ***/
		ResourceManager.loadTexture("axe_ui", Texture.newTexture(new MyFile("entity/axe/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("axe", Texture.newTexture(new MyFile("entity/axe/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("axe", OBJLoader.loadMesh("/entity/axe/model.obj"));
		/*** *** ***/
		
		/*** Flint ***/
		ResourceManager.loadTexture("flint_ui", Texture.newTexture(new MyFile("entity/flint/icon.png"))
				.normalMipMap().create());
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
		ResourceManager.loadMesh("hip", OBJLoader.loadMesh("/entity/player/hip.obj"));
		ResourceManager.loadMesh("shin", OBJLoader.loadMesh("/entity/player/shin.obj"));
		ResourceManager.loadMesh("leftArm", OBJLoader.loadMesh("/entity/player/leftArm.obj"));
		ResourceManager.loadMesh("leftForearm", OBJLoader.loadMesh("/entity/player/leftForearm.obj"));
		ResourceManager.loadMesh("rightArm", OBJLoader.loadMesh("/entity/player/rightArm.obj"));
		ResourceManager.loadMesh("rightForearm", OBJLoader.loadMesh("/entity/player/rightForearm.obj"));
		/*** *** ***/
	}
	
	@Override
	public void onEnter(Display display) throws Exception {
		Camera camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);
		World.getWorld().init();
		
		player = new Player(camera, new Transformation(80, 0.65f, 80));
		World.getWorld().addEntity(player);
		
		World.getWorld().addEntityToTile(new Rock(player, new Transformation(81.5f, 0, 84.5f)));
		World.getWorld().addEntityToTile(new Rock(player, new Transformation(82.5f, 0, 84.5f)));
		World.getWorld().addEntityToTile(new Rock(player, new Transformation(83.5f, 0, 85.5f)));
		World.getWorld().addEntityToTile(new Rock(player, new Transformation(84.5f, 0, 86.5f)));
		
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(81 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(82 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(83 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(84 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(85 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(86 + 0.5f, 0, 82 + 0.5f)));
		
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(80 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(81 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(82 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(83 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Wheat(player, new Transformation(84 + 0.5f, 0, 83 + 0.5f)));
		
		World.getWorld().addEntityToTile(new Pine(player, new Transformation(90.5f, 0, 88.5f)));
		World.getWorld().addEntityToTile(new Pine(player, new Transformation(93.5f, 0, 88.5f)));
		
		for(int i = 0; i < 100; i++) {		
			Mushroom shroom = new Mushroom(player, new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
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
		inventory.addItem(Item.AXE, 1);
		inventory.addItem(Item.HOE, 1);
		inventory.addItem(Item.PICKAXE, 1);
		inventory.addItem(Item.CROSS_WALL, 4);
		inventory.addItem(Item.WINDOW_WALL, 5);
		inventory.addItem(Item.DOOR_WALL, 1);
		inventory.addItem(Item.WALL, 6);
		inventory.addItem(Item.APPLE, 5);
		
		AudioMaster.ambientSource.play(ResourceManager.getSound("hills"));
	}
	
	@Override
	public void update(float dt) {	
		if(Keyboard.isKeyDown(Key.KEY_H)) {
			InventoryComponent inventory = (InventoryComponent) player.getComponent(ComponentType.INVENTORY);
			inventory.addItem(Item.APPLE, 32);
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
