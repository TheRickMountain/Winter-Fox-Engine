package com.wfe.gui.questSystem;

public interface IQuestObjective {

	String getTitle();
	String getDescription();
	boolean IsComplete();
	boolean IsBonus();
	void updateProgress();
	void checkProgress();
	
	
}
