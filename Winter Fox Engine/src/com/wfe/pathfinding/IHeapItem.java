package com.wfe.pathfinding;

public interface IHeapItem<T> extends Comparable<T> {

	public abstract int getHeapIndex();
	public abstract void setHeapIndex(int heapIndex);
	
}
