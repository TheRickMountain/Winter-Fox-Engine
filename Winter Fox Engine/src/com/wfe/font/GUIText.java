package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

import com.wfe.graph.Mesh;
import com.wfe.input.Mouse;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.Utils;


public class GUIText {

	protected static final int SPACE_ASCII = 32;
	
	private float width;
	private int numberOfLines;
	private int maxLineLength = 200;
	
	private float x, y;
	private float rotation;
	private float scaleX, scaleY;
	
	private Mesh mesh;
	
	private final Color color;

    private static final int VERTICES_PER_QUAD = 4;

    private final FontTexture fontTexture;
    
    private String text;

    public GUIText(String text, FontTexture fontTexture, int maxLineLength) {
        this.text = text;
        this.fontTexture = fontTexture;
        color = new Color(1.0f, 1.0f, 1.0f);
        mesh = buildMesh();
        this.maxLineLength = maxLineLength;
        setScale(1, 1);
    }
    
    private List<Line> createStructure() {
    	char[] chars = text.toCharArray();
    	List<Line> lines = new ArrayList<>();
    	Line currentLine = new Line(40, maxLineLength);
    	Word currentWord = new Word();
    	for(char c : chars) {
    		int ascii = (int)c;
    		if(ascii == SPACE_ASCII) {
    			boolean added = currentLine.attemptToAddWord(currentWord);
    			if(!added) {
    				lines.add(currentLine);
    				currentLine = new Line(40, maxLineLength);
    				currentLine.attemptToAddWord(currentWord);
    			}
    			CharInfo character = fontTexture.getCharInfo((char)ascii);
        		currentWord.addCharInfo(character);
    			
    			currentWord = new Word();
    			continue;
    		}
    		CharInfo character = fontTexture.getCharInfo((char)ascii);
    		currentWord.addCharInfo(character);
    	}
    	completeStructure(lines, currentLine, currentWord);    	
    	return lines;
    }
    
    private void completeStructure(List<Line> lines, Line currentLine, Word currentWord) {
    	boolean added = currentLine.attemptToAddWord(currentWord);
    	if(!added) {
    		lines.add(currentLine);
    		currentLine = new Line(40, maxLineLength);
    		currentLine.attemptToAddWord(currentWord);
    	}
    	lines.add(currentLine);
    }
    
    private Mesh buildMesh() {
    	List<Line> lines = createStructure();
    	numberOfLines = lines.size();
    	
    	width = 0;
    	
        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        List<Integer> indices   = new ArrayList<>();

        float startx = 0;
        float starty = 0;
        int count = 0;
        
        for(Line line : lines) {
        	for(Word word : line.getWords()) {
        		for(CharInfo charInfo : word.getCharactes()) {
        			if(starty == 0) {
        				width += charInfo.getWidth();
        			}
                    
                    // Build a character tile composed by two triangles
                    
                    // Left Top vertex
                    positions.add(startx); // x
                    positions.add(starty); // y
                    textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
                    textCoords.add(0.0f);
                    indices.add(count * VERTICES_PER_QUAD);
                                
                    // Left Bottom vertex
                    positions.add(startx); // x
                    positions.add(starty + (float)fontTexture.getHeight()); // y
                    textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
                    textCoords.add(1.0f);
                    indices.add(count * VERTICES_PER_QUAD + 1);

                    // Right Bottom vertex
                    positions.add((float) (startx + charInfo.getWidth())); // x
                    positions.add(starty + (float)fontTexture.getHeight()); // y
                    textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth() )/ (float)fontTexture.getWidth());
                    textCoords.add(1.0f);
                    indices.add(count * VERTICES_PER_QUAD + 2);

                    // Right Top vertex
                    positions.add((float) (startx + charInfo.getWidth())); // x
                    positions.add(starty); // y
                    textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth() )/ (float)fontTexture.getWidth());
                    textCoords.add(0.0f);
                    indices.add(count * VERTICES_PER_QUAD + 3);
                    
                    // Add indices por left top and bottom right vertices
                    indices.add(count * VERTICES_PER_QUAD);
                    indices.add(count * VERTICES_PER_QUAD + 2);
                    
                    startx += charInfo.getWidth();
                    count++;
        		}
        	}
        	startx = 0;
        	starty += fontTexture.getHeight();
        }

        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i->i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, indicesArr);
        return mesh;
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {  	
    	if(!this.text.equals(text)) {
    		this.text = text;
        	this.mesh.delete();
        	this.mesh = buildMesh();
    	}
    }
    
    public float getWidth() {
    	return width * scaleX;
    }
    
    public float getHeight() {
    	return (numberOfLines * fontTexture.getHeight()) * scaleY;
    }

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
		this.maxLineLength *= scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.maxLineLength *= scaleX;
	}
	
	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		
		this.maxLineLength *= scaleX;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Texture getTexture() {
		return fontTexture.getTexture();
	}
	
	public boolean isMouseOvered() {
		return Mouse.getX() > x && Mouse.getX() < x + getWidth() &&
				Mouse.getY() > y && Mouse.getY() < y + getHeight();
	}
    
}