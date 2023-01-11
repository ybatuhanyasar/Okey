package com.digitoy.service;

import com.digitoy.model.Player;
import com.digitoy.model.Tile;

import java.util.List;

public interface PlayerService {

    List<Player> initializePlayers(int playerCount);

    void distributeTiles(List<Tile> tiles, List<Player> players);

    //This function is find winner player according to their left irrelevant tiles.
    List<Player> comparePlayersScore(List<Player> players);

    void printWinners(List<Player> players);

}
