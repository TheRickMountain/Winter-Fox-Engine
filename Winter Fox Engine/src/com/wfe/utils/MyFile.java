package com.wfe.utils;

import java.io.InputStream;

public class MyFile {
	
	private static final String FILE_SEPARATOR = "/";

	private String path;
	private String name;

	public MyFile(String path) {
		this.path = FILE_SEPARATOR + path;
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	public InputStream getInputStream() {
		return Class.class.getResourceAsStream(path);
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}
	
}
