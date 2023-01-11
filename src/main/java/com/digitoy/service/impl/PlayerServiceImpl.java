package com.digitoy.service.impl;

import com.digitoy.model.Player;
import com.digitoy.model.Tile;
import com.digitoy.service.PlayerService;
import com.digitoy.service.RackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerServiceImpl implements PlayerService {

    private static final Random random = new Random();

    private static final RackService rackService = new RackServiceImpl();

    @Override
    public List<Player> initializePlayers(int playerCount) {

        if (playerCount > 4) {
            System.out.println("Player count cannot be greater than 4!!!");
            return null;
        } else {
            int starterIndex = random.nextInt(playerCount);

            List<Player> players = new ArrayList<>();

            for (int i = 0; i < playerCount; i++) {
                players.add(new Player());
            }

            players.get(starterIndex).setStarter(true);
            return players;
        }
    }

    @Override
    public void distributeTiles(List<Tile> tiles, List<Player> players) {

        int index = 0;

        for (Player player : players) {
            if (player.isStarter()) {
                player.setRack(tiles.subList(index, index + 14));
                index += 14;
            } else {
                player.setRack(tiles.subList(index, index + 13));
                index += 13;
            }
        }
    }

    @Override
    public List<Player> comparePlayersScore(List<Player> players) {
        List<Player> winners = new ArrayList<>();
        int irrelevantTiles = 15;

        for (Player player : players) {
            rackService.sortRack(player.getRack());
            List<List<Tile>> finalRack = rackService.finalRack(player.getRack());
            player.setFinalRack(finalRack);
            if (finalRack.get(finalRack.size() - 1).size() < irrelevantTiles) {
                irrelevantTiles = finalRack.get(finalRack.size() - 1).size();
                winners.clear();
                winners.add(player);
            } else if (finalRack.get(finalRack.size() - 1).size() == irrelevantTiles) {
                winners.add(player);
            }
        }

        return winners;
    }

    @Override
    public void printWinners(List<Player> players) {
        System.out.println("WINNERS:");
        System.out.println();
        for (Player player : players) {
            System.out.println("Player ID : " + player.getId());
            System.out.println("Final Rack :");
            for (List<Tile> stack : player.getFinalRack()) {
                System.out.print(" | ");
                for (Tile tile : stack) {
                    System.out.print(tile.getNumber() + "" + tile.getColor() + " ");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
