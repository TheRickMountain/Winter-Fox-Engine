package com.wfe.ecs;

import java.util.HashMap;
import java.util.Map;

import com.wfe.entities.Amanita;
import com.wfe.entities.Barrel;
import com.wfe.entities.Basket;
import com.wfe.entities.Bush;
import com.wfe.entities.Campfire;
import com.wfe.entities.Clay;
import com.wfe.entities.ClayCornerWall;
import com.wfe.entities.ClayWall;
import com.wfe.entities.CrossWall;
import com.wfe.entities.DoorWall;
import com.wfe.entities.Fern;
import com.wfe.entities.Flint;
import com.wfe.entities.Furnace;
import com.wfe.entities.Goat;
import com.wfe.entities.Grass;
import com.wfe.entities.Jug;
import com.wfe.entities.Mushroom;
import com.wfe.entities.Pine;
import com.wfe.entities.Rock;
import com.wfe.entities.Stick;
import com.wfe.entities.Wall;
import com.wfe.entities.Well;
import com.wfe.entities.Wheat;
import com.wfe.entities.WindowWall;

public class EntityCache {
	
	public static Map<Integer, Entity> entities = new HashMap<>();	
	
	public static void init() {
		entities.put(1, new Amanita(new Transformation()));
		//entities.put(2, new Axe());
		entities.put(3, new Barrel(new Transformation()));
		entities.put(4, new Basket());
		entities.put(5, new Bush(new Transformation()));
		entities.put(6, new Campfire(new Transformation()));
		entities.put(7, new Clay(new Transformation()));
		entities.put(8, new ClayCornerWall());
		entities.put(9, new ClayWall());
		//entities.put(10, new Club());
		entities.put(11, new CrossWall());
		entities.put(12, new DoorWall());
		entities.put(13, new Fern(new Transformation()));
		entities.put(14, new Flint(new Transformation()));
		entities.put(15, new Furnace());
		//entities.put(16, new Goat(new Transformation()));
		entities.put(17, new Grass(new Transformation()));
		//entities.put(18, new Hoe());
		entities.put(19, new Jug());
		entities.put(20, new Mushroom(new Transformation()));
		//entities.put(21, new Pickaxe());
		entities.put(22, new Pine(new Transformation()));
		//entities.put(23, new Player());
		entities.put(24, new Rock(new Transformation()));
		entities.put(25, new Stick(new Transformation()));
		entities.put(26, new Wall());
		entities.put(27, new Well());
		entities.put(28, new Wheat(new Transformation(), 5));
		entities.put(29, new WindowWall());
	}
	
	public static Entity getEntityById(int id) {
		return entities.get(id).getInstance();
	}

}
