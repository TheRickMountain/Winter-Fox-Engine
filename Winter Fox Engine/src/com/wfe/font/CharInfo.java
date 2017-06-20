package com.wfe.font;

public class CharInfo {
	
	private char letter;
	
	private final int startX;

	private final int width;

	public CharInfo(char letter, int startX, int width) {
		this.letter = letter;
		this.startX = startX;
		this.width = width;
	}
	
	public char getLetter() {
		return letter;
	}

	public int getStartX() {
		return startX;
	}

	public int getWidth() {
		return width;
	}
	
}