package com.digitoy.service;

import com.digitoy.model.Tile;

import java.util.List;

public interface RackService {

    void sortRack(List<Tile> rack);

    List<List<Tile>> pairRack(List<Tile> rack);

    List<List<Tile>> runs(List<Tile> rack);

    List<List<Tile>> sets(List<Tile> rack);

    Tile nextRunTile(List<Tile> rack, Tile tile);

    List<List<Tile>> runsAndSetsRack(List<Tile> rack);

    List<List<Tile>> finalRack(List<Tile> rack);

}
