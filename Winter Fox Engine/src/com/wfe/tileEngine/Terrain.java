package com.wfe.tileEngine;

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
	
	private int sizeX, sizeZ;
	private Camera camera;
	private Texture spriteSheet;
	
	private TerrainShader shader;
	
	private HeightGenerator heightGenerator;
	
	public Terrain(Tile[][] tiles, int sizeX, int sizeZ, Camera camera) throws Exception {
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.camera = camera;
		shader = new TerrainShader();
		heightGenerator = new HeightGenerator();
		init(tiles);
	}
	
	private void init(Tile[][] tiles) throws Exception {
		spriteSheet = Texture.newTexture(new MyFile("textures/terrain.png"))
				.normalMipMap(-0.4f).clampEdges().anisotropic().create();
		
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeZ; y++) {
				chunks.add(new Chunk(tiles, x, y, heightGenerator));
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
	
	public void cleanup() {
		for(Chunk chunk : chunks) {
			chunk.cleanup();
		}
		shader.cleanUp();
	}
	
}
