package com.wfe.game;

import com.wfe.animation.AnimatedEntity;
import com.wfe.animation.Animation;
import com.wfe.components.PlayerControllerComponent;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
import com.wfe.graph.OBJLoader;
import com.wfe.loader.AnimatedEntityCreator;
import com.wfe.loader.AnimationCreator;
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
		TextureBuilder texBuilder = Texture.newTexture(new MyFile("textures/player.png"));
		texBuilder.normalMipMap();
		ResourceManager.loadTexture("player", texBuilder.create());
		
		texBuilder = Texture.newTexture(new MyFile("textures/wall.png"));
		texBuilder.normalMipMap();
		ResourceManager.loadTexture("wall", texBuilder.create());
		
		/*** Meshes ***/
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/models/wall.obj"));
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/models/cross_wall.obj"));
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/models/door_wall.obj"));
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/models/window_wall.obj"));
	}
	
	@Override
	public void onEnter(Display display) throws Exception {
		camera = new Camera(new Vector3f(16, 0, 16));	
		
		World.createWorld(camera);

		/*StaticEntity player = new StaticEntity(OBJLoader.loadMesh("/models/box.obj"), 
				new Material(ResourceManager.getTexture("player")), new Transformation(16.5f, 0, 16.5f));
		player.getTransform().setScale(0.4f, 0.9f, 0.4f);
		player.addComponent(new PlayerControllerComponent(camera, player.getTransform()));
		World.getWorld().addEntity(player);*/
		
		animatedEntity = AnimatedEntityCreator.loadEntity(new MyFile("entity/model.dae"),
				new MyFile("entity/diffuse.png"));
		Animation animation = AnimationCreator.loadAnimation(new MyFile("entity/model.dae"));
		animatedEntity.doAnimation(animation);
		animatedEntity.getTransform().setPosition(16.5f, 0, 16.5f);
		animatedEntity.getTransform().setScale(0.25f);
		animatedEntity.addComponent(new PlayerControllerComponent(camera, animatedEntity.getTransform()));
	}

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
		World.getWorld().clearWorld();
	}

}
