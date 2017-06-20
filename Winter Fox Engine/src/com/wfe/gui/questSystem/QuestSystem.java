package com.wfe.gui.questSystem;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.gui.GUIFrame;
import com.wfe.newFont.GUIText;
import com.wfe.newFont.TextRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class QuestSystem {
	
	private GUIFrame background;
	
	private List<Quest> currentQuests = new ArrayList<>();
	
	private List<GUIText> questNames = new ArrayList<>();
	
	public QuestSystem() {
		background = new GUIFrame(new Rect(0, 0, 600, 350), false);
	}
	
	public void update() {
		
	}
	
	public void render() {
		GUIRenderer.render(background.getFrameTextures());
	}
	
	public void renderText() {
		for(GUIText text : questNames) {
			TextRenderer.render(text);
		}
	}
	
	public void updatePositions() {
		background.setPosition(
				Display.getWidth() / 2 - background.getWidth() / 2, 
				Display.getHeight() / 2 - background.getHeight() / 2);
		
		for(int i = 0; i < questNames.size(); i++) {
			GUIText text = questNames.get(i);
			text.setPosition(background.getX(), background.getY() + (i * (text.getHeight() + 5)));
		}
	}
	
	public void addQuest(Quest quest) {
		currentQuests.add(quest);
		
		questNames.add(new GUIText(quest.getInfo().getTitle(), 96));
		
		updatePositions();
	}

}
