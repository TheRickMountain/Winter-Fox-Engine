package com.wfe.graph;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.utils.OpenglUtils;

public class Mesh {
	
	private int VAO, EBO;
	private int VBO;
	private int[] indices;
	private int vertexCount;
	
	public Mesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
		this.indices = indices;
		
		int vertexByteCount = 4 * (3 + 2 + 3);
		
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, OpenglUtils.toFloatBuffer(vertices, texCoords, normals), GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, 4 * 3);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, vertexByteCount, 4 * (3 + 2));
		
		EBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, OpenglUtils.toIntBuffer(indices), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public Mesh(float[] vertices, float[] texCoords) {
		int vertexByteCount = 4 * (2 + 2);
		
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, OpenglUtils.toFloatBuffer(vertices, texCoords), GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, 4 * 2);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public Mesh(float[] data, int dimension) {
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);

        vertexCount = data.length / dimension;

        VBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, OpenglUtils.toFloatBuffer(data), GL15.GL_STATIC_DRAW);

        int vertexByteCount = 4 * dimension;

        GL20.glVertexAttribPointer(0, dimension, GL11.GL_FLOAT, false, vertexByteCount, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
	}
	
	public int getVAO() {
		return VAO;
	}
	
	public int getIndicesLength() {
		return indices.length;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public void delete() {
		GL15.glDeleteBuffers(VBO);
		GL15.glDeleteBuffers(EBO);
		GL30.glDeleteVertexArrays(VAO);
	}

}
