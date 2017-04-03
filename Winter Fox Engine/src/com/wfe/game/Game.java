package com.wfe.game;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.IGameLogic;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Fern;
import com.wfe.entities.Flint;
import com.wfe.entities.Grass;
import com.wfe.entities.Mushroom;
import com.wfe.entities.Pine;
import com.wfe.entities.Rock;
import com.wfe.entities.Settler;
import com.wfe.entities.Stick;
import com.wfe.entities.Wheat;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;
import com.wfe.graph.OBJLoader;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;
import com.wfe.utils.MyRandom;

public class Game implements IGameLogic {
	
	private World world;
	
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
		ResourceManager.loadSound("swing1", AudioMaster.loadSound("audio/swing1.wav"));
		ResourceManager.loadSound("swing2", AudioMaster.loadSound("audio/swing2.wav"));
		ResourceManager.loadSound("swing3", AudioMaster.loadSound("audio/swing3.wav"));
		
		ResourceManager.loadSound("hills", AudioMaster.loadSound("audio/hills.wav"));
		/*** *** ***/
		
		/*** Plane ***/
		float[] vertices = {
				-0.5f, 0.0f, -0.5f, 
				-0.5f, 0.0f, 0.5f,
				0.5f, 0.0f, 0.5f,
				0.5f, 0.0f, -0.5f
		};

		float[] textureCoords = {
				0.0f, 0.0f,
				0.0f, 0.0f,
				0.0f, 0.0f,
				0.0f, 0.0f
		};

		float[] normals = {
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f
		};

		int[] indices = {
				0, 1, 2,
				0, 2, 3
		};
		ResourceManager.loadMesh("plane", new Mesh(vertices, textureCoords, normals, indices));
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
		ResourceManager.loadTexture("selected_slot_ui", Texture.newTexture(new MyFile("gui/elements/selected_slot.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("cross_ui", Texture.newTexture(new MyFile("gui/elements/cross.png"))
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
		
		ResourceManager.loadTexture("waterskin_ui", Texture.newTexture(new MyFile("gui/items/waterskin.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("leather_ui", Texture.newTexture(new MyFile("gui/items/leather.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("fiber_ui", Texture.newTexture(new MyFile("gui/items/fiber.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("log_ui", Texture.newTexture(new MyFile("gui/items/log.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("rope_ui", Texture.newTexture(new MyFile("gui/items/rope.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("lavender_ui", Texture.newTexture(new MyFile("gui/items/lavender.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("honey_ui", Texture.newTexture(new MyFile("gui/items/honey.png"))
				.normalMipMap().create());
		/* * */
		
		/*** *** ***/
		
		/*** Hive ***/
		ResourceManager.loadTexture("hive", Texture.newTexture(new MyFile("entity/hive/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("hive", OBJLoader.loadMesh("/entity/hive/model.obj"));
		/*** *** ***/
		
		/*** Stone ***/
		ResourceManager.loadTexture("stone", Texture.newTexture(new MyFile("entity/stone/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("stone", OBJLoader.loadMesh("/entity/stone/model.obj"));
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
		
		/*** Firewood ***/
		ResourceManager.loadTexture("firewood", Texture.newTexture(new MyFile("entity/firewood/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("firewood", OBJLoader.loadMesh("/entity/firewood/model.obj"));
		/*** *** ***/
		
		/*** Goat ***/
		ResourceManager.loadTexture("goat", Texture.newTexture(new MyFile("entity/goat/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("goat", OBJLoader.loadMesh("/entity/goat/model.obj"));
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
		
		/*** Club ***/
		ResourceManager.loadTexture("club_ui", Texture.newTexture(new MyFile("entity/club/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("club", Texture.newTexture(new MyFile("entity/club/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("club", OBJLoader.loadMesh("/entity/club/model.obj"));
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
		
		/*** Sharp Flint ***/
		ResourceManager.loadTexture("sharp_flint_ui", Texture.newTexture(new MyFile("entity/sharpFlint/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("sharp_flint", Texture.newTexture(new MyFile("entity/sharpFlint/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("sharp_flint", OBJLoader.loadMesh("/entity/sharpFlint/model.obj"));
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
		
		/*** Well ***/
		ResourceManager.loadTexture("well_ui", Texture.newTexture(new MyFile("entity/well/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("well", Texture.newTexture(new MyFile("entity/well/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("well", OBJLoader.loadMesh("/entity/well/model.obj"));
		/*** *** ***/
		
		/*** Wheat ***/
		ResourceManager.loadTexture("wheat_ui", Texture.newTexture(new MyFile("entity/wheat/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("four", Texture.newTexture(new MyFile("entity/wheat/lavender.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("two", Texture.newTexture(new MyFile("entity/wheat/two.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("one", Texture.newTexture(new MyFile("entity/wheat/one.png"))
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
		Camera camera = new Camera(new Vector3f(80, 0, 80));	
		
		world = World.createWorld(camera);
		world.init();
		
		world.addEntityToTile(new Wheat(new Transformation(83.5f, 0, 83.5f)));
		world.addEntityToTile(new Wheat(new Transformation(90.5f, 0, 85.5f)));
		world.addEntityToTile(new Wheat(new Transformation(72.5f, 0, 94.5f)));
		world.addEntityToTile(new Wheat(new Transformation(80.5f, 0, 75.5f)));
		
		for(int i = 0; i < 100; i++) {		
			Mushroom shroom = new Mushroom(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			world.addEntityToTile(shroom);
			
			Fern fern = new Fern(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			fern.setTextureIndex(MyRandom.nextInt(4));
			world.addEntityToTile(fern);
		
			world.addEntityToTile(new Flint(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
			
			world.addEntityToTile(new Stick(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
			
			world.addEntityToTile(new Pine(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
			
			world.addEntityToTile(new Rock(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
		}
		
		for(int i = 0; i < 160; i++) {
			for(int j = 0; j < 160; j++) {
				int num = MyRandom.nextInt(10);
				if(num == 5) {
					Grass grass = new Grass(new Transformation(i, 0, j));
					grass.setTextureIndex(MyRandom.nextInt(3, 7));		
					world.addEntityToTile(grass);
				}
			}
		}
		
		world.addEntity(new Settler(world.getTile(85, 85), new Transformation(85.5f, 0.65f, 85.5f)));
		world.addEntity(new Settler(world.getTile(87, 85), new Transformation(87.5f, 0.65f, 85.5f)));
		
		AudioMaster.ambientSource.play(ResourceManager.getSound("hills"));
	}
	
	@Override
	public void update(float dt) {	
		world.update(dt);
	}

	@Override
	public void render() {
		world.render();
	}

	@Override
	public void onExit() {
		world.cleanup();
		AudioMaster.cleanup();
	}

}
