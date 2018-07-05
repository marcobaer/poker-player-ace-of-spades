package org.leanpoker.player;

import org.leanpoker.player.GameState.MyPlayer.Card;

public class GameState {
	int mimimumRaise;
	int current_buy_in;
	
	Card community1;
	Card community2;
	Card community3;
	
	public MyPlayer eigenerSpieler;
	
	public static class MyPlayer{
		
		public int stack;
		public int bet;
		
		public static class Card{
			public String rank;
			public int value;
		}
		public Card hole_card1;
		public Card hole_card2;
	}
}
