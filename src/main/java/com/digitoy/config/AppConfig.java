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

        List<Tile> tiles = tileService.initializeTiles(); // initialize tiles.

        tileService.shuffleTiles(tiles); // shuffle tiles.

        Tile pointer = tileService.choosePointer(tiles); // choose pointer.

        tileService.chooseJoker(tiles, pointer); // choose joker.

        List<Player> players = playerService.initializePlayers(4); // initialize players.

        playerService.distributeTiles(tiles, players); // distribute tiles to each player.

        tileService.printPointerAndJoker(tiles); // print pointer and joker to the console.

        List<Player> winners = playerService.comparePlayersScore(players); // determine winners with their left tiles.

        playerService.printWinners(winners); // print winners.

    }
}
