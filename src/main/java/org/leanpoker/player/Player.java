package org.leanpoker.player;

import org.leanpoker.player.GameState.MyPlayer;
import org.leanpoker.player.GameState.MyPlayer.Card;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "v2.7";

    public static int betRequest(JsonElement request) {
    	GameState gameState;
		try {
			gameState = parseGameState(request);
			return getBetAmount(gameState);
		} catch (Exception e) {
			return 200;
		}
    }
    
    static int getBetAmount(GameState gameState) {
    	MyPlayer eigenerSpieler = gameState.eigenerSpieler;
		if (eigenerSpieler.hole_card1.value == 200 &&
				eigenerSpieler.hole_card2.value == 200) {
			return eigenerSpieler.stack;
		}
		if (eigenerSpieler.hole_card1.value == 200 ||
				eigenerSpieler.hole_card2.value == 200) {
			return gameState.current_buy_in;
		}
		if (eigenerSpieler.hole_card1.rank.equals(eigenerSpieler.hole_card2.rank)
				&& eigenerSpieler.hole_card1.value >= 20) {
			return gameState.current_buy_in + gameState.mimimumRaise * 3;
		}
    	if (eigenerSpieler.hole_card1.value < 20 ||
    			eigenerSpieler.hole_card2.value < 20) {
    		return 0;
    	}
		return gameState.current_buy_in;
	}

	static GameState parseGameState(JsonElement request) throws Exception {
    	JsonElement min_raise = request.getAsJsonObject().get("minimum_raise");
    	int min_raise_int = min_raise.getAsInt();
    	JsonElement current_buy_in = request.getAsJsonObject().get("current_buy_in");
    	GameState gameState = new GameState();
    	gameState.mimimumRaise = min_raise_int;
    	gameState.current_buy_in = current_buy_in.getAsInt();
    	JsonElement players = request.getAsJsonObject().get("players");
    	JsonArray playersArray = players.getAsJsonArray();
    	for (JsonElement playerJason : playersArray) {
			if (playerJason.getAsJsonObject().get("name").getAsString().equals("Ace of Spades")) {
				MyPlayer myPlayer = new MyPlayer();
				myPlayer.stack = playerJason.getAsJsonObject().get("stack").getAsInt();
				JsonArray hole_cards_json = playerJason.getAsJsonObject().get("hole_cards").getAsJsonArray();
				Card card1 = parseCard(hole_cards_json.get(0));
				Card card2 = parseCard(hole_cards_json.get(1));
				myPlayer.hole_card1 = card1;
				myPlayer.hole_card2 = card2;
				gameState.eigenerSpieler = myPlayer;
				break;
			}
		}
    	return gameState;
    }

	private static Card parseCard(JsonElement wholeCardJson) {
		String rank = wholeCardJson.getAsJsonObject().get("rank").getAsString();
		Card card = new Card();
		card.rank = rank;
		card.value = calculateValue(rank);
		return card;
	}

    private static int calculateValue(String rank) {
    	switch(rank) {
    	case "2":
    	case "3":
    	case "4":
    	case "5":
    	case "6":
    	case "7":
    	case "8":
    	case "9":
    		return 10;
    	case "10":
    		return 20;
    	case "J":
    	case "Q":
    		return 50;
    	case "K":
    		return 100;
    	case "A":
    		return 200;
    	}
		return 0;
	}

	public static void showdown(JsonElement game) {
    }
}
