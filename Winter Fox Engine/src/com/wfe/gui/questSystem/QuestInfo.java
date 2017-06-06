package com.wfe.gui.questSystem;

public class QuestInfo implements IQuestInfo {

	private String title;
	private String descriptionSummary;
	private String hint;
	
	public QuestInfo(String title, String descriptionSummary, String hint) {
		this.title = title;
		this.descriptionSummary = descriptionSummary;
		this.hint = hint;
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescriptionSummary() {
		return descriptionSummary;
	}

	@Override
	public String getHint() {
		return hint;
	}

}
