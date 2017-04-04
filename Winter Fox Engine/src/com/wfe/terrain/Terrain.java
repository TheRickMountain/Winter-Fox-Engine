package com.wfe.terrain;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.textures.Texture;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MyFile;
import com.wfe.weather.DirectionalLight;

public class Terrain {

	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	private int width, height;
	private int sizex, sizez;
	private Camera camera;
	private Texture spriteSheet;
	
	private TerrainShader shader;
	
	private HeightGenerator heightGenerator;
	
	public Terrain(int sizex, int sizez, Camera camera, float[][] tilesHeight) {
		this.sizex = sizex;
		this.sizez = sizez;
		this.width = sizex * Chunk.SIZE;
		this.height = sizez * Chunk.SIZE;
		this.camera = camera;
		shader = new TerrainShader();
		heightGenerator = new HeightGenerator();
		init(tilesHeight);
	}
	
	public void init(float[][] tilesHeight) {
		spriteSheet = Texture.newTexture(new MyFile("textures/terrain.png"))
				.normalMipMap(-0.4f).clampEdges().anisotropic().create();
		
		for(int x = 0; x < sizex; x++) {
			for(int y = 0; y < sizez; y++) {
				chunks.add(new Chunk(x, y, heightGenerator, tilesHeight));
			}
		}
		
		shader.start();
		shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		shader.stop();
	}
	
	public void update(float x, float z) {
		for(Chunk chunk : chunks) {
			float distance = MathUtils.getDistance(x, z, chunk.getPosX(), chunk.getPosY());
			if(distance >= 48) {
				chunk.setVisible(false);
			} else {
				chunk.setVisible(true);
			}

		}
	}
	
	public void render() {
		shader.start();
		
		if(Display.isResized()) {
			shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		}
		
		shader.viewMatrix.loadMatrix(camera.getViewMatrix());
		shader.lightPosition.loadVec3(DirectionalLight.LIGHT_POSITION);
		shader.lightColor.loadVec3(DirectionalLight.LIGHT_COLOR);
		
		spriteSheet.bind(0);
		
		for(Chunk chunk : chunks) {
			if(chunk.isVisible())
				chunk.render();
		}
		shader.start();
	}
	
	public Tile getTile(int x, int y) {
		int terrainX = x / Chunk.SIZE;
		int terrainY = y / Chunk.SIZE;
		
		int tileX = x - terrainX * Chunk.SIZE;
		int tileY = y - terrainY * Chunk.SIZE;
		
		return chunks.get(terrainX * sizez + terrainY).getTile(tileX, tileY);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void cleanup() {
		for(Chunk chunk : chunks) {
			chunk.cleanup();
		}
		shader.cleanUp();
	}
	
}
