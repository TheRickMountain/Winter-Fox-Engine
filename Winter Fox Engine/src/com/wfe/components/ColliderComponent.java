package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.physics.AABB;

public class ColliderComponent extends Component {

	public float sizeX, sizeY, sizeZ;
	
	private AABB aabb;
	
	private Transformation transform;
	
	public ColliderComponent(float sizeX, float sizeY, float sizeZ) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}
	
	@Override
	public void init() {
		transform = getParent().getTransform();
		aabb = new AABB(transform.x - sizeX / 2, transform.y, transform.z - sizeZ / 2, 
				transform.x + sizeX / 2, transform.y + sizeY, transform.z + sizeZ / 2);
	}

	@Override
	public void update(float dt) {
		
	}

	public AABB getAABB() {
		return aabb;
	}


	@Override
	public ComponentType getType() {
		return ComponentType.COLLIDER;
	}


	@Override
	public Component getInstance() {
		return new ColliderComponent(sizeX, sizeY, sizeZ);
	}
	
}
