package com.wfe.entities;

import java.util.ArrayList;
import java.util.List;

import com.wfe.components.BoundingBoxComponent;
import com.wfe.components.NpcComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.dualogueSystem.Answer;
import com.wfe.gui.dualogueSystem.DialogueNode;
import com.wfe.gui.questSystem.CollectionObjective;
import com.wfe.gui.questSystem.IQuestObjective;
import com.wfe.gui.questSystem.Quest;
import com.wfe.gui.questSystem.QuestInfo;

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
		
		List<IQuestObjective> objectives = new ArrayList<IQuestObjective>();
		objectives.add(new CollectionObjective("Gather", 5, ItemDatabase.getItem(Item.MUSHROOM), 
				"Settler wants you to bring him 5 mushrooms", false));
		
		/*** Test ***/
		DialogueNode[] dialogueNode = new DialogueNode[]{
				new DialogueNode("Hello traveler.",
						new Answer("1. Do you have a job for me?", 1),
						new Answer("2. Let's play Mancala.", 2).setPlayMancala(true),
						new Answer("3. Bye!", 0).setSpeakEnd(true)),
				new DialogueNode("Find 4 fiber and bring it to me.",
						new Answer("1. Yes.", 0).setQuest(
								new Quest(
										new QuestInfo(
												"Busy mushrooms", 
												"Settler wants to cook mushrooms soup", 
												"Test"),
										objectives)),
						new Answer("2. [Back]", 0)),
				new DialogueNode("Ok.",
						new Answer("1. [Back]", 0))
		};
		/*** *** ***/	
		
		addComponent(new NpcComponent(dialogueNode, 0));
	}

}