package com.wfe.terrain;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.math.Vector2f;

public class Chunk {

	public static final int SIZE = 16;
	private Vector2f position;
	private int VAO, vVBO, vtVBO;
	
	private float[] vertices;
	private float[] texCoords;
	
	private float[][] tilesHeight;
	private Tile[][] tiles;
	
	private boolean isVisible = true;
	
	public static final float SPRITE_SHEET = 4;
	private boolean rebuild = false;
	
	public Chunk(int x, int y, HeightGenerator heightGenerator, float[][] tilesHeight) {
		position = new Vector2f(x * SIZE, y * SIZE);		
		
		init(heightGenerator, tilesHeight, (int)position.x, (int)position.y);
		initGL();
	}
	
	int currentTile = 6;
	
	public void init(HeightGenerator heightGenerator, float[][] tilesHeight, int iterX, int iterY) {
		this.tilesHeight = tilesHeight;
		tiles = new Tile[SIZE][SIZE];
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				float height = heightGenerator.generateHeight(x + iterX, y + iterY);
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
				
				if(height > -0.1f) {
					tiles[x][y] = new Tile(this, iterX + x, iterY + y, currentTile);
				} else {
					tiles[x][y] = new Tile(this, iterX + x, iterY + y, currentTile);
				}
			}
		}
		
		create(tilesHeight);
	}
	
	private void initGL() {
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		vVBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vVBO);
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
		fb.put(vertices).flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		vtVBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vtVBO);
		fb = BufferUtils.createFloatBuffer(texCoords.length);
		fb.put(texCoords).flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		
		GL30.glBindVertexArray(0);
	}
	
	private void create(float[][] tilesHeight) {
		List<Float> vList = new ArrayList<Float>();
		List<Float> vtList = new ArrayList<Float>();
		
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				float height = tilesHeight[x + (int)position.x][y + (int)position.y];
				top(x, y, height, vList, vtList);
				
				if(height != 0) {
					right(x, y, height, vList, vtList);
					left(x, y, height, vList, vtList);
					back(x, y, height, vList, vtList);
					front(x, y, height, vList, vtList);
				}
			}
		}
		
		vertices = new float[vList.size()];
		int i = 0;
		
		for(Float f : vList) {
			vertices[i++] = (f != null ? f : Float.NaN);
		}
		
		texCoords = new float[vtList.size()];
		i = 0;
		
		for(Float f : vtList) {
			texCoords[i++] = (f != null ? f : Float.NaN);
		}
	}
	
	private void top(float x, float y, float height, List<Float> vList, List<Float> vtList) {
		float u0 = ((tiles[(int) x][(int) y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((tiles[(int) x][(int) y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + position.x); // x
		vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Left Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v1);
        
        // Right Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
        
        // Right Top vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v0);
        
        // Left Top vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Right Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
	}

	private void right(float x, float y, float height,  List<Float> vList, List<Float> vtList) {
		float u0 = ((tiles[(int) x][(int) y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((tiles[(int) x][(int) y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + position.x); // x
		vList.add(0.0f);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Left Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v1);
        
        // Right Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
        
        // Right Top vertex
        vList.add(x + position.x); // x
        vList.add(0.0f);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v0);
        
        // Left Top vertex
        vList.add(x + position.x); // x
        vList.add(0.0f);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Right Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
	}
	
	private void left(float x, float y, float height,  List<Float> vList, List<Float> vtList) {
		float u0 = ((tiles[(int) x][(int) y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((tiles[(int) x][(int) y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + 1.0f + position.x); // x
		vList.add(0.0f);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Left Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v1);
        
        // Right Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
        
        // Right Top vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(0.0f);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v0);
        
        // Left Top vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(0.0f);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Right Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
	}
	
	private void back(float x, float y, float height,  List<Float> vList, List<Float> vtList) {
		float u0 = ((tiles[(int) x][(int) y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((tiles[(int) x][(int) y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + 1.0f + position.x); // x
		vList.add(0.0f);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Left Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v1);
        
        // Right Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
        
        // Right Top vertex
        vList.add(x + position.x); // x
        vList.add(0.0f);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v0);
        
        // Left Top vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(0.0f);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Right Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + 1.0f + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
	}
	
	private void front(float x, float y, float height,  List<Float> vList, List<Float> vtList) {
		float u0 = ((tiles[(int) x][(int) y].getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((tiles[(int) x][(int) y].getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + position.x); // x
		vList.add(0.0f);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Left Bottom vertex
        vList.add(x + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v1);
        
        // Right Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
        
        // Right Top vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(0.0f);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v0);
        
        // Left Top vertex
        vList.add(x + position.x); // x
        vList.add(0.0f);
        vList.add(y + position.y); // y
        vtList.add(u0);
        vtList.add(v0);
        
        // Right Bottom vertex
        vList.add(x + 1.0f + position.x); // x
        vList.add(height);
        vList.add(y + position.y); // y
        vtList.add(u1);
        vtList.add(v1);
	}
	
	private void rebuild(float[][] tilesHeight) {
		create(tilesHeight);
		
		// Change texture coordinates buffer
		GL30.glBindVertexArray(VAO);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vVBO);
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
		fb.put(vertices).flip();
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, fb);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vtVBO);
		fb = BufferUtils.createFloatBuffer(texCoords.length);
		fb.put(texCoords).flip();		
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, fb);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
	}
	
	public void render() {
		if(rebuild) {
			rebuild(tilesHeight);
			rebuild = false;
		}
		
		GL30.glBindVertexArray(VAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length / 3);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void cleanup() {
		GL15.glDeleteBuffers(vVBO);
		GL15.glDeleteBuffers(vtVBO);
		GL30.glDeleteVertexArrays(VAO);
	}
	
	public int getPosX() {
		return (int) position.x;
	}
	
	public int getPosY() {
		return (int) position.y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(tiles[i][j].isHasEntity()) {
					tiles[i][j].getEntity().setVisible(isVisible);
				}
			}
		}
		this.isVisible = isVisible;
	}
	
	protected void setRebuild(boolean value) {
		rebuild = value;
	}
	
}
