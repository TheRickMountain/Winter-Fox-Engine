package com.wfe.gui.questSystem;

import java.util.List;

public class Quest {

	private List<IQuestObjective> objectives;
	private IQuestInfo info;
	
	public Quest(IQuestInfo info, List<IQuestObjective> objectives) {
		this.info = info;
		this.objectives = objectives;
	}
	
	public boolean isComplete() {
		for(int i = 0; i < objectives.size(); i++) {
			if(!objectives.get(i).IsComplete() && !objectives.get(i).IsBonus()) {
				return false;
			}
		}
		
		return true;
	}
	
	public IQuestInfo getInfo() {
		return info;
	}
	
}
