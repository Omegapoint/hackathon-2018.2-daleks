package se.omegapoint.daleks.model;

import java.util.ArrayList;
import java.util.List;

public class Square {

    private Player player = null;
    private List<Dalek> daleks = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public Square setPlayer(final Player player) {
        this.player = player;
        return this;
    }

    public List<Dalek> getDaleks() {
        return daleks;
    }
}
