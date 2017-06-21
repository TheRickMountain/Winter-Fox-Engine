package com.wfe.font;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wfe.utils.MyFile;

public class MetaFile {

	private static final int PAD_TOP = 0;
	private static final int PAD_LEFT = 1;
	private static final int PAD_BOTTOM = 2;
	private static final int PAD_RIGHT = 3;
	
	private static final int DESIRED_PADDING = 3;
	
	private static final String SPLITTER = " ";
	private static final String NUMBER_SEPARATOR = ",";
	
	private int[] padding;
	private int paddingWidth;
	private int paddingHeight;
	
	private static double lineHeight;
	
	private BufferedReader reader;
	private Map<String, String> values = new HashMap<>();
	
	private static Map<Integer, MyCharacter> metaData = new HashMap<>();
	
	public MetaFile(MyFile file) {
		reader = file.getReader();
		loadPaddingData();
		loadLineSizes(512);
		loadCharacterData(512);
	}
	
	public static MyCharacter getCharacter(int ascii) {
		return metaData.get(ascii);
	}
	
	public static double getLineHeight() {
		return lineHeight;
	}
	
	protected void close() {
		
	}
	
	private void loadPaddingData() {
		processNextLine();
		padding = getValuesOfVariable("padding");
		paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
        paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
	}
	
	private void loadLineSizes(int imageSize) {
		processNextLine();
		lineHeight = (double) getValueOfVariable("lineHeight") / (double) imageSize;
	}
	
	private int getValueOfVariable(String name) {
		return Integer.parseInt(values.get(name));
	}
	
	private int[] getValuesOfVariable(String name) {
		String[] numbers = values.get(name).split(NUMBER_SEPARATOR);
		int[] actualValues = new int[numbers.length];
		for(int i = 0; i < actualValues.length; i++) {
			actualValues[i] = Integer.parseInt(numbers[i]);
		}
		return actualValues;
	}
	
	private boolean processNextLine() {
		values.clear();
		String line = null;
		try {
			line = reader.readLine();
		} catch(IOException e) {}
		
		if(line == null) {
			return false;
		}
		
		for(String part : line.split(SPLITTER)) {
			String[] valuePairs = part.split("=");
			if(valuePairs.length == 2) {
				values.put(valuePairs[0], valuePairs[1]);
			}
		}
		return true;
	}
	
	private void loadCharacterData(int imageWidth) {
		processNextLine();
		processNextLine();
		while(processNextLine()) {
			MyCharacter c = loadCharacter(imageWidth);
			if(c != null) {
				metaData.put(c.getId(), c);
			}
		}
	}
	
	private MyCharacter loadCharacter(int imageSize) {
		int id = getValueOfVariable("id");
		double xTex = ((double) getValueOfVariable("x") + (padding[PAD_LEFT] - DESIRED_PADDING)) / (double) imageSize;
		double yTex = ((double) getValueOfVariable("y") + (padding[PAD_TOP] - DESIRED_PADDING)) / (double) imageSize;
		int width = getValueOfVariable("width") - (paddingWidth - (2 * DESIRED_PADDING));
        int height = getValueOfVariable("height") - (paddingHeight - (2 * DESIRED_PADDING));
        double xTexSize = (double) width / (double) imageSize;
        double yTexSize = (double) height / (double) imageSize;
		double xOffset = (getValueOfVariable("xoffset") + padding[PAD_LEFT] - DESIRED_PADDING) / (double) imageSize;
		double yOffset = (getValueOfVariable("yoffset") + padding[PAD_TOP] - DESIRED_PADDING) / (double) imageSize;
		double xAdvance = (getValueOfVariable("xadvance") - paddingWidth) / (double) imageSize;
		return new MyCharacter(id, xTex, yTex, xTexSize, yTexSize, xOffset, yOffset, xAdvance);
	}
	
}
