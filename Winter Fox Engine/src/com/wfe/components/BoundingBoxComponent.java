package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.math.Vector3f;
import com.wfe.physics.AABB;
import com.wfe.utils.MousePicker;

public class BoundingBoxComponent extends Component {

	private AABB aabb;
	private float yOffset, sizeX, sizeY, sizeZ;
	private Transformation transform;
	
	public BoundingBoxComponent(float yOffset, float sizeX, float sizeY, float sizeZ, Transformation transform) {
		this.yOffset = yOffset;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.transform = transform;
		this.aabb = new AABB(transform.x - sizeX / 2, transform.y + yOffset, transform.z - sizeZ / 2, 
				transform.x + sizeX / 2, transform.y + sizeY + yOffset, transform.z + sizeZ / 2);
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update(float dt) {
		this.aabb.set(transform.x - sizeX / 2, transform.y + yOffset, transform.z - sizeZ / 2, 
				transform.x + sizeX / 2, transform.y + sizeY + yOffset, transform.z + sizeZ / 2);
	}
	
	public boolean intersects() {
		float tmin, tmax, tymin, tymax, tzmin, tzmax;
		Vector3f rayOrigin = MousePicker.getRayOrigin();
		Vector3f rayDirection = MousePicker.getCurrentRay();
		
		if (rayDirection.x >= 0) {
			tmin = (aabb.x0 - rayOrigin.x) / rayDirection.x;
			tmax = (aabb.x1 - rayOrigin.x) / rayDirection.x;
		} else {
			tmin = (aabb.x1 - rayOrigin.x) / rayDirection.x;
			tmax = (aabb.x0 - rayOrigin.x) / rayDirection.x;
		}
		
		if (rayDirection.y >= 0) {
			tymin = (aabb.y0 - rayOrigin.y) / rayDirection.y;
			tymax = (aabb.y1 - rayOrigin.y) / rayDirection.y;
		} else {
			tymin = (aabb.y1 - rayOrigin.y) / rayDirection.y;
			tymax = (aabb.y0 - rayOrigin.y) / rayDirection.y;
		}
		
		if ( (tmin > tymax) || (tymin > tmax) )
			return false;
			
		if (tymin > tmin)
			tmin = tymin;
		
		if (tymax < tmax)
			tmax = tymax;
		
		if (rayDirection.z >= 0) {
			tzmin = (aabb.z0 - rayOrigin.z) / rayDirection.z;
			tzmax = (aabb.z1 - rayOrigin.z) / rayDirection.z;
		} else {
			tzmin = (aabb.z1 - rayOrigin.z) / rayDirection.z;
			tzmax = (aabb.z0 - rayOrigin.z) / rayDirection.z;
		}
			
		if ( (tmin > tzmax) || (tzmin > tmax) )
			return false;
			
		if (tzmin > tmin)
			tmin = tzmin;
			
		if (tzmax < tmax)
			tmax = tzmax;
			
		return ( (tmin < 1000) && (tmax > 1) );
	}

	@Override
	public ComponentType getType() {
		return ComponentType.BOUNDING_BOX;
	}

	@Override
	public Component getInstance() {
		return null;
	}

}
