package com.wfe.font;

import java.util.ArrayList;
import java.util.List;

import com.wfe.graph.Mesh;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.Utils;


public class GUIText {

	private float width;
	
	private float x, y;
	private float rotation;
	private float scaleX, scaleY;
	
	private Mesh mesh;
	
	private final Color color;

    private static final int VERTICES_PER_QUAD = 4;

    private final FontTexture fontTexture;
    
    private String text;

    public GUIText(String text, FontTexture fontTexture) {
        this.text = text;
        this.fontTexture = fontTexture;
        color = new Color(1.0f, 1.0f, 1.0f);
        mesh = buildMesh();
        scaleX = scaleY = 1;
    }
    
    private Mesh buildMesh() {
    	width = 0;
    	
        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        List<Integer> indices   = new ArrayList<>();
        char[] characters = text.toCharArray();
        int numChars = characters.length;

        float startx = 0;
        for(int i=0; i<numChars; i++) {
            FontTexture.CharInfo charInfo = fontTexture.getCharInfo(characters[i]);
            width += charInfo.getWidth();
            
            // Build a character tile composed by two triangles
            
            // Left Top vertex
            positions.add(startx); // x
            positions.add(0.0f); //y
            textCoords.add( (float)charInfo.getStartX() / (float)fontTexture.getWidth());
            textCoords.add(0.0f);
            indices.add(i*VERTICES_PER_QUAD);
                        
            // Left Bottom vertex
            positions.add(startx); // x
            positions.add((float)fontTexture.getHeight()); //y
            textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
            textCoords.add(1.0f);
            indices.add(i*VERTICES_PER_QUAD + 1);

            // Right Bottom vertex
            positions.add(startx + charInfo.getWidth()); // x
            positions.add((float)fontTexture.getHeight()); //y
            textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth() )/ (float)fontTexture.getWidth());
            textCoords.add(1.0f);
            indices.add(i*VERTICES_PER_QUAD + 2);

            // Right Top vertex
            positions.add(startx + charInfo.getWidth()); // x
            positions.add(0.0f); //y
            textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth() )/ (float)fontTexture.getWidth());
            textCoords.add(0.0f);
            indices.add(i*VERTICES_PER_QUAD + 3);
            
            // Add indices por left top and bottom right vertices
            indices.add(i*VERTICES_PER_QUAD);
            indices.add(i*VERTICES_PER_QUAD + 2);
            
            startx += charInfo.getWidth();
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
    	return fontTexture.getHeight() * scaleY;
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
	}
	
	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
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
    
}