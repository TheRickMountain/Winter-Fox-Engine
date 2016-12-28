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
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.font.FontType;
import com.wfe.font.GUIText;
import com.wfe.graph.Material;
import com.wfe.graph.OBJLoader;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.textures.TextureBuilder;
import com.wfe.utils.MyFile;

public class Game implements IGameLogic {
	
	Camera camera;
	AnimatedEntity animatedEntity;
	
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
		
		/*** Meshes ***/
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/models/wall.obj"));
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/models/cross_wall.obj"));
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/models/door_wall.obj"));
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/models/window_wall.obj"));
		ResourceManager.loadMesh("fern", OBJLoader.loadMesh("/entity/fern/fern.obj"));
	}
	
	@Override
	public void onEnter(Display display) throws Exception {
		camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);
		
		animatedEntity = AnimatedEntityCreator.loadEntity(new MyFile("entity/model.dae"),
				new MyFile("entity/diffuse.png"));
		Animation animation = AnimationCreator.loadAnimation(new MyFile("entity/model.dae"));
		animatedEntity.doAnimation(animation);
		animatedEntity.getTransform().setPosition(16.5f, 0, 16.5f);
		animatedEntity.getTransform().setScale(0.2f);
		animatedEntity.addComponent(new PlayerControllerComponent(camera, animatedEntity.getTransform()));

		FontType fontType = new FontType(ResourceManager.getTexture("myFont").getID(),
				new MyFile("font/myFont.fnt"));
		GUIText text = new GUIText("Winter Font Engine", 1.1f, fontType, 0.0f, 0.0f, 0.125f, true);
		text.setColor(1, 1, 1);
		World.getWorld().addText(text);
		
		Material fernMaterial = new Material(ResourceManager.getTexture("fern"));
		fernMaterial.setNumberOfRows(2);
		fernMaterial.setHasTransparency(true);
		StaticEntity fern = new StaticEntity(ResourceManager.getMesh("fern"), 
				fernMaterial, new Transformation(14, 0, 14));
		fern.setTextureIndex(0);
		fern.getTransform().setScale(0.6f);
		World.getWorld().addEntity(fern);
	}

	float scale = 0;
	boolean state = false;
	
	@Override
	public void update(float dt) {		
		World.getWorld().update(dt);
		animatedEntity.update(dt);
	}

	@Override
	public void render() {
		World.getWorld().render(animatedEntity);
	}

	@Override
	public void onExit() {
		World.getWorld().cleanup();
	}

}
