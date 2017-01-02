package com.wfe.ecs;

public class Transformation {

	private StaticEntity parent;
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float rotX = 0;
	public float rotY = 0;
	public float rotZ = 0;
	public float scaleX = 1;
	public float scaleY = 1;
	public float scaleZ = 1;
	public boolean isMoving;
	
	public Transformation(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scaleX = scale;
		this.scaleY = scale;
		this.scaleZ = scale;
	}

	public Transformation(float x, float y, float z, float rotX, float rotY, float rotZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public Transformation(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Transformation(Transformation transform) {
		this.set(transform);
	}

	public void set(Transformation transform) {
		this.x = transform.x;
		this.y = transform.y;
		this.z = transform.z;
		this.rotX = transform.rotX;
		this.rotY = transform.rotY;
		this.rotZ = transform.rotZ;
		this.scaleX = transform.scaleX;
		this.scaleY = transform.scaleY;
		this.scaleZ = transform.scaleZ;
	}
	
	protected void setParent(StaticEntity parent) {
		this.parent = parent;
	}

	public StaticEntity getParent() {
		return parent;
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public void setPosition(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setRotation(float rotX, float rotY, float rotZ){
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}
	
	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		this.scaleZ = scale;
	}
	
	public void setScale(float sX, float sY, float sZ){
		this.scaleX = sX;
		this.scaleY = sY;
		this.scaleZ = sZ;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public float getScaleX() {
		return scaleX;
	}
	
	public float getScaleY() {
		return scaleY;
	}
	
	public float getScaleZ() {
		return scaleZ;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	
	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
	}
	
}
