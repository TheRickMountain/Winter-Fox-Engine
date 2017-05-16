package com.wfe.gui;

public class Answer {
	
	public String text;
	public int toNode;
	private boolean speakEnd;
	private boolean playMancala;
	
	public Answer(String text, int toNode) {
		this.text = text;
		this.toNode = toNode;
	}

	public boolean isSpeakEnd() {
		return speakEnd;
	}

	public Answer setSpeakEnd(boolean value) {
		if(value) {
			playMancala = false;
		}
		
		this.speakEnd = value;
		return this;
	}
	
	public boolean isPlayMancala() {
		return playMancala;
	}
	
	public Answer setPlayMancala(boolean value) {
		if(value) {
			speakEnd = false;
		}
		
		this.playMancala = value;
		return this;
	}

}
