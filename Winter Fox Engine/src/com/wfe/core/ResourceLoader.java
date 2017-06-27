package com.wfe.core;

import com.wfe.audio.AudioMaster;
import com.wfe.graph.OBJLoader;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;

public class ResourceLoader {

	private static boolean loaded = false;

	private ResourceLoader() {}
	
	public static void load() {
		if(loaded)
			return;
		
		/*** Audio Initialization ***/
		ResourceManager.loadSound("hoe", AudioMaster.loadSound("audio/hoe.wav"));
		ResourceManager.loadSound("chop", AudioMaster.loadSound("audio/chop.wav"));
		ResourceManager.loadSound("mine", AudioMaster.loadSound("audio/mine.wav"));
		ResourceManager.loadSound("eating", AudioMaster.loadSound("audio/eat.wav"));
		ResourceManager.loadSound("tick", AudioMaster.loadSound("audio/tick.wav"));
		ResourceManager.loadSound("taking", AudioMaster.loadSound("audio/take.wav"));
		ResourceManager.loadSound("equip", AudioMaster.loadSound("audio/equip.wav"));
		ResourceManager.loadSound("inventory", AudioMaster.loadSound("audio/inventory.wav"));
		ResourceManager.loadSound("footstep1", AudioMaster.loadSound("audio/footstep1.wav"));
		ResourceManager.loadSound("footstep2", AudioMaster.loadSound("audio/footstep2.wav"));
		ResourceManager.loadSound("swing1", AudioMaster.loadSound("audio/swing1.wav"));
		ResourceManager.loadSound("swing2", AudioMaster.loadSound("audio/swing2.wav"));
		ResourceManager.loadSound("swing3", AudioMaster.loadSound("audio/swing3.wav"));
		
		ResourceManager.loadSound("hills", AudioMaster.loadSound("audio/hills.wav"));
		/*** *** ***/
		
		/*** Font ***/
		ResourceManager.loadTexture("primitiveFont", Texture.newTexture(new MyFile("font/primitiveFont.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("myFont", Texture.newTexture(new MyFile("font/myFont.png"))
				.normalMipMap().create());
		/*** *** ***/
		
		/*** GUI Elements ***/	
		ResourceManager.loadTexture("quest_ui", Texture.newTexture(new MyFile("gui/quest.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("mancala_ui", Texture.newTexture(new MyFile("gui/mancala.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("gem_0_ui", Texture.newTexture(new MyFile("gui/gem_0.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("gem_1_ui", Texture.newTexture(new MyFile("gui/gem_1.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("gem_2_ui", Texture.newTexture(new MyFile("gui/gem_2.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("gem_3_ui", Texture.newTexture(new MyFile("gui/gem_3.png"))
				.normalMipMap().create());
	
		ResourceManager.loadTexture("hunger_icon_ui", Texture.newTexture(new MyFile("gui/hunger_icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("health_icon_ui", Texture.newTexture(new MyFile("gui/health_icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("thirst_icon_ui", Texture.newTexture(new MyFile("gui/thirst_icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("cowry_ui", Texture.newTexture(new MyFile("gui/cowry.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("selected_slot_ui", Texture.newTexture(new MyFile("gui/elements/selected_slot.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("selected_slot_ui", Texture.newTexture(new MyFile("gui/elements/selected_slot.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("background_frame_ui", Texture.newTexture(new MyFile("gui/background_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("corner_frame_ui", Texture.newTexture(new MyFile("gui/corner_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("h_edge_frame_ui", Texture.newTexture(new MyFile("gui/h_edge_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("v_edge_frame_ui", Texture.newTexture(new MyFile("gui/v_edge_frame.png"))
				.normalMipMap().create());
		
		ResourceManager.loadTexture("popUp_background_frame_ui", Texture.newTexture(new MyFile("gui/popUp_background_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("popUp_corner_frame_ui", Texture.newTexture(new MyFile("gui/popUp_corner_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("popUp_h_edge_frame_ui", Texture.newTexture(new MyFile("gui/popUp_h_edge_frame.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("popUp_v_edge_frame_ui", Texture.newTexture(new MyFile("gui/popUp_v_edge_frame.png"))
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
		
		/*** Amanita ***/
		ResourceManager.loadTexture("amanita", Texture.newTexture(new MyFile("entity/amanita/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("amanita", OBJLoader.loadMesh("/entity/amanita/model.obj"));
		/*** *** ***/
		
		/*** Enemy ***/
		ResourceManager.loadTexture("enemy", Texture.newTexture(new MyFile("entity/enemy/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("enemy", OBJLoader.loadMesh("/entity/enemy/model.obj"));
		/*** *** ***/
		
		/*** Campfire ***/
		ResourceManager.loadTexture("campfire", Texture.newTexture(new MyFile("entity/campfire/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("campfire", OBJLoader.loadMesh("/entity/campfire/model.obj"));
		/*** *** ***/
		
		/*** Barrel ***/
		ResourceManager.loadTexture("barrel", Texture.newTexture(new MyFile("entity/barrel/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("barrel", OBJLoader.loadMesh("/entity/barrel/model.obj"));
		/*** *** ***/
		
		/*** Hive ***/
		ResourceManager.loadTexture("hive", Texture.newTexture(new MyFile("entity/hive/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("hive", OBJLoader.loadMesh("/entity/hive/model.obj"));
		/*** *** ***/
		
		/*** Bush ***/
		ResourceManager.loadTexture("bush", Texture.newTexture(new MyFile("entity/bush/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("bush", OBJLoader.loadMesh("/entity/bush/model.obj"));
		/*** *** ***/
		
		/*** Fern ***/
		ResourceManager.loadTexture("fern", Texture.newTexture(new MyFile("entity/fern/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("fern", OBJLoader.loadMesh("/entity/fern/model.obj"));
		/*** *** ***/
		
		/*** Bed ***/
		ResourceManager.loadTexture("bed_ui", Texture.newTexture(new MyFile("entity/bed/icon.png"))
				.normalMipMap().create());
		ResourceManager.loadTexture("bed", Texture.newTexture(new MyFile("entity/bed/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("bed", OBJLoader.loadMesh("/entity/bed/model.obj"));
		/*** *** ***/
		
		/*** Walls ***/
		ResourceManager.loadTexture("clay_wall", Texture.newTexture(new MyFile("entity/clay_wall/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadTexture("clay_corner_wall_ui", Texture.newTexture(new MyFile("entity/clay_wall/corner_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("clay_corner_wall", OBJLoader.loadMesh("/entity/clay_wall/corner_wall.obj"));
		
		ResourceManager.loadTexture("clay_wall_ui", Texture.newTexture(new MyFile("entity/clay_wall/wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("clay_wall", OBJLoader.loadMesh("/entity/clay_wall/wall.obj"));
		
		
		ResourceManager.loadTexture("walls", Texture.newTexture(new MyFile("entity/log_wall/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadTexture("cross_wall_ui", Texture.newTexture(new MyFile("entity/log_wall/cross_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("cross_wall", OBJLoader.loadMesh("/entity/log_wall/cross_wall.obj"));
		
		ResourceManager.loadTexture("door_wall_ui", Texture.newTexture(new MyFile("entity/log_wall/door_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("door_wall", OBJLoader.loadMesh("/entity/log_wall/door_wall.obj"));
		
		ResourceManager.loadTexture("window_wall_ui", Texture.newTexture(new MyFile("entity/log_wall/window_wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("window_wall", OBJLoader.loadMesh("/entity/log_wall/window_wall.obj"));
		
		ResourceManager.loadTexture("wall_ui", Texture.newTexture(new MyFile("entity/log_wall/wall.png"))
				.normalMipMap().create());
		ResourceManager.loadMesh("wall", OBJLoader.loadMesh("/entity/log_wall/wall.obj"));
		/*** *** ***/
		
		/*** Rock ***/
		ResourceManager.loadTexture("rock", Texture.newTexture(new MyFile("entity/rock/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadMesh("rock", OBJLoader.loadMesh("/entity/rock/model.obj"));
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
		ResourceManager.loadTexture("wheat_seeds_ui", Texture.newTexture(new MyFile("gui/items/wheat_seeds.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("wheat_ui", Texture.newTexture(new MyFile("entity/wheat/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("wheat_stage_5", Texture.newTexture(new MyFile("entity/wheat/wheat_stage_7.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("wheat_stage_4", Texture.newTexture(new MyFile("entity/wheat/wheat_stage_6.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("wheat_stage_3", Texture.newTexture(new MyFile("entity/wheat/wheat_stage_5.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("wheat_stage_2", Texture.newTexture(new MyFile("entity/wheat/wheat_stage_4.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("wheat_stage_1", Texture.newTexture(new MyFile("entity/wheat/wheat_stage_3.png"))
				.normalMipMap(-0.4f)
				.create());
		ResourceManager.loadTexture("wheat_stage_0", Texture.newTexture(new MyFile("entity/wheat/wheat_stage_2.png"))
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
		
		/*** Clay ***/
		ResourceManager.loadTexture("clay_ui", Texture.newTexture(new MyFile("entity/clay/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("clay", Texture.newTexture(new MyFile("entity/clay/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		/*** *** ***/
		
		/*** Stick ***/
		ResourceManager.loadTexture("jug_ui", Texture.newTexture(new MyFile("entity/jug/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("jug", Texture.newTexture(new MyFile("entity/jug/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("jug", OBJLoader.loadMesh("/entity/jug/model.obj"));
		/*** *** ***/
		
		/*** Basket ***/
		ResourceManager.loadTexture("basket_ui", Texture.newTexture(new MyFile("entity/basket/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("basket", Texture.newTexture(new MyFile("entity/basket/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("basket", OBJLoader.loadMesh("/entity/basket/model.obj"));
		/*** *** ***/
		
		/*** Furnace ***/
		ResourceManager.loadTexture("furnace_ui", Texture.newTexture(new MyFile("entity/furnace/icon.png"))
				.normalMipMap()
				.create());
		ResourceManager.loadTexture("furnace", Texture.newTexture(new MyFile("entity/furnace/diffuse.png"))
				.normalMipMap(-0.4f)
				.create());
		
		ResourceManager.loadMesh("furnace", OBJLoader.loadMesh("/entity/furnace/model.obj"));
		/*** *** ***/
		
		loaded = true;
	}
	
}
