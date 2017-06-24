package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

public class Word {
	
	List<MyCharacter> letters = new ArrayList<>();
	private float length;
	private float fontSize;
	
	public Word(float fontSize) {
		this.fontSize = fontSize;
	}
	
	public void addLetter(char c) {
		MyCharacter letter = MetaFile.getCharacter(c);
		letters.add(letter);
		length += (letter.getWidth() * fontSize);
	}
	
	public List<MyCharacter> getLetters() {
		return letters;
	}
	
	public float getLength() {
		return length;
	}
	
	public float getFontSize() {
		return fontSize;
	}
	
}
