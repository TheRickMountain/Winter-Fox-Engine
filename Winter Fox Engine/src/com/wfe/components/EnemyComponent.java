package com.wfe.components;

import com.wfe.core.Game;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.physics.AABB;

public class EnemyComponent extends Component {

	private Transformation transform;
	private float speed = 0.5f;
	
	private ColliderComponent cc;
	private AABB aabb;
	
	@Override
	public void init() {
		cc = (ColliderComponent) getParent().getComponent(ComponentType.COLLIDER);
		aabb = cc.getAABB();
		transform = getParent().getTransform();
	}

	@Override
	public void update(float dt) {
		transform.x += Math.signum(Game.player.getTransform().x - transform.x) * dt * speed;
		transform.z += Math.signum(Game.player.getTransform().z - transform.z) * dt * speed;
		
		aabb.set(transform.x - cc.sizeX / 2, transform.y, transform.z - cc.sizeZ / 2, 
		transform.x + cc.sizeX / 2, transform.y + cc.sizeY, transform.z + cc.sizeZ / 2);
	}

	@Override
	public ComponentType getType() {
		return ComponentType.ENEMY;
	}

	@Override
	public Component getInstance() {
		return new EnemyComponent();
	}

}
