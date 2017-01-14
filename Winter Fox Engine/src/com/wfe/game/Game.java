package com.wfe.game;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Amanita;
import com.wfe.entities.Fern;
import com.wfe.entities.Flint;
import com.wfe.entities.Grass;
import com.wfe.entities.Player;
import com.wfe.entities.Shroom;
import com.wfe.entities.Stick;
import com.wfe.graph.OBJLoader;
import com.wfe.gui.GUIText;
import com.wfe.math.Vector3f;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;
import com.wfe.utils.MyRandom;

public class Game implements IGameLogic {
	
	Camera camera;
	Player player;
	
	@Override
	public void loadResources() throws Exception {
		/*** Textures ***/
		ResourceManager.loadTexture("wall", Texture.newTexture(new MyFile("textures/wall.png"))
				.normalMipMap(-0.4f)
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
		ResourceManager.loadTexture("fiber_ui", Texture.newTexture(new MyFile("gui/items/fiber.png")).create());
		ResourceManager.loadTexture("cookie_ui", Texture.newTexture(new MyFile("gui/items/cookie.png")).create());
		ResourceManager.loadTexture("apple_ui", Texture.newTexture(new MyFile("gui/items/apple.png")).create());
		ResourceManager.loadTexture("wall_ui", Texture.newTexture(new MyFile("gui/items/wall.png")).create());
		ResourceManager.loadTexture("cross_wall_ui", Texture.newTexture(new MyFile("gui/items/cross_wall.png")).create());
		ResourceManager.loadTexture("window_wall_ui", Texture.newTexture(new MyFile("gui/items/window_wall.png")).create());
		ResourceManager.loadTexture("door_wall_ui", Texture.newTexture(new MyFile("gui/items/door_wall.png")).create());
		ResourceManager.loadTexture("axe_ui", Texture.newTexture(new MyFile("gui/items/axe.png")).create());
		ResourceManager.loadTexture("shroom_ui", Texture.newTexture(new MyFile("gui/items/shroom.png")).create());
		ResourceManager.loadTexture("bread_ui", Texture.newTexture(new MyFile("gui/items/bread.png")).create());
		ResourceManager.loadTexture("bush_ui", Texture.newTexture(new MyFile("gui/items/bush.png")).create());
		ResourceManager.loadTexture("slot_ui", Texture.newTexture(new MyFile("gui/slot.png")).create());
		ResourceManager.loadTexture("hoe_ui", Texture.newTexture(new MyFile("gui/items/hoe.png")).create());
		ResourceManager.loadTexture("background_frame_ui", Texture.newTexture(new MyFile("gui/background_frame.png")).create());
		ResourceManager.loadTexture("corner_frame_ui", Texture.newTexture(new MyFile("gui/corner_frame.png")).create());
		ResourceManager.loadTexture("h_edge_frame_ui", Texture.newTexture(new MyFile("gui/h_edge_frame.png")).create());
		ResourceManager.loadTexture("v_edge_frame_ui", Texture.newTexture(new MyFile("gui/v_edge_frame.png")).create());
		ResourceManager.loadTexture("health_icon_ui", Texture.newTexture(new MyFile("gui/health_icon.png")).create());
		ResourceManager.loadTexture("hunger_icon_ui", Texture.newTexture(new MyFile("gui/hunger_icon.png")).create());
		
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
		
		/*** Tomato ***/
		ResourceManager.loadTexture("tomato_plant", Texture.newTexture(new MyFile("entity/tomatoe/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("tomato_plant", OBJLoader.loadMesh("/entity/tomatoe/model.obj"));
		
		ResourceManager.loadTexture("tomatoes", Texture.newTexture(new MyFile("entity/tomatoe/tomatoes.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("tomatoes", OBJLoader.loadMesh("/entity/tomatoe/tomatoes.obj"));
		/*** *** ***/
		
		/*** Amanita ***/
		ResourceManager.loadTexture("amanita_ui", Texture.newTexture(new MyFile("entity/amanita/icon.png"))
				.create());
		ResourceManager.loadTexture("amanita", Texture.newTexture(new MyFile("entity/amanita/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("amanita", OBJLoader.loadMesh("/entity/amanita/model.obj"));
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
				.create());
		ResourceManager.loadTexture("stick", Texture.newTexture(new MyFile("entity/stick/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("stick", OBJLoader.loadMesh("/entity/stick/model.obj"));
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
		
		GUIText text = new GUIText("Winter Fox Engine", 1.1f, 
				FontRenderer.font, 0.875f, 0.0f, 0.125f, true);
		text.setColor(1, 1, 1);
		World.getWorld().addGUIText(text);
			
		World.getWorld().addEntityToTile(new Shroom(player, new Transformation(80 + 0.5f, 0, 80 + 0.5f)));
		World.getWorld().addEntityToTile(new Shroom(player, new Transformation(81 + 0.5f, 0, 80 + 0.5f)));
		World.getWorld().addEntityToTile(new Shroom(player, new Transformation(82 + 0.5f, 0, 80 + 0.5f)));
		
		World.getWorld().addEntityToTile(new Amanita(player, new Transformation(80 + 0.5f, 0, 81 + 0.5f)));
		World.getWorld().addEntityToTile(new Amanita(player, new Transformation(81 + 0.5f, 0, 81 + 0.5f)));
		World.getWorld().addEntityToTile(new Amanita(player, new Transformation(82 + 0.5f, 0, 81 + 0.5f)));
		
		World.getWorld().addEntityToTile(new Grass(new Transformation(80, 0, 82)).setTextureIndex(0));
		World.getWorld().addEntityToTile(new Grass(new Transformation(81, 0, 82)).setTextureIndex(1));
		World.getWorld().addEntityToTile(new Grass(new Transformation(82, 0, 82)).setTextureIndex(2));
		
		World.getWorld().addEntityToTile(new Flint(player, new Transformation(80 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(81 + 0.5f, 0, 83 + 0.5f)));
		World.getWorld().addEntityToTile(new Stick(player, new Transformation(82 + 0.5f, 0, 83 + 0.5f)));
		
		for(int i = 0; i < 100; i++) {		
			Shroom shroom = new Shroom(player, new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			World.getWorld().addEntityToTile(shroom);
			
			Fern fern = new Fern(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			fern.setTextureIndex(MyRandom.nextInt(4));
			World.getWorld().addEntityToTile(fern);
		}
		
		for(int i = 60; i < 120; i++) {
			for(int j = 60; j < 120; j++) {
				int send = MyRandom.nextInt(5);
				if(send == 1) {
					Grass grass = new Grass(new Transformation(i, 0, j));
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
		
		/*Transformation pineTransform = new Transformation(90.5f, 0, 88.5f);
		pineTransform.setScale(0.4f);
		StaticEntity pineBark = new StaticEntity(ResourceManager.getMesh("pine_bark"),
				new Material(ResourceManager.getTexture("pine_bark")), pineTransform);
		pineBark.addComponent(new ColliderComponent(0.5f, 1, 0.5f, pineTransform));
		World.getWorld().addEntityToTile(pineBark);
		
		StaticEntity pineLeaves = new StaticEntity(ResourceManager.getMesh("pine_leaves"),
				new Material(ResourceManager.getTexture("pine_leaves")).setHasTransparency(true), pineTransform);
		World.getWorld().addEntity(pineLeaves);
		
		StaticEntity willow = new StaticEntity(ResourceManager.getMesh("willow"),
				new Material(ResourceManager.getTexture("willow")).setHasTransparency(true), 
				new Transformation(86.5f, 0, 90.5f));
		willow.addComponent(new ColliderComponent(0.5f, 1, 0.5f, willow.getTransform()));
		World.getWorld().addEntityToTile(willow);*/
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
	}

}
