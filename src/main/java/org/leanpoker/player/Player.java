package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.Map;

public class Player {

    static final String VERSION = "v1.3";

    public static int betRequest(JsonElement request) {
    	GameState gameState;
		try {
			System.out.println(request.toString());
			gameState = parseGameState(request);
		} catch (Exception e) {
			return 200;
		}
        return gameState.mimimumRaise;
    }
    
    static GameState parseGameState(JsonElement request) throws Exception {
    	JsonElement min_raise = request.getAsJsonObject().get("minimum_raise");
    	int min_raise_int = min_raise.getAsInt();
    	GameState gameState = new GameState();
    	gameState.mimimumRaise = min_raise_int;
    	return gameState;
    }

    public static void showdown(JsonElement game) {
    }
}
