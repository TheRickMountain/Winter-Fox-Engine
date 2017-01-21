package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Arrow;
import com.wfe.entities.Player;
import com.wfe.game.Game;
import com.wfe.utils.MathUtils;

public class ArrowComponent implements Component {

	private final StaticEntity target;
	private final Arrow arrow;
	
	private float speed = 50f;
	
	public ArrowComponent(Player player, StaticEntity target, Arrow arrow) {
		this.target = target;
		this.arrow = arrow;
	}
	
	@Override
	public void update(float dt) {
		Transformation transform = arrow.getTransform();
		
		float directionX = (float)Math.sin(Math.toRadians(-transform.getRotY() + 90)) * -1.0f * speed * dt;
		float directionZ = (float)Math.cos(Math.toRadians(-transform.getRotY() + 90)) * speed * dt;
		
		transform.setX(transform.getX() + directionX);
		transform.setZ(transform.getZ() + directionZ);
		
		if(MathUtils.getDistance(transform.getX(), transform.getZ(), 
				target.getTransform().getX(), target.getTransform().getZ()) <= 0.5f) {
			arrow.remove();
			Game.pickedEntity = null;
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.ARROW;
	}

}
