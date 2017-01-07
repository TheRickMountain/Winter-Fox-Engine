package com.wfe.entities;

import com.wfe.components.PlayerAnimationComponent;
import com.wfe.components.PlayerControllerComponent;
import com.wfe.core.Camera;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.graph.Material;

public class Player extends StaticEntity {
	
	public final StaticEntity rightArm;
	public final StaticEntity leftArm;
	public final StaticEntity rightHip;
	public final StaticEntity leftHip;
	public final StaticEntity leftForearm;
	public final StaticEntity rightForearm;
	public final StaticEntity leftShin;
	public final StaticEntity rightShin;

	public Player(Camera camera, Transformation transform) {
		super(null, 
				null, transform);
		transform.setScale(0.3f);
		
		StaticEntity body = new StaticEntity(ResourceManager.getMesh("body"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		addChild(body);
		World.getWorld().addEntity(body);
		
		StaticEntity head = new StaticEntity(ResourceManager.getMesh("head"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		head.getTransform().localY = 0.85f;
		body.addChild(head);
		World.getWorld().addEntity(head);
		
		StaticEntity eyes = new StaticEntity(ResourceManager.getMesh("eyes"),
				new Material(ResourceManager.getTexture("eyes")), new Transformation());
		eyes.getTransform().localY = -1.0f;
		head.addChild(eyes);
		World.getWorld().addEntity(eyes);
		
		leftArm = new StaticEntity(ResourceManager.getMesh("leftArm"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		leftArm.getTransform().localX = 0.65f;
		leftArm.getTransform().localY = 0.6f;
		body.addChild(leftArm);
		World.getWorld().addEntity(leftArm);
		
		{
			leftForearm = new StaticEntity(ResourceManager.getMesh("leftForearm"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			leftForearm.getTransform().localX = 1.08f;
			leftForearm.getTransform().localY = -0.7f;
			leftArm.addChild(leftForearm);
			World.getWorld().addEntity(leftForearm);
		}
		
		rightArm = new StaticEntity(ResourceManager.getMesh("rightArm"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		rightArm.getTransform().localX = -0.65f;
		rightArm.getTransform().localY = 0.6f;
		body.addChild(rightArm);
		World.getWorld().addEntity(rightArm);		
			
		{
			rightForearm = new StaticEntity(ResourceManager.getMesh("rightForearm"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			rightForearm.getTransform().localX = -1.08f;
			rightForearm.getTransform().localY = -0.7f;
			rightArm.addChild(rightForearm);
			World.getWorld().addEntity(rightForearm);
		}
		
		leftHip = new StaticEntity(ResourceManager.getMesh("hip"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		leftHip.getTransform().localX = 0.4f;
		leftHip.getTransform().localY = -0.45f;
		body.addChild(leftHip);
		World.getWorld().addEntity(leftHip);
		
		{
			leftShin = new StaticEntity(ResourceManager.getMesh("shin"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			leftShin.getTransform().localY = -0.8f;
			leftHip.addChild(leftShin);
			World.getWorld().addEntity(leftShin);
		}
		
		rightHip = new StaticEntity(ResourceManager.getMesh("hip"),
				new Material(ResourceManager.getTexture("player")), new Transformation());
		rightHip.getTransform().localX = -0.4f;
		rightHip.getTransform().localY = -0.45f;
		body.addChild(rightHip);
		World.getWorld().addEntity(rightHip);
		
		{
			rightShin = new StaticEntity(ResourceManager.getMesh("shin"),
					new Material(ResourceManager.getTexture("player")), new Transformation());
			rightShin.getTransform().localY = -0.8f;
			rightHip.addChild(rightShin);
			World.getWorld().addEntity(rightShin);
		}
		
		PlayerAnimationComponent playerAnim = new PlayerAnimationComponent(this);
		addComponent(new PlayerAnimationComponent(this));
		addComponent(new PlayerControllerComponent(camera, transform, playerAnim));
	}


}
