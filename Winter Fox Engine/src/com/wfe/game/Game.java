package com.wfe.game;

import com.wfe.animation.AnimatedEntity;
import com.wfe.animation.Animation;
import com.wfe.animation.loaders.AnimatedEntityCreator;
import com.wfe.animation.loaders.AnimationCreator;
import com.wfe.components.ColliderComponent;
import com.wfe.components.PlayerControllerComponent;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Fern;
import com.wfe.entities.Grass;
import com.wfe.entities.Shroom;
import com.wfe.font.FontType;
import com.wfe.graph.Material;
import com.wfe.graph.OBJLoader;
import com.wfe.gui.GUIText;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;

public class Game implements IGameLogic {
	
	Camera camera;
	AnimatedEntity player;
	
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
		ResourceManager.loadTexture("cookie_ui", Texture.newTexture(new MyFile("gui/items/cookie.png")).create());
		ResourceManager.loadTexture("apple_ui", Texture.newTexture(new MyFile("gui/items/apple.png")).create());
		ResourceManager.loadTexture("wall_ui", Texture.newTexture(new MyFile("gui/items/wall.png")).create());
		ResourceManager.loadTexture("cross_wall_ui", Texture.newTexture(new MyFile("gui/items/cross_wall.png")).create());
		ResourceManager.loadTexture("window_wall_ui", Texture.newTexture(new MyFile("gui/items/window_wall.png")).create());
		ResourceManager.loadTexture("door_wall_ui", Texture.newTexture(new MyFile("gui/items/door_wall.png")).create());
		ResourceManager.loadTexture("axe_ui", Texture.newTexture(new MyFile("gui/items/axe.png")).create());
		ResourceManager.loadTexture("shroom_ui", Texture.newTexture(new MyFile("gui/items/shroom.png")).create());
		ResourceManager.loadTexture("bread_ui", Texture.newTexture(new MyFile("gui/items/bread.png")).create());
		ResourceManager.loadTexture("slot_ui", Texture.newTexture(new MyFile("gui/slot.png")).create());
		ResourceManager.loadTexture("background_frame_ui", Texture.newTexture(new MyFile("gui/background_frame.png")).create());
		ResourceManager.loadTexture("corner_frame_ui", Texture.newTexture(new MyFile("gui/corner_frame.png")).create());
		ResourceManager.loadTexture("h_edge_frame_ui", Texture.newTexture(new MyFile("gui/h_edge_frame.png")).create());
		ResourceManager.loadTexture("v_edge_frame_ui", Texture.newTexture(new MyFile("gui/v_edge_frame.png")).create());
		ResourceManager.loadTexture("health_ui", Texture.newTexture(new MyFile("gui/health.png")).create());
		
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
		
		/*** Shroom ***/
		ResourceManager.loadTexture("shroom", Texture.newTexture(new MyFile("entity/shroom/shroom.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("shroom", OBJLoader.loadMesh("/entity/shroom/shroom_1.obj"));
		/*** *** ***/
	}
	
	@Override
	public void onEnter(Display display) throws Exception {
		camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);
		World.getWorld().init();
		
		player = AnimatedEntityCreator.loadEntity(new MyFile("entity/model.dae"),
				new MyFile("entity/diffuse.png"));
		Animation animation = AnimationCreator.loadAnimation(new MyFile("entity/model.dae"));
		player.doAnimation(animation);
		player.getTransform().setPosition(80, 0, 80);
		player.getTransform().setScale(0.2f);
		player.addComponent(new PlayerControllerComponent(camera, player.getTransform()));

		FontType fontType = new FontType(ResourceManager.getTexture("myFont").getID(),
				new MyFile("font/myFont.fnt"));
		GUIText text = new GUIText("Winter Fox Engine", 1.1f, fontType, 0.875f, 0.0f, 0.125f, true);
		text.setColor(1, 1, 1);
		World.getWorld().addGUIText(text);
		
		Fern fern1 = new Fern(new Transformation(84.5f, 0, 75.5f));
		fern1.setTextureIndex(0);
		World.getWorld().addEntityToTile(fern1);
		
		Fern fern2 = new Fern(new Transformation(87.5f, 0, 83.5f));
		fern2.setTextureIndex(1);
		World.getWorld().addEntityToTile(fern2);
		
		Fern fern3 = new Fern(new Transformation(92.5f, 0, 95.5f));
		fern3.setTextureIndex(2);
		World.getWorld().addEntityToTile(fern3);
		
		Fern fern4 = new Fern(new Transformation(79.5f, 0, 82.5f));
		fern4.setTextureIndex(3);
		World.getWorld().addEntityToTile(fern4);
		
		Grass grass = new Grass(new Transformation(85, 0, 90));
		grass.setTextureIndex(0);
		World.getWorld().addEntityToTile(grass);
		
		Grass grass1 = new Grass(new Transformation(85, 0, 91));
		grass1.setTextureIndex(1);
		World.getWorld().addEntityToTile(grass1);
		
		Grass grass2 = new Grass(new Transformation(86, 0, 90));
		grass2.setTextureIndex(2);
		World.getWorld().addEntityToTile(grass2);
		
		Grass grass3 = new Grass(new Transformation(86, 0, 91));
		grass3.setTextureIndex(2);
		World.getWorld().addEntityToTile(grass3);
		
		Transformation pineTransform = new Transformation(90.5f, 0, 88.5f);
		pineTransform.setScale(0.4f);
		StaticEntity pineBark = new StaticEntity(ResourceManager.getMesh("pine_bark"),
				new Material(ResourceManager.getTexture("pine_bark")), pineTransform);
		pineBark.addComponent(new ColliderComponent(0.5f, 1, 0.5f, pineTransform));
		World.getWorld().addEntityToTile(pineBark);
		
		StaticEntity pineLeaves = new StaticEntity(ResourceManager.getMesh("pine_leaves"),
				new Material(ResourceManager.getTexture("pine_leaves")).setHasTransparency(true), pineTransform);
		World.getWorld().addEntity(pineLeaves);
		
		Shroom shroom = new Shroom(player, new Transformation(77, 0, 89));
		World.getWorld().addEntityToTile(shroom);
		
		Shroom shroom2 = new Shroom(player, new Transformation(86, 0, 99));
		World.getWorld().addEntityToTile(shroom2);
		
		Shroom shroom3 = new Shroom(player, new Transformation(97, 0, 88));
		World.getWorld().addEntityToTile(shroom3);
		
		Shroom shroom4 = new Shroom(player, new Transformation(86, 0, 78));
		World.getWorld().addEntityToTile(shroom4);
	}
	
	@Override
	public void update(float dt) {		
		World.getWorld().update(dt, player);
		player.update(dt);
	}

	@Override
	public void render() {
		World.getWorld().render(player);
	}

	@Override
	public void onExit() {
		World.getWorld().cleanup();
	}

}
