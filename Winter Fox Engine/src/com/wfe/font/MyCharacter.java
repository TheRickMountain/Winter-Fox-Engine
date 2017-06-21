package com.wfe.font;

public class MyCharacter {

	private int id;
	private double x, y;
	private double width, height;
	private double xOffset, yOffset;
	private double xAdvance;

	protected MyCharacter(int id, double x, double y, double width, double height,
            double xOffset, double yOffset, double xAdvance) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
    }

	public int getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public double getxAdvance() {
		return xAdvance;
	}
	
}
