package se.omegapoint.daleks.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    final private String name;
    private boolean alive = true;
    private List<Point2D> path = new ArrayList<>();

    public Player(final String name, final Point2D position) {
        this.name = name;
        this.path.add(0, position);
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public Point2D getPosition() {
        return path.get(0);
    }

    public boolean isAlive() {
        return alive;
    }

    public Player kill() {
        alive = false;
        return this;
    }

    public Player move(final Point2D delta) {
        return alive ? moveTo(getPosition().add(delta)) : this;
    }

    public Player moveTo(final Point2D destination) {
        if (alive) {
            path.add(0, destination);
            //System.out.println(toString());

        }
        return this;
    }

    public Player popMove() {
        path.remove(0);
        return this;
    }

    public String renderAscii() {
        return alive ? "*" : "X";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name != null ? name.equals(player.name) : player.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", path=" + path +
                ", alive=" + alive +
                '}';
    }
}
