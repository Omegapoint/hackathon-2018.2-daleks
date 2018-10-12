package se.omegapoint.daleks.model;

public class Dalek {
    private Point2D position = null;
    private boolean alive = true;

    public Dalek(final Point2D position) {
        this.position = position;
        this.alive = true;
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }

    public Dalek kill() {
        alive = false;
        return this;
    }

    public Dalek move(final Point2D delta) {
        return alive ? moveTo(position.add(delta)) : this;
    }

    public Dalek moveTo(final Point2D destination) {
        if (alive) {
            position = destination;
            //System.out.println(toString());
        }
        return this;
    }

    public String renderAscii() {
        return alive ? "!" : "M";
    }

    @Override
    public String toString() {
        return "Dalek{" +
                "position=" + position +
                ", alive=" + alive +
                '}';
    }
}
