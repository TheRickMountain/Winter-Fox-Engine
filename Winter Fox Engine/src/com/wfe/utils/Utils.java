package com.wfe.utils;

import java.util.ArrayList;

public class Utils {

	public static String [] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }
	
	 public static float[] joinArrays(float[] vertices, float[] uvs) {
	        float[] data = new float[vertices.length + uvs.length];
	        int count = 0;
	        int vertexCount = 0;
	        int uvsCount = 0;
	        for(int i = 0; i < data.length / 4; i++) {
	            data[count] = vertices[vertexCount];
	            vertexCount++;
	            count++;
	            data[count] = vertices[vertexCount];
	            vertexCount++;
	            count++;
	            data[count] = uvs[uvsCount];
	            uvsCount++;
	            count++;
	            data[count] = uvs[uvsCount];
	            uvsCount++;
	            count++;
	        }
	        return data;
	    }
	
}
