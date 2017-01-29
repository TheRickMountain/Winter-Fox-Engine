package com.wfe.game;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
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
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;
import com.wfe.utils.MyRandom;

public class Game implements IGameLogic {
	
	Camera camera;
	public static Player player;
	
	@Override
	public void loadResources() throws Exception {
		/*** Audio Initialization ***/
		AudioMaster.init();
		AudioMaster.setListenerData(0, 0, 0);
		
		ResourceManager.loadSound("eating", AudioMaster.loadSound("audio/eating.wav"));
		ResourceManager.loadSound("taking", AudioMaster.loadSound("audio/taking.wav"));
		/*** *** ***/
		
		
		/*** Textures ***/	
		ResourceManager.loadTexture("wall", Texture.newTexture(new MyFile("textures/wall.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadTexture("primitiveFont", Texture.newTexture(new MyFile("font/primitiveFont.png"))
				.normalMipMap()
				.create());
		
		ResourceManager.loadTexture("myFont", Texture.newTexture(new MyFile("font/myFont.png"))
				.normalMipMap()
				.create());
		
		ResourceManager.loadTexture("fern", Texture.newTexture(new MyFile("entity/fern/fern.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadTexture("grass", Texture.newTexture(new MyFile("entity/grass/grass.png"))
				.normalMipMap(-0.4f)
				.create());
			
		ResourceManager.loadTexture("banana_ui", Texture.newTexture(new MyFile("gui/items/banana.png")).create());
		ResourceManager.loadTexture("log_ui", Texture.newTexture(new MyFile("gui/items/log.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("wheat_seed_ui", Texture.newTexture(new MyFile("gui/items/wheat_seed.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("rope_ui", Texture.newTexture(new MyFile("gui/items/rope.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("fiber_ui", Texture.newTexture(new MyFile("gui/items/fiber.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("cookie_ui", Texture.newTexture(new MyFile("gui/items/cookie.png")).create());
		ResourceManager.loadTexture("apple_ui", Texture.newTexture(new MyFile("gui/items/apple.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("wall_ui", Texture.newTexture(new MyFile("gui/items/wall.png")).create());
		ResourceManager.loadTexture("cross_wall_ui", Texture.newTexture(new MyFile("gui/items/cross_wall.png")).create());
		ResourceManager.loadTexture("window_wall_ui", Texture.newTexture(new MyFile("gui/items/window_wall.png")).create());
		ResourceManager.loadTexture("door_wall_ui", Texture.newTexture(new MyFile("gui/items/door_wall.png")).create());
		ResourceManager.loadTexture("axe_ui", Texture.newTexture(new MyFile("gui/items/axe.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("shroom_ui", Texture.newTexture(new MyFile("gui/items/shroom.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("bread_ui", Texture.newTexture(new MyFile("gui/items/bread.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("bow_ui", Texture.newTexture(new MyFile("gui/items/bow.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("bush_ui", Texture.newTexture(new MyFile("gui/items/bush.png")).create());
		ResourceManager.loadTexture("slot_ui", Texture.newTexture(new MyFile("gui/slot.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("slot_light_ui", Texture.newTexture(new MyFile("gui/slot_light.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("sack_ui", Texture.newTexture(new MyFile("gui/sack.png")).create());
		ResourceManager.loadTexture("flour_ui", Texture.newTexture(new MyFile("gui/items/flour.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("dough_ui", Texture.newTexture(new MyFile("gui/items/dough.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("craft_ui", Texture.newTexture(new MyFile("gui/craft.png")).create());
		ResourceManager.loadTexture("list_ui", Texture.newTexture(new MyFile("gui/list.png")).create());
		ResourceManager.loadTexture("hoe_ui", Texture.newTexture(new MyFile("gui/items/hoe.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("background_frame_ui", Texture.newTexture(new MyFile("gui/background_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("corner_frame_ui", Texture.newTexture(new MyFile("gui/corner_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("h_edge_frame_ui", Texture.newTexture(new MyFile("gui/h_edge_frame.png")).create());
		ResourceManager.loadTexture("v_edge_frame_ui", Texture.newTexture(new MyFile("gui/v_edge_frame.png")).create());
		ResourceManager.loadTexture("health_icon_ui", Texture.newTexture(new MyFile("gui/health_icon.png")).create());
		ResourceManager.loadTexture("hunger_icon_ui", Texture.newTexture(new MyFile("gui/hunger_icon.png")).create());
		ResourceManager.loadTexture("thirst_icon_ui", Texture.newTexture(new MyFile("gui/thirst_icon.png")).create());
		
		/*** Meshes ***/
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/models/wall.obj"));
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/models/cross_wall.obj"));
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/models/door_wall.obj"));
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/models/window_wall.obj"));
		ResourceManager.loadMesh("fern", OBJLoader.loadMesh("/entity/fern/fern.obj"));
		ResourceManager.loadMesh("grass", OBJLoader.loadMesh("/entity/grass/grass.obj"));
		
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
		camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);
		World.getWorld().init();
		
		player = new Player(camera, new Transformation(80, 0.65f, 80));
		World.getWorld().addEntity(player);
		
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(81 + 0.5f, 0, 82 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(82 + 0.5f, 0, 82 + 0.5f)));
		
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
					int tileType = World.getWorld().getTile(i, j);
					switch (tileType) {
						case 4:
						case 5:
						case 8:
						case 9:
							grass.setTextureIndex(MyRandom.nextInt(3, 4, 5, 6));
							break;
						case 6:
						case 7:
						case 10:
						case 11:
							grass.setTextureIndex(MyRandom.nextInt(3, 4, 5, 6));
							break;
					}
						
					World.getWorld().addEntityToTile(grass);
				}
			}
		}
		
		//OpenglUtils.goWireframe(true);
	}
	
	@Override
	public void update(float dt) {	
		World.getWorld().update(dt, player);
		player.update(dt);
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
