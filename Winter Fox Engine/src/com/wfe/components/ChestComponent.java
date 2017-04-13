package com.wfe.components;

import java.util.ArrayList;
import java.util.List;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.Slot;
import com.wfe.utils.Rect;

public class ChestComponent extends Component {

	private int columns;
	private int rows;
	
	private List<Slot> slots;
	
	public ChestComponent(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}
	
	@Override
	public void init() {
		slots = new ArrayList<>(columns * rows);
		for(int i = 0, n = columns * rows; i < n; i++) {
			slots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
		}
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
	
	public List<Slot> getSlots() {
		return slots;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CHEST;
	}

	@Override
	public Component getInstance() {
		return new ChestComponent(columns, rows);
	}

}
