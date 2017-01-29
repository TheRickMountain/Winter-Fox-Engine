package com.wfe.entities;

import com.wfe.components.PlayerComponent;
import com.wfe.core.Camera;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.graph.Material;
import com.wfe.gui.inventory.GUI;

public class Player extends Entity {

	private Entity weapon;
	
	public Player(Camera camera, Transformation transform) {
		super(null, 
				null, transform);
		transform.setScale(0.3f);
		
		Entity body = new Entity(ResourceManager.getMesh("body"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		addChild(body);
		World.getWorld().addEntity(body);
		
		Entity head = new Entity(ResourceManager.getMesh("head"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		head.getTransform().localY = 1.15f;
		body.addChild(head);
		World.getWorld().addEntity(head);
		
		Entity eyes = new Entity(ResourceManager.getMesh("eyes"),
				new Material(ResourceManager.getTexture("eyes")), new Transformation());
		eyes.getTransform().localY = -1f;
		head.addChild(eyes);
		World.getWorld().addEntity(eyes);
		
		addComponent(new PlayerComponent(camera, transform));
	}

	public void addTool(Entity weapon) {
		this.weapon = weapon;
		
		weapon.getTransform().localScaleX = 1.75f;
		weapon.getTransform().localScaleY = 1.75f;
		weapon.getTransform().localScaleZ = 1.75f;
		weapon.getTransform().localRotY = 180;
		weapon.getTransform().localX = -1.1f;
		weapon.getTransform().localY = 0.5f;
		addChild(weapon);
		World.getWorld().addEntity(weapon);
	}
	
	public void removeTool() {
		removeChild(weapon);
		World.getWorld().removeEntity(weapon);
	}
	
	private boolean down = false;
	private float animationSpeed = 300;
	
	public void chopingAnimation(float dt) {
		if(weapon != null) {
			Transformation transform = weapon.getTransform();
			if(transform.localRotX >= 80)
				down = true;
			else if(transform.localRotX <= -10) {
				down = false;
				GUI.soundSource.play(ResourceManager.getSound("chopping"));
			}
			
			if(down) {
				transform.localRotX -= animationSpeed * dt;
			} else {
				transform.localRotX += animationSpeed * dt;
			}
		}
	}
	
	public void idleAnim() {
		if(weapon != null)
			weapon.getTransform().localRotX = 0;
	}

}
