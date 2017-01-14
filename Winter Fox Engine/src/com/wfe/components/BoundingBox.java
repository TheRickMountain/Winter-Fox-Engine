package com.wfe.components;

import com.wfe.ecs.Transformation;
import com.wfe.math.Vector3f;
import com.wfe.physics.AABB;
import com.wfe.utils.MousePicker;

public class BoundingBox {

	private AABB aabb;
	
	public BoundingBox(float sizeX, float sizeY, float sizeZ, Transformation transform) {
		this.aabb = new AABB(transform.x - sizeX / 2, transform.y, transform.z - sizeZ / 2, 
				transform.x + sizeX / 2, transform.y + sizeY, transform.z + sizeZ / 2);
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

}
