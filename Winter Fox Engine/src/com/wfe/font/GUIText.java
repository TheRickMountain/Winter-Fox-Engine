package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

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
	
	public GUIText(String text, int fontSize) {
		this.text = text;
		this.fontSize = fontSize;
		buildMesh();
	}
	
	private void buildMesh() {
		mesh = new Mesh(buildVertices(), 4);
	}
	
	private float[] buildVertices() {
		List<Float> data = new ArrayList<>();
		
		char[] letters = text.toCharArray();
		
		float startx = 0;
		float starty = 0;
		
		lineHeight = (float) (MetaFile.getLineHeight() * fontSize);
		
		for(int i = 0; i < letters.length; i++) {	
			if(letters[i] == '\n') {
				starty += (MetaFile.getLineHeight() * fontSize);
				
				lineHeight += starty;
				
				startx = 0;
				continue;
			}
			
			MyCharacter letter = MetaFile.getCharacter(letters[i]);
			float x = (float) letter.getX();
			float y = (float) letter.getY();
			float w = (float) letter.getWidth();
			float h = (float) letter.getHeight();
			float xadvance = (float) letter.getxAdvance();
			float yoffset = (float) letter.getyOffset();
			
			if(letters[i] == 32) {
				startx += xadvance * fontSize;
				continue;
			}
			
			data.add(startx);                  data.add(starty + h * fontSize + yoffset * fontSize); data.add(x); 	 data.add(y + h);
			data.add(startx);                  data.add(starty + yoffset * fontSize);                data.add(x);     data.add(y);
			data.add(startx + (w * fontSize)); data.add(starty + yoffset * fontSize);                data.add(x + w); data.add(y);
			
			data.add(startx);                  data.add(starty + h * fontSize + yoffset * fontSize); data.add(x); 	 data.add(y + h);
			data.add(startx + (w * fontSize)); data.add(starty + yoffset * fontSize);                data.add(x + w); data.add(y);
			data.add(startx + (w * fontSize)); data.add(starty + h * fontSize + yoffset * fontSize); data.add(x + w); data.add(y + h);
			
			startx += (w * fontSize);
			
			if(lineWidth < startx) {
				lineWidth = startx;
			}
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
