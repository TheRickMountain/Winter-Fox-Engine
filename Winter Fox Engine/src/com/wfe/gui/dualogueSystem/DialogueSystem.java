package com.wfe.gui.dualogueSystem;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.gui.GUIFrame;
import com.wfe.gui.GUIManager;
import com.wfe.gui.Slot;
import com.wfe.input.Mouse;
import com.wfe.newFont.GUIText;
import com.wfe.newFont.TextRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class DialogueSystem {

	public DialogueNode[] node;
	public int currentNode;
	
	private GUIFrame background;
	
	private GUIText npcText;	
	private List<GUIText> answersText = new ArrayList<>();
	
	public DialogueSystem() {
		background = new GUIFrame(new Rect(0, 0, 500, 200), false);
		npcText = new GUIText("", 96);
	}
	
	public void update() {			
		for(int i = 0; i < node[currentNode].playerAnswer.length; i++) {
			GUIText text = answersText.get(i);
			if(text.isMouseOvered()) {
				text.getColor().set(1.0f, 1.0f, 0.0f);
				
				if(Mouse.isButtonDown(0)) {	
					if(node[currentNode].playerAnswer[i].isSpeakEnd()) {
						GUIManager.state = GUIManager.GUIState.GAME;
					} else if(node[currentNode].playerAnswer[i].isPlayMancala())  {
						GUIManager.state = GUIManager.GUIState.MANCALA;
					} else if(node[currentNode].playerAnswer[i].isHasQuest()) {
						GUIManager.questSystem.addQuest(node[currentNode].playerAnswer[i].getQuest());
					} else {
						currentNode = node[currentNode].playerAnswer[i].toNode;
						updateText();
						updatePositions();
					}
				}
			} else {
				text.getColor().set(1.0f, 1.0f, 1.0f);
			}
		}
	}
	
	public void render() {
		GUIRenderer.render(background.getFrameTextures());
	}
	
	public void renderText() {
		TextRenderer.render(npcText);
		
		for(GUIText text : answersText) {
			TextRenderer.render(text);
		}
	}
	
	public void open(DialogueNode[] node, int currentNode) {
		this.node = node;
		this.currentNode = currentNode;
		
		updateText();
		updatePositions();
		
		GUIManager.state = GUIManager.GUIState.DIALOGUE;
	}
	
	private void updateText() {
		npcText.setText(node[currentNode].npcText);
		
		answersText.clear();
		for(int i = 0; i < node[currentNode].playerAnswer.length; i++) {
			answersText.add(new GUIText(node[currentNode].playerAnswer[i].text, 96));
		}
	}
	
	public void updatePositions() {
		background.setPosition(Display.getWidth() / 2 - background.rect.width / 2, 
				Display.getHeight() - background.rect.height - Slot.SIZE - 15);
		
		npcText.setPosition(background.getX(), background.getY());
		
		for(int i = 0; i < answersText.size(); i++) {
			answersText.get(i).setPosition(background.getX(), 
					npcText.getY() + npcText.getHeight() + (i * (npcText.getHeight() + 5)));
		}
	}
	
}
