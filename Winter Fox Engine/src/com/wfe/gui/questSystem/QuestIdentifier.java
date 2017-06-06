package com.wfe.gui.questSystem;

public class QuestIdentifier implements IQuestIdentifier {

	private int id;
	private int sourceID;
	private int chainQuestID;
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public int getSourceID() {
		return sourceID;
	}

	@Override
	public int getChainQuestID() {
		return chainQuestID;
	}

	
}
