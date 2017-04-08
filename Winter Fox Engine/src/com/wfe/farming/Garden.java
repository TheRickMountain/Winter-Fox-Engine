package com.wfe.farming;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;
import com.wfe.core.World;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Wheat;
import com.wfe.jobSystem.Job;
import com.wfe.jobSystem.JobType;
import com.wfe.terrain.Tile;
import com.wfe.utils.TimeUtil;

public class Garden {
	
	private World world;
	
	private List<Tile> tiles = new ArrayList<>();
	private TimeUtil timer;
	
	private int finalStage;
	private int stage;
	
	private boolean sowing;
	
	private Wheat wheat;
	
	public Garden(World world, List<Tile> tiles, int finalStage) {
		this.world = world;
		this.tiles.addAll(tiles);
		this.finalStage = finalStage;
		
		timer = new TimeUtil();
		
		stage = 0;
		sowing = false;
		wheat = new Wheat(new Transformation());
	}
	
	public void update() {
		if(isPlowed()) {
			if(!sowing) {
				for(Tile tile : tiles) {
					world.getJobList().add(new Job(JobType.SOWING, tile, 1.5f, wheat, null, 0));
				}
				sowing = true;
			}
		}
		
		if(isSowed()) {
			if(stage != finalStage) {
				if(timer.getTime() >= 5) {
					stage++;
					for(Tile tile : tiles) {
						tile.getEntity().getMaterial().setTexture(ResourceManager.getTexture("wheat_stage_" + stage));
					}
					timer.reset();
				}
			}
		}
	}
	
	private boolean isPlowed() {
		for(Tile tile : tiles) {
			if(tile.getId() != 11) return false;
		}
		
		return true;
	}
	
	private boolean isSowed() {
		for(Tile tile : tiles) {
			if(!tile.isHasEntity()) return false; 
		}
		
		return true;
	}

}
