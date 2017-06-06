package com.wfe.gui.questSystem;

public class LocationObjective implements IQuestObjective {

	private String title;
	private String description;
	private boolean isComplete;
	private boolean isBonus;
	
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
		
	}

}
