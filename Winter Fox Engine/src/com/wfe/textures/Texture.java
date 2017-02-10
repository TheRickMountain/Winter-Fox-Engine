package com.wfe.textures;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import com.wfe.utils.MyFile;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {
	
	private int id;
	private int width;
	private int height;
	
	public Texture(int textureID, int size) {
		this.id = textureID;
		this.width = size;
		this.height = size;
	}
	
	public Texture(InputStream is) {
		try {
			 // Load Texture file
	        PNGDecoder decoder = new PNGDecoder(is);

	        this.width = decoder.getWidth();
	        this.height = decoder.getHeight();

	        // Load texture contents into a byte buffer
	        ByteBuffer buf = ByteBuffer.allocateDirect(
	                4 * decoder.getWidth() * decoder.getHeight());
	        decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
	        buf.flip();

	        // Create a new OpenGL texture 
	        this.id = glGenTextures();
	        // Bind the texture
	        glBindTexture(GL_TEXTURE_2D, this.id);

	        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
	        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	        // Upload the texture data
	        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
	        // Generate Mip Map
	        glGenerateMipmap(GL_TEXTURE_2D);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bind(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void delete() {
		glDeleteTextures(id);
	}

	public static TextureBuilder newTexture(MyFile textureFile) {
		return new TextureBuilder(textureFile);
	}
	
	public int getID() {
		return id;
	}
	
	public int getSize() {
		return width;
	}

}
