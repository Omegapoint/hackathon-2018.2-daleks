package se.omegapoint.daleks.model;

import java.util.Objects;
import java.util.Random;

public class Point2D {

    private final int x;
    private final int y;

    public Point2D(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Point2D getRandomPoint2D(final Random rand, final Point2D range, final Point2D offset) {
        final int randX = rand.nextInt(range.x) + offset.x;
        final int randY = rand.nextInt(range.y) + offset.y;
        return new Point2D(randX, randY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point2D add(final Point2D term2) {
        final int newX = x + term2.x;
        final int newY = y + term2.y;
        return new Point2D(newX, newY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2D point2D = (Point2D) o;

        if (x != point2D.x) return false;
        return y == point2D.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
