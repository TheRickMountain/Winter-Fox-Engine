package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

public class Line {
	
	private List<Word> words = new ArrayList<>();
	private float length;
	
	private float maxLength;

	public Line(float maxLength) {
		this.maxLength = maxLength;
	}
	
	public boolean addWord(Word word) {
		float wordLength = word.getLength();
		
		if(length + wordLength <= maxLength) {
			words.add(word);
			length += wordLength + (MetaFile.getCharacter(32).getxAdvance() * word.getFontSize());
			return true;
		}
		
		return false;
	}
	
	public List<Word> getWords() {
		return words;
	}
	
	public float getLength() {
		return length;
	}
	
}
