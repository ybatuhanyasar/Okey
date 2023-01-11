package com.digitoy.config;

import com.digitoy.model.Player;
import com.digitoy.model.Tile;
import com.digitoy.service.PlayerService;
import com.digitoy.service.TileService;
import com.digitoy.service.impl.PlayerServiceImpl;
import com.digitoy.service.impl.TileServiceImpl;

import java.util.List;

public class AppConfig {

    public static void start() {

        TileService tileService = new TileServiceImpl();

        PlayerService playerService = new PlayerServiceImpl();

        List<Tile> tiles = tileService.initializeTiles();

        tileService.shuffleTiles(tiles);

        Tile pointer = tileService.choosePointer(tiles);

        tileService.chooseJoker(tiles, pointer);

        List<Player> players = playerService.initializePlayers(4);

        playerService.distributeTiles(tiles, players);

        tileService.printPointerAndJoker(tiles);

        List<Player> winners = playerService.comparePlayersScore(players);

        playerService.printWinners(winners);

    }
}
