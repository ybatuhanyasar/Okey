package com.digitoy.service.impl;

import com.digitoy.model.Tile;
import com.digitoy.service.TileService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TileServiceImpl implements TileService {

    private static final Random random = new Random();

    @Override
    public List<Tile> initializeTiles() {

        Tile.Color[] colors = Tile.Color.values();

        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (Tile.Color color : colors) {
                if (color == Tile.Color.FAKE_JOKER) {
                    continue;
                }
                for (int j = 1; j <= 13; j++) {
                    Tile tile = new Tile(j, color);
                    tiles.add(tile);
                }
            }
            Tile tile = new Tile(0, Tile.Color.FAKE_JOKER);
            tiles.add(tile);
        }

        return tiles;
    }

    @Override
    public void shuffleTiles(List<Tile> tiles) {
        Collections.shuffle(tiles);
    }

    @Override
    public Tile choosePointer(List<Tile> tiles) {

        Tile pointer;

        while (true) {
            int pointerIndex = random.nextInt(106);

            if (tiles.get(pointerIndex).getColor() != Tile.Color.FAKE_JOKER) {

                pointer = tiles.get(pointerIndex);
                pointer.setPointer(true);
                for (Tile tile : tiles) {

                    if (tile.getColor() == pointer.getColor() && tile.getNumber() == pointer.getNumber()) {
                        tile.setPointer(true);
                    }
                }

                tiles.remove(pointer);

                break;
            }
        }
        return pointer;
    }

    @Override
    public void chooseJoker(List<Tile> tiles, Tile pointer) {

        for (Tile tile : tiles) {
            if (pointer.getNumber() != 13 && tile.getColor() == pointer.getColor() && tile.getNumber() == pointer.getNumber() + 1) {
                tile.setJoker(true);
            }
            if (pointer.getNumber() == 13 && tile.getColor() == pointer.getColor() && tile.getNumber() == 1) {
                tile.setJoker(true);
            }
            if (tile.getColor() == Tile.Color.FAKE_JOKER) {
                tile.setNumber(pointer.getNumber() + 1);
                tile.setColor(pointer.getColor());
            }
        }
    }

    @Override
    public void printPointerAndJoker(List<Tile> tiles) {
        Tile pointer = tiles.stream().filter(Tile::isPointer).findFirst().orElse(null);
        Tile joker = tiles.stream().filter(Tile::isJoker).findFirst().orElse(null);

        if (pointer != null && joker != null) {
            System.out.println("Pointer is " + pointer.getNumber() + "" + pointer.getColor());

            System.out.println("Joker is " + joker.getNumber() + "" + joker.getColor());
        }

        System.out.println();
    }
}
