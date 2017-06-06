package com.wfe.gui.dualogueSystem;

public class DialogueNode {

	public String npcText;
	public Answer[] playerAnswer;
	
	public DialogueNode(String npcText, Answer... playerAnswer) {
		this.npcText = npcText;
		this.playerAnswer = playerAnswer;
	}
	
}
