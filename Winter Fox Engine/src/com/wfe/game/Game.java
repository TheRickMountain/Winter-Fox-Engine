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
		ResourceManager.loadTexture("log_wall_ui", Texture.newTexture(new MyFile("gui/items/log wall.png")).create());
		ResourceManager.loadTexture("axe_ui", Texture.newTexture(new MyFile("gui/items/axe.png")).create());
		ResourceManager.loadTexture("shroom_ui", Texture.newTexture(new MyFile("gui/items/shroom.png")).create());
		ResourceManager.loadTexture("bread_ui", Texture.newTexture(new MyFile("gui/items/bread.png")).create());
		ResourceManager.loadTexture("slot_ui", Texture.newTexture(new MyFile("gui/slot.png")).create());
		ResourceManager.loadTexture("background_frame_ui", Texture.newTexture(new MyFile("gui/background_frame.png")).create());
		ResourceManager.loadTexture("corner_frame_ui", Texture.newTexture(new MyFile("gui/corner_frame.png")).create());
		ResourceManager.loadTexture("h_edge_frame_ui", Texture.newTexture(new MyFile("gui/h_edge_frame.png")).create());
		ResourceManager.loadTexture("v_edge_frame_ui", Texture.newTexture(new MyFile("gui/v_edge_frame.png")).create());
		
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
		GUIText text = new GUIText("Winter Font Engine", 1.1f, fontType, 0.875f, 0.0f, 0.125f, true);
		text.setColor(1, 1, 1);
		World.getWorld().addGUIText(text);
		
		Fern fern1 = new Fern(new Transformation(84.5f, 0, 84.5f));
		fern1.setTextureIndex(0);
		World.getWorld().addToTile(fern1);
		
		Fern fern2 = new Fern(new Transformation(83.5f, 0, 84.5f));
		fern2.setTextureIndex(1);
		World.getWorld().addToTile(fern2);
		
		Fern fern3 = new Fern(new Transformation(81.5f, 0, 84.5f));
		fern3.setTextureIndex(2);
		World.getWorld().addToTile(fern3);
		
		Fern fern4 = new Fern(new Transformation(80.5f, 0, 84.5f));
		fern4.setTextureIndex(3);
		World.getWorld().addToTile(fern4);
		
		Grass grass = new Grass(new Transformation(85.5f, 0, 85.5f));
		grass.setTextureIndex(0);
		World.getWorld().addToTile(grass);
		
		Grass grass1 = new Grass(new Transformation(85.5f, 0, 86.5f));
		grass1.setTextureIndex(1);
		World.getWorld().addToTile(grass1);
		
		Grass grass2 = new Grass(new Transformation(85.5f, 0, 87.5f));
		grass2.setTextureIndex(2);
		World.getWorld().addToTile(grass2);
		
		Transformation pineTransform = new Transformation(90.5f, 0, 88.5f);
		pineTransform.setScale(0.4f);
		StaticEntity pineBark = new StaticEntity(ResourceManager.getMesh("pine_bark"),
				new Material(ResourceManager.getTexture("pine_bark")), pineTransform);
		pineBark.addComponent(new ColliderComponent(0.5f, 1, 0.5f, pineTransform));
		World.getWorld().addToTile(pineBark);
		
		StaticEntity pineLeaves = new StaticEntity(ResourceManager.getMesh("pine_leaves"),
				new Material(ResourceManager.getTexture("pine_leaves")).setHasTransparency(true), pineTransform);
		World.getWorld().addEntity(pineLeaves);
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
