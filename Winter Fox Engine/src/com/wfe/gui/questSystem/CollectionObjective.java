package com.wfe.gui.questSystem;

import com.wfe.gui.Item;

public class CollectionObjective implements IQuestObjective {

	private String title;
	private String description;
	private boolean isComplete;
	private boolean isBonus;
	private String verb;
	private int collectionAmount;
	private int currentAmount;
	private Item itemToCollect;
	
	public CollectionObjective(String titleVerb, int totalAmount, Item item, String descrip, boolean bonus) {
		title = titleVerb + " " + totalAmount + " " + item.name;
		verb = titleVerb;
		description = descrip;
		itemToCollect = item;
		collectionAmount = totalAmount;
		currentAmount = 0;
		isBonus = bonus;
		checkProgress();
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public boolean IsComplete() {
		return isComplete;
	}

	@Override
	public boolean IsBonus() {
		return isBonus;
	}

	@Override
	public void updateProgress() {
		
	}

	@Override
	public void checkProgress() {
		if(currentAmount >= collectionAmount) {
			isComplete = true;
		} else {
			isComplete = false;
		}
	}
	
	public int getCollectionAmount() {
		return collectionAmount;
	}

	public int getCurrentAmount() {
		return currentAmount;
	}
	
	public Item getItemToCollect() {
		return itemToCollect;
	}
	
	@Override
	public String toString() {
		return currentAmount + "/" + collectionAmount + " " + itemToCollect.name + " " + verb + "ed!";
	}

}
