package com.digitoy.service;

import com.digitoy.model.Tile;

import java.util.List;

public interface TileService {

    List<Tile> initializeTiles();

    void shuffleTiles(List<Tile> tiles);

    Tile choosePointer(List<Tile> tiles);

    void chooseJoker(List<Tile> tiles, Tile pointer);

    void printPointerAndJoker(List<Tile> tiles);

}
