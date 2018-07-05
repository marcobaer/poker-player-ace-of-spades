package org.leanpoker.player;

public class GameState {
	int mimimumRaise;
	
	public MyPlayer eigenerSpieler;
	
	public static class MyPlayer{
		
		public static class Card{
			public String rank;
			public int value;
		}
		public Card hole_card1;
		public Card hole_card2;
	}
}
