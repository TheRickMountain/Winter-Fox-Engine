package com.wfe.ecs;

import com.wfe.math.Matrix4f;
import com.wfe.math.Vector4f;
import com.wfe.utils.MathUtils;

public class Transformation {

	private Entity parent;
	private Vector4f temp;
	private Matrix4f tempMatrix;
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float rotX = 0;
	public float rotY = 0;
	public float rotZ = 0;
	public float scaleX = 1;
	public float scaleY = 1;
	public float scaleZ = 1;
	
	public float localX = 0;
	public float localY = 0;
	public float localZ = 0;
	public float localRotX = 0;
	public float localRotY = 0;
	public float localRotZ = 0;
	public float localScaleX = 1;
	public float localScaleY = 1;
	public float localScaleZ = 1;
	
	public boolean isMoving;
	
	public Transformation() {
		this(0, 0, 0, 0, 0, 0, 1);
	}
	
	public Transformation(float x, float y, float z, float rotX, float rotY, float rotZ) {
		this(x, y, z, rotX, rotY, rotZ, 1);
	}
	
	public Transformation(float x, float y, float z) {
		this(x, y, z, 0, 0, 0, 1);
	}
	
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
		this.temp = new Vector4f();
		this.tempMatrix = new Matrix4f();
	}
	
	public Transformation(Transformation transform) {
		this.x = transform.x;
		this.y = transform.y;
		this.z = transform.z;
		this.rotX = transform.rotX;
		this.rotY = transform.rotY;
		this.rotZ = transform.rotZ;
		this.scaleX = transform.scaleX;
		this.scaleY = transform.scaleY;
		this.scaleZ = transform.scaleZ;
		
		this.localX = transform.localX;
		this.localY = transform.localY;
		this.localZ = transform.localZ;
		this.localRotX = transform.localRotX;
		this.localRotY = transform.localRotY;
		this.localRotZ = transform.localRotZ;
		this.localScaleX = transform.localScaleX;
		this.localScaleY = transform.localScaleY;
		this.localScaleZ = transform.localScaleZ;
		
		this.temp = new Vector4f();
		this.tempMatrix = new Matrix4f();
	}
	
	public void update(float dt) {
		Entity pp = parent.getParent();
		Matrix4f.transform(MathUtils.getEulerModelMatrix(tempMatrix, pp.transform), 
				new Vector4f(localX, localY, localZ, 1.0f), temp);
		x = temp.x;
		y = temp.y;
		z = temp.z;
		
		rotX = localRotX + pp.transform.rotX;
		rotY = localRotY + pp.transform.rotY;
		rotZ = localRotZ + pp.transform.rotZ;
		
		if(rotY < 0) {
			rotY += 360;
		}
		
		scaleX = localScaleX * pp.transform.scaleX;
		scaleY = localScaleY * pp.transform.scaleY;
		scaleZ = localScaleZ * pp.transform.scaleZ;
		
	}
	
	protected void setParent(Entity parent) {
		this.parent = parent;
	}

	public Entity getParent() {
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
