package com.digitoy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class Player {

    private static AtomicInteger nextId = new AtomicInteger();
    private int id;

    private List<Tile> rack;

    private List<List<Tile>> finalRack;

    private boolean starter;

    public Player() {
        this.id = nextId.getAndIncrement();
        this.rack = new ArrayList<>();
        this.starter = false;
        this.finalRack = new ArrayList<>();
    }
}
