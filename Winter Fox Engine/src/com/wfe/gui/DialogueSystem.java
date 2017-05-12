package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.font.GUIText;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class DialogueSystem {

	public DialogueNode[] node;
	public int currentNode;
	
	private GUIFrame background;
	
	private GUIText npcText;	
	private List<GUIText> answersText = new ArrayList<>();
	
	private boolean showDialogue = true;
	
	public DialogueSystem(DialogueNode[] node, int currentNode) {
		this.node = node;
		this.currentNode = currentNode;
		
		background = new GUIFrame(new Rect(0, 0, 500, 200), false);
		
		npcText = new GUIText("", FontRenderer.ARIAL);
		
		updateText();
		
		updatePositions();
	}
	
	public void update() {
		if(showDialogue) {
			if(Mouse.isButtonDown(0)) {				
				for(int i = 0; i < node[currentNode].playerAnswer.length; i++) {
					GUIText text = answersText.get(i);
					if(text.isMouseOvered()) {
						if(node[currentNode].playerAnswer[i].speakEnd) {
							showDialogue = false;
						} else {
							currentNode = node[currentNode].playerAnswer[i].toNode;
							updateText();
							updatePositions();
						}
					}
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public void render() {
		if(showDialogue) {
			GUIRenderer.render(background.getFrameTextures());
		}
	}
	
	public void renderText() {
		if(showDialogue) {
			FontRenderer.render(npcText);
			
			for(GUIText text : answersText) {
				FontRenderer.render(text);
			}
		}
	}
	
	private void updateText() {
		npcText.setText(node[currentNode].npcText);
		
		answersText.clear();
		for(int i = 0; i < node[currentNode].playerAnswer.length; i++) {
			answersText.add(new GUIText(node[currentNode].playerAnswer[i].text, FontRenderer.ARIAL));
		}
	}
	
	private void updatePositions() {
		background.setPosition(Display.getWidth() / 2 - background.rect.width / 2, 
				Display.getHeight() - background.rect.height - Slot.SIZE - 15);
		
		npcText.setPosition(background.getX(), background.getY());
		
		for(int i = 0; i < answersText.size(); i++) {
			answersText.get(i).setPosition(background.getX(), 
					npcText.getY() + npcText.getHeight() + (i * (npcText.getHeight() + 5)));
		}
	}
	
}
