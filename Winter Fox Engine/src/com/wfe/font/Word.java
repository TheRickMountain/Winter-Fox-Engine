package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

public class Word {
	
	private List<CharInfo> characters = new ArrayList<>();
	private double width = 0;
	
	public Word() {
		
	}
	
	public void addCharInfo(CharInfo charInfo) {
		characters.add(charInfo);
		width += charInfo.getWidth();
	}

	public List<CharInfo> getCharactes() {
		return characters;
	}
	
	public double getWidth() {
		return width;
	}
	
}
