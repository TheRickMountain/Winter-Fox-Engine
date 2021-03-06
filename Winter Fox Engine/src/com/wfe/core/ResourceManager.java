package com.wfe.core;

import java.util.HashMap;
import java.util.Map;

import com.wfe.graph.Mesh;
import com.wfe.textures.Texture;


public class ResourceManager {
	
	private static Map<String, Texture> textures = new HashMap<>();
	private static Map<String, Mesh> meshes = new HashMap<>();
	private static Map<String, Integer> sounds = new HashMap<>();

	public static void loadMesh(String name, Mesh mesh) {
        meshes.put(name, mesh);
    }

    public static Mesh getMesh(String meshName) {
        return meshes.get(meshName);
    }
	
	public static void loadTexture(String name, Texture texture){
		textures.put(name, texture);
    }
	
	public static Texture getTexture(String textureName) {
		Texture texture = textures.get(textureName);
		if(texture == null)
			System.err.println("There is no '" + textureName + "' texture!");
		return texture;
	}
	
	public static void loadSound(String name, int sound) {
        sounds.put(name, sound);
    }

    public static int getSound(String soundName) {
    	Integer sound = sounds.get(soundName);
    	if(sound == null)
    		System.err.println("There is no '" + soundName + "' sound!");
        return sounds.get(soundName);
    }
	
}
