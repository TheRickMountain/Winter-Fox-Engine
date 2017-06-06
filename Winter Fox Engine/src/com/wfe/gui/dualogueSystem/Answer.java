package com.wfe.gui.dualogueSystem;

import com.wfe.gui.questSystem.Quest;

public class Answer {
	
	public String text;
	public int toNode;
	private boolean isSpeakEnd;
	private boolean isPlayMancala;
	private Quest quest;
	private boolean hasQuest;
	
	public Answer(String text, int toNode) {
		this.text = text;
		this.toNode = toNode;
	}

	public boolean isSpeakEnd() {
		return isSpeakEnd;
	}

	public Answer setSpeakEnd(boolean value) {
		if(value) {
			isPlayMancala = false;
		}
		
		this.isSpeakEnd = value;
		return this;
	}
	
	public boolean isPlayMancala() {
		return isPlayMancala;
	}
	
	public Answer setPlayMancala(boolean value) {
		if(value) {
			isSpeakEnd = false;
		}
		
		this.isPlayMancala = value;
		return this;
	}
	
	public Answer setQuest(Quest quest) {
		this.quest = quest;
		hasQuest = true;
		return this;
	}
	
	public boolean isHasQuest() {
		return hasQuest;
	}
	
	public Quest getQuest() {
		return quest;
	}

}
