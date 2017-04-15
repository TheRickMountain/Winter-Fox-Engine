package com.wfe.tileEngine;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Chunk {

	public static final int SIZE = 16;
	private int posX, posY;
	private int VAO, vVBO, vtVBO;
	
	private float[] vertices;
	private float[] texCoords;
	
	private Tile[][] tiles;
	
	private boolean isVisible = true;
	
	public static final float SPRITE_SHEET = 4;
	private boolean rebuild = false;
	
	public Chunk(Tile[][] tiles, int x, int z, HeightGenerator heightGenerator) {
		this.tiles = tiles;
		posX = x * 16;
		posY = z * 16;
		
		init(heightGenerator);
		initGL();
	}
	
	int currentTile = 6;
	
	public void init(HeightGenerator heightGenerator) {
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 16; y++) {
				float height = heightGenerator.generateHeight(posX + x, posY + y);
				if((y % 2 == 0)) {
					if(x % 2 == 0) {
						currentTile = 2;
					} else {
						currentTile = 3;
					}
				} else {
					if((x + y) % 2 == 0) {
						currentTile = 7;
					} else {
						currentTile = 6;
					}
				}
				
				if(height > -0.1f)
					tiles[posX + x][posY + y] = new Tile(this, posX + x, posY + y, currentTile);
				else
					tiles[posX + x][posY + y] = new Tile(this, posX + x, posY + y, currentTile);
			}
		}
		
		create();
	}
	
	private void initGL() {
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		vVBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vVBO);
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
		fb.put(vertices).flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		vtVBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vtVBO);
		fb = BufferUtils.createFloatBuffer(texCoords.length);
		fb.put(texCoords).flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		
		GL30.glBindVertexArray(0);
	}
	
	private void create() {
		List<Float> vList = new ArrayList<Float>();
		List<Float> vtList = new ArrayList<Float>();
		
		// Vertices
		for(int x = posX; x < posX + SIZE; x++) {
			for(int y = posY; y < posY + SIZE; y++) {	
				float x0 = (float)x + 0.0f;
		        float x1 = (float)x + 1.0f;
		        float y0 = (float)y + 0.0f;
		        float y1 = (float)y + 1.0f;
				
				vList.add((float) x0); vList.add(0.0f); vList.add((float) y0);
				vList.add((float) x0); vList.add(0.0f); vList.add((float) y1);
				vList.add((float) x1); vList.add(0.0f); vList.add((float) y0); 		
				vList.add((float) x1); vList.add(0.0f); vList.add((float) y1); 
			}
		}
		
		vertices = new float[vList.size()];
		int i = 0;
		
		for(Float f : vList) {
			vertices[i++] = (f != null ? f : Float.NaN);
		}
		
		// Texture Coords
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				float u0 = ((tiles[posX + x][posY + y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET) + 0.005f;
		        float u1 = (u0 + (1.0f / SPRITE_SHEET)) - 0.01f;
		        float v0 = ((tiles[posX + x][posY + y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET) + 0.005f;
		        float v1 = (v0 + (1.0f / SPRITE_SHEET)) - 0.01f;
		        
				vtList.add(u0); vtList.add(v0);
				vtList.add(u0); vtList.add(v1);
				vtList.add(u1); vtList.add(v0);
				vtList.add(u1); vtList.add(v1);
			}
		}
		
		texCoords = new float[vtList.size()];
		i = 0;
		
		for(Float f : vtList) {
			texCoords[i++] = (f != null ? f : Float.NaN);
		}
	}
	
	protected void rebuild() {
		List<Float> vtList = new ArrayList<Float>();
		// Texture Coords
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				float u0 = ((tiles[posX + x][posY + y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET) + 0.005f;
		        float u1 = (u0 + (1.0f / SPRITE_SHEET)) - 0.01f;
		        float v0 = ((tiles[posX + x][posY + y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET) + 0.005f;
		        float v1 = (v0 + (1.0f / SPRITE_SHEET)) - 0.01f;

				vtList.add(u0); vtList.add(v0);
				vtList.add(u0); vtList.add(v1);
				vtList.add(u1); vtList.add(v0);
				vtList.add(u1); vtList.add(v1);
			}
		}

		int i = 0;

		for(Float f : vtList) {
			texCoords[i++] = (f != null ? f : Float.NaN);
		}
		
		// Change texture coordinates buffer
		GL30.glBindVertexArray(VAO);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vtVBO);
		FloatBuffer fb = BufferUtils.createFloatBuffer(texCoords.length);
		fb.put(texCoords).flip();		
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, fb);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void render() {
		if(rebuild) {
			rebuild();
			rebuild = false;
		}
		
		GL30.glBindVertexArray(VAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, SIZE * SIZE * 4);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup() {
		GL15.glDeleteBuffers(vVBO);
		GL15.glDeleteBuffers(vtVBO);
		GL30.glDeleteVertexArrays(VAO);
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(tiles[posX + i][posY + j].isHasEntity()) {
					tiles[posX + i][posY + j].getEntity().setVisible(isVisible);
				}
			}
		}
		this.isVisible = isVisible;
	}
	
}
