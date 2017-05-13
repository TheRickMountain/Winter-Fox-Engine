package com.wfe.entities;

import com.wfe.components.BoundingBoxComponent;
import com.wfe.components.NpcComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Answer;
import com.wfe.gui.DialogueNode;

public class Settler extends Entity {
	
	public Entity leftArm;
	public Entity rightArm;
	
	public Entity leftForearm;
	public Entity rightForearm;
	
	public Entity rightHip;
	public Entity leftHip;
	
	public Entity rightShin;
	public Entity leftShin;
	
	public Settler(Transformation transform) {
		super(null, null, transform);
		setId(0);
		setTag("npc");
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
		
		
		addComponent(new BoundingBoxComponent(-0.5f, 0.75f, 1.25f, 0.75f, transform));
		
		/*** Test ***/
		DialogueNode[] dialogueNode = new DialogueNode[]{
				new DialogueNode("Hello traveler.",
						new Answer("1. Do you have a job for me?", 1, false),
						new Answer("2. Do you want to play Mancala?", 2, false),
						new Answer("3. Bye!", 0, true)),
				new DialogueNode("Of course, look at the letter.",
						new Answer("1. [Back]", 0, false)),
				new DialogueNode("Yes, I do.",
						new Answer("1. [Back]", 0, false))
		};
		/*** *** ***/	
		
		addComponent(new NpcComponent(dialogueNode, 0));
	}

}