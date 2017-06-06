package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.gui.questSystem.Quest;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class QuestSystem {
	
	private GUIFrame background;
	
	private List<Quest> currentQuests = new ArrayList<>();
	
	public QuestSystem() {
		background = new GUIFrame(new Rect(0, 0, 600, 350), false);
	}
	
	public void update() {
		
	}
	
	public void render() {
		GUIRenderer.render(background.getFrameTextures());
	}
	
	public void renderText() {
		
	}
	
	public void updatePositions() {
		background.setPosition(
				Display.getWidth() / 2 - background.getWidth() / 2, 
				Display.getHeight() / 2 - background.getHeight() / 2);
	}
	
	public void addQuest(Quest quest) {
		System.out.println(quest.getInfo().getTitle());
		currentQuests.add(quest);
	}

}
