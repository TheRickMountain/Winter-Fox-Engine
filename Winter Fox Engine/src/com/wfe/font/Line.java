package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

public class Line {
	
	private double maxLength;
	private double spaceSize;
	
	private List<Word> words = new ArrayList<>();
	private double currentLineLength = 0;
	
	public Line(double spaceWidth, double maxLength) {
		this.spaceSize = spaceWidth;
		this.maxLength = maxLength;
	}
	
	/* Adding word to this line, if line is greater then maximal 
	 * length, then line doesn't add word and return false */
	public boolean attemptToAddWord(Word word) {
		double additionalLength = word.getWidth();
		additionalLength += !words.isEmpty() ? spaceSize : 0;
		if(currentLineLength + additionalLength <= maxLength) {
			words.add(word);
			currentLineLength += additionalLength;
			return true;
		} else {
			return false;
		}
	}
	
	public double getMaxLength() {
		return maxLength;
	}
	
	public double getLineLength() {
		return currentLineLength;
	}
	
	public List<Word> getWords() {
		return words;
	}

}
