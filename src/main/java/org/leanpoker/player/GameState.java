package org.leanpoker.player;

public class GameState {
	int mimimumRaise;
	int current_buy_in;
	
	public MyPlayer eigenerSpieler;
	
	public static class MyPlayer{
		
		public int stack;
		
		public static class Card{
			public String rank;
			public int value;
		}
		public Card hole_card1;
		public Card hole_card2;
	}
}
