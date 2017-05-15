package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.MyRandom;
import com.wfe.utils.Rect;
import com.wfe.utils.TimeUtil;

public class Mancala {
	
	private enum State {
		GAME,
		WIN,
		LOSE
	}
	
	private enum Turn {
		PLAYER(0), AI(1);
		
		private final int value;
		private Turn(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private Rect rect;
	private Texture texture;
	
	private List<Slot> slots = new ArrayList<>();
	
	private Turn turn = Turn.PLAYER;
	private State state = State.GAME;
	private int[] cups;
	
	private TimeUtil timer;
	
	public Mancala() {
		rect = new Rect(0, 0, 20 + Slot.SIZE * 8 + 7 * 5, 20 + Slot.SIZE * 3);
		texture = ResourceManager.getTexture("mancala_ui");
		
		cups = new int[7 * 2];
		for(int i = 0; i < cups.length; i++) {
			cups[i] = 4;
		}
		cups[6] = cups[13] = 0;
		
		for(int i = 0; i < cups.length; i++) {
			slots.add(new Slot());
			
			for(int j = 0; j < cups[i]; j++) {
				slots.get(i).addGem(new GUITexture(ResourceManager.getTexture("gem_0_ui")));
			}
		}
		
		timer = new TimeUtil();
		
		updatePositions();
	}
	
	public void update(float dt) {
		switch(state) {
		case GAME:
			switch(turn) {
			case PLAYER:
				if(Mouse.isButtonDown(0)) {
					for(int i = 0; i < 6; i++) {
						Slot slot = slots.get(i);
						if(slot.rect.isMouseOvered()) {
							doMove(turn.getValue(), i);
							updateSlots();
							break;
						}
					}
				}
				break;
			case AI:
				if(timer.getTime() >= 2) {
					doMove(turn.getValue(), aiMove());
					updateSlots();
				
					timer.reset();
				}
				break;
			}
			break;
		case WIN:
			System.out.println("You win!");
			break;
		case LOSE:
			System.out.println("You lose!");
			break;
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	private int aiMove() {
		int num = Turn.AI.getValue() * 7 + MyRandom.nextInt(6);
		
		if(cups[num] == 0) {
			num = aiMove();
		} 
		
		return num;
	}
	
	public void render() {
		GUIRenderer.render(texture, Color.WHITE, rect.x, rect.y, 0f, rect.width, rect.height, false);
		
		for(Slot slot : slots) {
			slot.render();
		}
	}
	
	public void renderText() {
		for(Slot slot : slots) {
			slot.renderText();
		}
	}

	private void updatePositions() {
		rect.setPosition(Display.getWidth() / 2 - rect.width / 2, 
				Display.getHeight() / 2 - rect.height / 2);
		
		slots.get(6).setPosition(rect.x + rect.width - 10 - Slot.SIZE, rect.y + rect.height - 10 - Slot.SIZE);
		slots.get(13).setPosition(rect.x + 10, rect.y + 10);
		
		for(int i = 0; i < 6; i++) {
			Slot slot = slots.get(i);
			slot.setPosition(rect.x + 10 + 60 + 5 + (65 * i), rect.y + 10 + 120);
		}
		
		int count = 0; 
		for(int i = 12; i >= 7; i--) {
			Slot slot = slots.get(i);
			slot.setPosition(rect.x + 10 + 60 + 5 + (65 * count), rect.y + 10);
			count++;
		}
	}
	
	private void doMove(int player, int cup) {
		int amount = count(cup);
		int  i = 0;
		for(i = cup + 1; i < cup + amount + 1; i++) {
			
			if(player == 0) {
				if(i == 13) {
					amount++;
					continue;
				}
			} else {
				if(i == 6) {
					amount++;
					continue;
				}
			}
			
			addToCup(i % 14);
		}
		
		i = cup + amount;
		
		if(player == 0) {
			if(i == 6) {
				turn = Turn.PLAYER;
				System.out.println("Player turn");
			} else {
				System.out.println("Ai turn");
				turn = Turn.AI;
			}
		} else {
			if(i == 13) {
				turn = Turn.AI;
				System.out.println("AI turn");
			} else {
				System.out.println("Player turn");
				turn = Turn.PLAYER;
			}
		}
		
		clearCup(cup);
		
		checkWinner();
	}
	
	private void checkWinner() {
		boolean player = true;
		for(int i = 0; i < 6; i++) {
			if(cups[i] > 0) {
				player = false;
				break;
			}
		}
		
		boolean ai = true;
		for(int i = 7; i < 13; i++) {
			if(cups[i] > 0) {
				ai = false;
				break;
			}
		}
		
		if(player || ai) {
			if(cups[6] > cups[13]) {
				state = State.WIN;
			} else {
				state = State.LOSE;
			}
		}
	}
	
	private void addToCup(int index) {
		cups[index]++;
	}
	
	private void clearCup(int index) {
		cups[index] = 0;
	}
	
	private int count(int index) {
		return cups[index];
	}
	
	private void updateSlots() {
		for(int i = 0; i < cups.length; i++) {
			
			slots.get(i).clear();
			
			for(int j = 0; j < cups[i]; j++) {
				slots.get(i).addGem(new GUITexture(ResourceManager.getTexture("gem_0_ui")));
			}
		}
	}
	
	private class Slot {
		
		private static final int SIZE = 60;
		
		public Rect rect;
		
		private GUIText text;
		public int amount;
		
		public List<GUITexture> gems = new ArrayList<>();
		
		public Slot() {
			rect = new Rect(0, 0, SIZE, SIZE);
			text = new GUIText("", FontRenderer.ARIAL);
			amount = 0;
		}
		
		public void render() {
			GUIRenderer.render(gems);
		}

		public void renderText() {
			FontRenderer.render(text);
		}
		
		public void setPosition(float x, float y) {
			rect.setPosition(x, y);
			
			for(GUITexture gem : gems) {
				gem.rect.setPosition(x, y);
			}
			
			text.setPosition(x + SIZE / 2, y + SIZE);
		}
		
		public void addGem(GUITexture gem) {
			gem.rect.setPosition(rect.x, rect.y);
			gem.rect.setSize(25, 25);
			gems.add(gem);
			amount = gems.size();
			
			text.setText(String.valueOf(amount));
		}
		
		public void clear() {
			gems.clear();
			amount = 0;
			text.setText("");
		}
		
	}
	
}
