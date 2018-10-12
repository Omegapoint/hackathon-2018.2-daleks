package se.omegapoint.daleks.model;

import java.util.Arrays;
import java.util.Optional;

public enum Direction {
    Z(new Point2D(0, 0)),
    N(new Point2D(-1, 0)),
    NE(new Point2D(-1, 1)),
    E(new Point2D(0, 1)),
    SE(new Point2D(1, 1)),
    S(new Point2D(1, 0)),
    SW(new Point2D(1, -1)),
    W(new Point2D(0, -1)),
    NW(new Point2D(-1, -1));

    private final Point2D step;

    Direction(final Point2D step) {
        this.step = step;
    }

    public Point2D getStep() {
        return this.step;
    }

    public static Optional<Direction> getDirectionFor(final String candidate) {
        Optional<Direction> direction = null;
        if (candidate != null) {
            direction =  Arrays.stream(values()).filter(value -> value.name().equalsIgnoreCase(candidate)).findFirst();
        }
        return direction;
    }
}