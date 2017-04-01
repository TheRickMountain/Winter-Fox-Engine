package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.physics.AABB;

public class ColliderComponent extends Component {

	private float sizeX;
	private float sizeY;
	private float sizeZ;
	private Transformation transform;
	
	private AABB aabb;
	
	public ColliderComponent(float sizeX, float sizeY, float sizeZ, Transformation transform) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.transform = transform;
		this.aabb = new AABB(transform.x - sizeX / 2, transform.y, transform.z - sizeZ / 2, 
				transform.x + sizeX / 2, transform.y + sizeY, transform.z + sizeZ / 2);
	}


	@Override
	public void update(float dt) {
		
	}

	public AABB getAABB() {
		return aabb;
	}

	@Override
	public ColliderComponent getInstance() {
		return new ColliderComponent(sizeX, sizeY, sizeZ, new Transformation(transform));
	}

	@Override
	public ComponentType getType() {
		return ComponentType.COLLIDER;
	}
	
}
