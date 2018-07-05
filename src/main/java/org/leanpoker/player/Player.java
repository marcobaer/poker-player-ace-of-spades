package org.leanpoker.player;

import org.leanpoker.player.GameState.MyPlayer;
import org.leanpoker.player.GameState.MyPlayer.Card;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "v2.1";

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
    	try {
    		if (eigenerSpieler.hole_card1.rank.equals("A") ||
    				eigenerSpieler.hole_card2.rank.equals("A")) {
    			return gameState.mimimumRaise * 3;
    		}
	    	if (Integer.parseInt(eigenerSpieler.hole_card1.rank) < 10 ||
	    			Integer.parseInt(eigenerSpieler.hole_card2.rank) < 10) {
	    		return 0;
	    	}
    	} catch (NumberFormatException e){
    		return gameState.mimimumRaise;
    	}
		return 0;
	}

	static GameState parseGameState(JsonElement request) throws Exception {
    	JsonElement min_raise = request.getAsJsonObject().get("minimum_raise");
    	int min_raise_int = min_raise.getAsInt();
    	GameState gameState = new GameState();
    	gameState.mimimumRaise = min_raise_int;
    	
    	JsonElement players = request.getAsJsonObject().get("players");
    	JsonArray playersArray = players.getAsJsonArray();
    	for (JsonElement playerJason : playersArray) {
			if (playerJason.getAsJsonObject().get("name").getAsString().equals("Ace of Spades")) {
				MyPlayer myPlayer = new MyPlayer();
				JsonArray hole_cards_json = playerJason.getAsJsonObject().get("hole_cards").getAsJsonArray();
				JsonElement wholeCardJson = hole_cards_json.get(0);
				String rank = wholeCardJson.getAsJsonObject().get("rank").getAsString();
				Card card = new Card();
				card.rank = rank;
				myPlayer.hole_card1 = card;
				wholeCardJson = hole_cards_json.get(1);
				rank = wholeCardJson.getAsJsonObject().get("rank").getAsString();
				Card card2 = new Card();
				card2.rank = rank;
				myPlayer.hole_card2 = card;
				gameState.eigenerSpieler = myPlayer;
				break;
			}
		}
    	return gameState;
    }

    public static void showdown(JsonElement game) {
    }
}
