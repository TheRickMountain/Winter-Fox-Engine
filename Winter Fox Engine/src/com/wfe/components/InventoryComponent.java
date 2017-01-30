package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.GUIFrame;
import com.wfe.gui.GUIManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.utils.Rect;

public class InventoryComponent extends Component {
	
	private GUIFrame frame;
	
	private boolean show;
	
	public InventoryComponent() {
		frame = new GUIFrame(new Rect(0, 0, 200, 400));
	}
	
	@Override
	public void update(float dt) {
		if(Keyboard.isKeyDown(Key.KEY_TAB)) {
			show = !show;
			if(show) {
				GUIManager.interfaces.addAll(frame.getFrameTextures());
			} else {
				GUIManager.interfaces.removeAll(frame.getFrameTextures());
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.INVENTORY;
	}

}
