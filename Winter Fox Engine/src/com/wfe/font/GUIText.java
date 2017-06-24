package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.graph.Mesh;
import com.wfe.input.Mouse;
import com.wfe.utils.Color;

public class GUIText {

	private Mesh mesh;
	
	private String text;
	private float x, y;
	private float scaleX = 1, scaleY = 1;
	private float fontSize;
	private Color color = new Color(Color.WHITE);
	
	private float lineWidth;
	private float lineHeight;
	
	private int numberOfLines;
	
	private int maxLineLength = Display.getWidth();
	
	public GUIText(String text, int fontSize) {
		this.text = text;
		this.fontSize = fontSize;
		buildMesh();
	}
	
	public GUIText(String text, int fontSize, int maxLineLength) {
		this.text = text;
		this.fontSize = fontSize;
		this.maxLineLength = maxLineLength;
		buildMesh();
	}
	
	private void buildMesh() {
		mesh = new Mesh(buildVertices(), 4);
	}
	
	private List<Line> createStructure(String text) {
		char[] letters = text.toCharArray();
		List<Line> lines = new ArrayList<>();
		Line currentLine = new Line(maxLineLength);
		Word currentWord = new Word(fontSize);
		
		for(int i = 0; i < letters.length; i++) {
			if(letters[i] == 32) {
				boolean added = currentLine.addWord(currentWord);
				if(!added) {
					lines.add(currentLine);
					currentLine = new Line(maxLineLength);
					currentLine.addWord(currentWord);
				}
				currentWord = new Word(fontSize);
				continue;
			}
			
			currentWord.addLetter(letters[i]);
		}
		boolean added = currentLine.addWord(currentWord);
		if(!added) {
			lines.add(currentLine);
			currentLine = new Line(maxLineLength);
			currentLine.addWord(currentWord);
		}
		lines.add(currentLine);
		return lines;
	}
	
	private float[] buildVertices() {		
		List<Line> lines = createStructure(text);
		
		numberOfLines = lines.size();
		lineWidth = 0;
		lineHeight = (float) (numberOfLines * (MetaFile.getLineHeight() * fontSize));
		
		float startx = 0f;
		float starty = 0f;
		
		int count = 0;
		
		List<Float> data = new ArrayList<>();
		for(Line line : lines) {
			for(Word word : line.getWords()) {
				for(MyCharacter letter : word.getLetters()) {					
					float x = (float) letter.getX();
					float y = (float) letter.getY();
					float w = (float) letter.getWidth();
					float h = (float) letter.getHeight();
					float yoffset = (float) letter.getyOffset();
					
					data.add(startx);                  data.add(starty + h * fontSize + yoffset * fontSize); data.add(x); 	 data.add(y + h);
					data.add(startx);                  data.add(starty + yoffset * fontSize);                data.add(x);     data.add(y);
					data.add(startx + (w * fontSize)); data.add(starty + yoffset * fontSize);                data.add(x + w); data.add(y);
					
					data.add(startx);                  data.add(starty + h * fontSize + yoffset * fontSize); data.add(x); 	 data.add(y + h);
					data.add(startx + (w * fontSize)); data.add(starty + yoffset * fontSize);                data.add(x + w); data.add(y);
					data.add(startx + (w * fontSize)); data.add(starty + h * fontSize + yoffset * fontSize); data.add(x + w); data.add(y + h);
					
					startx += (w * fontSize);
				}
				
				if(count <= line.getWords().size() - 1) {
					MyCharacter letter = MetaFile.getCharacter(32);
					startx += (letter.getxAdvance() * 2) * fontSize;
				}
				
				count++;
			}
			
			if(lineWidth < line.getLength()) {
				lineWidth = line.getLength();
			}
			
			starty += (MetaFile.getLineHeight() * fontSize);
			startx = 0;
			count = 0;
		}
		
		return listToArray(data);
	}
	
	public void setPosition(float f, float y) {
		this.x = f;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setScale(int scaleX, int scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public float getScaleX() {
		return scaleX;
	}
	
	public float getScaleY() {
		return scaleY;
	}
	
	public float getFontSize() {
		return fontSize;
	}
	
	public Color getColor() {
		return color;
	}
	
	public float getWidth() {
		return lineWidth * scaleX;
	}
	
	public float getHeight() {
		return lineHeight * scaleY;
	}
	
	public int getNumberOfLines() {
		return numberOfLines;
	}
	
	public void setText(String text) {
		if(!this.text.equals(text)) {
			this.text = text;
			mesh.delete();
			buildMesh();
		}
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	private static float[] listToArray(List<Float> listOfFloats) {
		 float[] array = new float[listOfFloats.size()];
		 for (int i = 0; i < array.length; i++) {
			 array[i] = listOfFloats.get(i);
		 }
		 return array;
	}
	 
	public boolean isMouseOvered() {
		 return Mouse.getX() > x && Mouse.getX() < x + getWidth() &&
				 Mouse.getY() > y && Mouse.getY() < y + getHeight();
	}
	
}
