package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.DialogueNode;

public class NpcComponent extends Component {

	public DialogueNode[] node;
	public int currentNode;
	
	public NpcComponent(DialogueNode[] node, int currentNode) {
		this.node = node;
		this.currentNode = currentNode;
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public ComponentType getType() {
		return ComponentType.NPC;
	}

	@Override
	public Component getInstance() {
		return null;
	}

}
