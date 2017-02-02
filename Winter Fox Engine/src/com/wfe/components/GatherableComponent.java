package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.entities.Player;
import com.wfe.game.Game;
import com.wfe.input.Mouse;
import com.wfe.utils.BoundingBox;
import com.wfe.utils.MathUtils;

public class GatherableComponent extends Component {

	private BoundingBox boundingBox;
	private int givenItem;
	
	public GatherableComponent(int givenItem, BoundingBox boundingBox) {
		this.givenItem = givenItem;
		this.boundingBox = boundingBox;
	}
	
	@Override
	public void update(float dt) {
		if(Mouse.isButtonDown(1)) {
			Player player = Game.player;
			if(MathUtils.getDistance(player.getTransform().x, player.getTransform().z, 
					getParent().getTransform().x, getParent().getTransform().z) <= 2.5f) {
				if(boundingBox.intersects()) {
					InventoryComponent inv = (InventoryComponent) player.getComponent(ComponentType.INVENTORY);
					inv.addItem(givenItem, 1);
					getParent().remove();
				}
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
