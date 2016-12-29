package com.wfe.game;

import com.wfe.animation.AnimatedEntity;
import com.wfe.animation.Animation;
import com.wfe.animation.loaders.AnimatedEntityCreator;
import com.wfe.animation.loaders.AnimationCreator;
import com.wfe.components.PlayerControllerComponent;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Fern;
import com.wfe.entities.Grass;
import com.wfe.font.FontType;
import com.wfe.graph.OBJLoader;
import com.wfe.gui.GUIFrame;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.textures.TextureBuilder;
import com.wfe.utils.MyFile;

public class Game implements IGameLogic {
	
	Camera camera;
	AnimatedEntity player;
	
	@Override
	public void loadResources() throws Exception {
		/*** Textures ***/
		TextureBuilder texBuilder = Texture.newTexture(new MyFile("textures/wall.png"));
		texBuilder.normalMipMap(-0.4f);
		ResourceManager.loadTexture("wall", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("font/myFont.png"));
		texBuilder.normalMipMap();
		ResourceManager.loadTexture("myFont", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("entity/fern/fern.png"));
		texBuilder.normalMipMap(-0.4f);
		ResourceManager.loadTexture("fern", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("entity/grass/grass.png"));
		texBuilder.normalMipMap(-0.4f);
		ResourceManager.loadTexture("grass", texBuilder.create());
			
		texBuilder = Texture.newTexture(new MyFile("gui/banana.png"));
		ResourceManager.loadTexture("banana_ui", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("gui/background_frame.png"));
		ResourceManager.loadTexture("background_frame_ui", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("gui/corner_frame.png"));
		ResourceManager.loadTexture("corner_frame_ui", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("gui/h_edge_frame.png"));
		ResourceManager.loadTexture("h_edge_frame_ui", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("gui/v_edge_frame.png"));
		ResourceManager.loadTexture("v_edge_frame_ui", texBuilder.create());
		
		/*** Meshes ***/
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/models/wall.obj"));
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/models/cross_wall.obj"));
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/models/door_wall.obj"));
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/models/window_wall.obj"));
		ResourceManager.loadMesh("fern", OBJLoader.loadMesh("/entity/fern/fern.obj"));
		ResourceManager.loadMesh("grass", OBJLoader.loadMesh("/entity/grass/grass.obj"));
	}
	
	@Override
	public void onEnter(Display display) throws Exception {
		camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);
		
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
		World.getWorld().addEntity(fern1);
		
		Fern fern2 = new Fern(new Transformation(83.5f, 0, 84.5f));
		fern2.setTextureIndex(1);
		World.getWorld().addEntity(fern2);
		
		Fern fern3 = new Fern(new Transformation(81.5f, 0, 84.5f));
		fern3.setTextureIndex(2);
		World.getWorld().addEntity(fern3);
		
		Fern fern4 = new Fern(new Transformation(80.5f, 0, 84.5f));
		fern4.setTextureIndex(3);
		World.getWorld().addEntity(fern4);
		
		Grass grass = new Grass(new Transformation(85.5f, 0, 85.5f));
		grass.setTextureIndex(0);
		World.getWorld().addEntity(grass);
		
		Grass grass1 = new Grass(new Transformation(85.5f, 0, 86.5f));
		grass1.setTextureIndex(1);
		World.getWorld().addEntity(grass1);
		
		Grass grass2 = new Grass(new Transformation(85.5f, 0, 87.5f));
		grass2.setTextureIndex(2);
		World.getWorld().addEntity(grass2);
		
		GUIFrame frame = new GUIFrame(Display.getWidth() / 2 - 250, Display.getHeight() - 50, 
				500, 50);
		for(int i = 0; i < frame.getFrameTextures().size(); i++) {
			World.getWorld().addGUITexture(frame.getFrameTextures().get(i));
		}
		
		GUITexture bananaUI = new GUITexture(ResourceManager.getTexture("banana_ui"),
				Display.getWidth() / 2 - 250, Display.getHeight() - 50, 0, 50, 50, false);
		World.getWorld().addGUITexture(bananaUI);
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
