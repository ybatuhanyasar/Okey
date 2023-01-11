package com.digitoy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class Tile implements Comparable<Tile> {

    private static AtomicInteger nextId = new AtomicInteger();
    private int id;
    private int number;
    private Color color;
    private boolean joker;
    private boolean pointer;

    public Tile(int number, Color color) {

        this.id = nextId.getAndIncrement();
        this.number = number;
        this.color = color;
        this.joker = false;
        this.pointer = false;
    }

    @Override
    public int compareTo(Tile tile) {
        int tileNumber = tile.getNumber();
        return this.getNumber() - tileNumber;
    }

    public enum Color {
        YELLOW,
        BLUE,
        BLACK,
        RED,
        FAKE_JOKER
    }
}
