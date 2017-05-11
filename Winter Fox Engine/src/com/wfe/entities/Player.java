package com.wfe.entities;

import com.wfe.components.PlayerAnimation;
import com.wfe.components.PlayerControllerComponent;
import com.wfe.core.Camera;
import com.wfe.core.ResourceManager;
import com.wfe.core.World;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Player extends Entity {

	public PlayerControllerComponent playerController;
	
	public Entity leftArm;
	public Entity rightArm;
	
	public Entity leftForearm;
	public Entity rightForearm;
	
	public Entity rightHip;
	public Entity leftHip;
	
	public Entity rightShin;
	public Entity leftShin;
	
	public Player(Camera camera, Transformation transform) {
		super(null, null, transform);
		setTag("player");
		transform.setScale(0.3f);
		
		Entity body = new Entity(ResourceManager.getMesh("body"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		addChild(body);
		
		Entity head = new Entity(ResourceManager.getMesh("head"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		head.getTransform().localY = 0.85f;
		body.addChild(head);
		
		Entity eyes = new Entity(ResourceManager.getMesh("eyes"),
				new Material(ResourceManager.getTexture("eyes")), new Transformation());
		eyes.getTransform().localY = -1.0f;
		head.addChild(eyes);
		
		leftArm = new Entity(ResourceManager.getMesh("leftArm"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		leftArm.getTransform().localX = 0.65f;
		leftArm.getTransform().localY = 0.6f;
		body.addChild(leftArm);
		
		{
			leftForearm = new Entity(ResourceManager.getMesh("leftForearm"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			leftForearm.getTransform().localX = 1.08f;
			leftForearm.getTransform().localY = -0.7f;
			leftArm.addChild(leftForearm);
		}
		
		rightArm = new Entity(ResourceManager.getMesh("rightArm"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		rightArm.getTransform().localX = -0.65f;
		rightArm.getTransform().localY = 0.6f;
		body.addChild(rightArm);	
			
		{
			rightForearm = new Entity(ResourceManager.getMesh("rightForearm"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			rightForearm.getTransform().localX = -1.08f;
			rightForearm.getTransform().localY = -0.7f;
			rightArm.addChild(rightForearm);
		}
		
		leftHip = new Entity(ResourceManager.getMesh("hip"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		leftHip.getTransform().localX = 0.4f;
		leftHip.getTransform().localY = -0.45f;
		body.addChild(leftHip);
		
		{
			leftShin = new Entity(ResourceManager.getMesh("shin"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			leftShin.getTransform().localY = -0.8f;
			leftHip.addChild(leftShin);
		}
		
		rightHip = new Entity(ResourceManager.getMesh("hip"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		rightHip.getTransform().localX = -0.4f;
		rightHip.getTransform().localY = -0.45f;
		body.addChild(rightHip);
		
		{
			rightShin = new Entity(ResourceManager.getMesh("shin"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			rightShin.getTransform().localY = -0.8f;
			rightHip.addChild(rightShin);
		}
		
		playerController = new PlayerControllerComponent(camera, transform, 
				new PlayerAnimation(this), rightForearm);
		addComponent(playerController);
	}

}
