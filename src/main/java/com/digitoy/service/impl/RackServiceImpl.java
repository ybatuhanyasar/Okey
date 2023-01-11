package com.digitoy.service.impl;

import com.digitoy.model.Tile;
import com.digitoy.service.RackService;

import java.util.*;
import java.util.stream.Collectors;

/*
    This service implementation is about the rack utility.
    There are several methods and functions about sorting the rack according to rules and
    determine which one is the best solution.
 */
public class RackServiceImpl implements RackService {

    private static final List<Tile.Color> colors = Arrays.stream(Tile.Color.values()).filter(value -> value != Tile.Color.FAKE_JOKER).toList();

    @Override
    public void sortRack(List<Tile> rack) { // sorting the rack.
        rack.sort(Comparator.comparing(Tile::isJoker).thenComparing(Tile::getNumber).thenComparing(Tile::getColor));
    }

    @Override
    public List<List<Tile>> pairRack(List<Tile> rack) { // solve rack with pairs.

        List<List<Tile>> pairs = new ArrayList<>();
        List<Tile> irrelevant = new ArrayList<>();

        for (Tile tile : rack) {
            List<Tile> pair = new ArrayList<>(rack.stream().filter(tile1 -> tile1.getColor() == tile.getColor() && tile1.getNumber() == tile.getNumber() && !tile1.isJoker() && !tile.isJoker()).toList());
            if (pairs.contains(pair)) { // if there is already a pair jump to next tile.
                continue;
            }
            if (pair.size() > 1) { // if there is a match pair size must be greater than 1
                pairs.add(pair);
            } else { //if there are none match then look for joker if exist pair the current tile with joker.
                Tile joker = rack.stream().filter(Tile::isJoker).findFirst().orElse(null);
                if (joker != null && pairs.stream().noneMatch(list -> list.contains(joker))) {
                    pair.add(joker);
                    pairs.add(pair);
                } else {
                    irrelevant.add(tile);
                }
            }
        }
        pairs.add(irrelevant);

        return pairs;
    }

    @Override
    public List<List<Tile>> runs(List<Tile> rack) {

        List<List<Tile>> runs = new ArrayList<>();
        List<Tile> irrevelants = new ArrayList<>();

        for (Tile.Color color : colors) {
            List<Tile> setOfColor = rack.stream().filter(tile -> tile.getColor() == color).toList();
            List<Tile> run = new ArrayList<>();

            for (Tile tile : setOfColor) {
                if (runs.stream().anyMatch(list -> list.contains(tile))) { // if there is already a run in rack with current tile
                    continue;
                }
                run.add(tile);
                Tile nextTile = nextRunTile(setOfColor, tile);
                while (nextTile != null) { // looking to next tile of current tile with same color and +1 number or with number 1 if current tile is 13.
                    run.add(nextTile);
                    if (tile.getNumber() == 13 && nextTile.getNumber() == 1) {
                        break;
                    }
                    Tile currentTile = nextTile;
                    nextTile = nextRunTile(setOfColor, nextTile);
                    if (nextTile != null && currentTile.getNumber() == 13 && nextTile.getNumber() == 1) {
                        break;
                    }
                }
                if (run.size() > 2) { // if there is a run with at least 3 tiles
                    runs.add(run.stream().toList());
                    run.clear();
                } else {
                    for (Tile tile1 : run) {
                        if (!irrevelants.contains(tile1)) {
                            irrevelants.add(tile1);
                        }
                    }
                    run.clear();

                }
            }
        }
        runs.add(irrevelants);
        return runs;
    }


    @Override
    public List<List<Tile>> sets(List<Tile> rack) {

        List<List<Tile>> sets = new ArrayList<>();
        List<Tile> irrelevants = new ArrayList<>();

        for (int i = 1; i <= 13; i++) { // for every number that can be a set search for different colors.
            int number = i;
            List<Tile> setOfNumber = rack.stream().filter(tile -> tile.getNumber() == number).toList();
            // below fragment is for remove same color and same number tiles.
            setOfNumber = setOfNumber.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Tile::getColor))), ArrayList::new));
            if (setOfNumber.size() > 2) {
                sets.add(setOfNumber);
            } else {
                Tile joker = rack.stream().filter(Tile::isJoker).findFirst().orElse(null); // if set size is 2 and there is a joker.
                if (joker != null && setOfNumber.size() == 2 && sets.stream().noneMatch(list -> list.contains(joker))) {
                    setOfNumber.add(joker);
                    sets.add(setOfNumber);
                } else {
                    irrelevants.addAll(setOfNumber);
                }
            }
        }
        sets.add(irrelevants);
        return sets;
    }


    @Override
    public Tile nextRunTile(List<Tile> rack, Tile tile) { // find next tile of current tile.
        if (tile.getNumber() == 13) {
            return rack.stream().filter(nextTile -> tile.getNumber() - 12 == nextTile.getNumber() && !tile.isJoker() && !nextTile.isJoker()).findFirst().orElse(null);
        } else {
            return rack.stream().filter(nextTile -> tile.getNumber() + 1 == nextTile.getNumber() && !tile.isJoker() && !nextTile.isJoker()).findFirst().orElse(null);

        }
    }

    @Override
    public List<List<Tile>> runsAndSetsRack(List<Tile> rack) { // this function is first find runs in rack and then find sets.

        List<List<Tile>> finalRack = runs(rack);

        List<List<Tile>> sets = sets(finalRack.get(finalRack.size() - 1));

        finalRack.remove(finalRack.get(finalRack.size() - 1));

        finalRack.addAll(sets);

        return finalRack;
    }

    @Override
    public List<List<Tile>> finalRack(List<Tile> rack) { // determine which rack sort is better pair or runs and sets.
        List<List<Tile>> pairRack = pairRack(rack);
        List<List<Tile>> runsAndSetsRack = runsAndSetsRack(rack);
        if (pairRack.get(pairRack.size() - 1).size() < runsAndSetsRack.get(runsAndSetsRack.size() - 1).size()) {
            return pairRack;
        } else {
            return runsAndSetsRack;
        }
    }
}
