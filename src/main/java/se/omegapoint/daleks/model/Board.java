package se.omegapoint.daleks.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    final private int widthX;
    final private int widthY;
    final private Player player;
    final private Dalek[] daleks;

    public Board(final int widthX, final int widthY, final Player player, final Dalek[] daleks) {
        this.widthX = widthX;
        this.widthY = widthY;
        this.player = player;
        this.daleks = daleks;
    }

    public Player getPlayer() {
        return player;
    }

    public Square[][] getSquares() {
        Square[][] squares = new Square[widthX][widthY];
        for (int x = 0; x < widthX; x++) {
            for (int y = 0; y < widthY; y++) {
                squares[x][y] = new Square();
                Point2D squarePos = new Point2D(x, y);
                if (player.getPosition().equals(squarePos)) {
                    squares[x][y].setPlayer(player);
                }
                List<Dalek> daleks = getDaleks(squarePos);
                squares[x][y].getDaleks().addAll(daleks);
            }
        }
        return squares;
    }

    private List<Dalek> getDaleks(final Point2D position) {
        List<Dalek> daleks = new ArrayList<>();
        for (Dalek d : this.daleks) {
            if (d.getPosition().equals(position)) {
                daleks.add(d);
            }
        }
        return daleks;
    }

    public boolean isDaleksAlive() {
        return numberDaleksAlive() > 0;
    }
    public long numberDaleksAlive() {
        return Arrays.stream(daleks).filter(d -> d.isAlive()).count();
    }
    public boolean movePlayer(final Point2D step) {
        player.move(step);
        if (!isPlayerInBounds()) {
            player.popMove();
            player.kill();
        } else {
            List<Dalek> daleks = getDaleks(player.getPosition());
            if (!daleks.isEmpty()) {
                player.kill();
            }
        }
        return player.isAlive();
    }

    public boolean movePlayerTo(final Point2D destination) {
        player.moveTo(destination);
        if (!isPlayerInBounds()) {
            player.popMove();
            player.kill();
        } else {
            List<Dalek> daleks = getDaleks(player.getPosition());
            if (!daleks.isEmpty()) {
                player.kill();
            }
        }
        return player.isAlive();
    }

    public boolean moveDaleks() {
        for (Dalek d : daleks) {
            moveDalek(d);
        }
        for (Dalek d : daleks) {
            List<Dalek> hits = getDaleks(d.getPosition());
            if (hits.size() > 1) {
                for (Dalek h : hits) {
                    h.kill();
                }
            }
        }

        List<Dalek> daleks = getDaleks(player.getPosition());
        if (!daleks.isEmpty()) {
            player.kill();
        }

        return player.isAlive();
    }

    public void moveDalek(Dalek dalek) {
        if (dalek.isAlive()) {
            int deltaX = (player.getPosition().getX() - dalek.getPosition().getX()) * widthY;
            int deltaY = (player.getPosition().getY() - dalek.getPosition().getY()) * widthX;
            while (deltaX > 1 || deltaX < -1 || deltaY > 1 || deltaY < -1) {
                deltaX /= 2;
                deltaY /= 2;
            }
            dalek.move(new Point2D(deltaX, deltaY));
        }
    }

    public boolean isPlayerInBounds() {
        Point2D position = player.getPosition();
        return position.getX() >= 0 && position.getX() < widthX && position.getY() >= 0 && position.getY() < widthY;
    }

    public String renderAscii() {
        final StringBuilder sb = new StringBuilder();
        renderAsciiFirstLine(sb);
        sb.append('\n');

        renderAsciiIntermediateLines(sb);

        renderAsciiLastLine(sb);
        sb.append('\n');

        return sb.toString();
    }

    private void renderAsciiFirstLine(final StringBuilder sb) {
        sb.append('+');
        for (int i = 0; i < widthY; i++) {
            sb.append('-');
        }
        sb.append('+');
    }

    private void renderAsciiLastLine(final StringBuilder sb) {
        renderAsciiFirstLine(sb);
    }

    private void renderAsciiIntermediateLine(final StringBuilder sb, final Square[] line) {
        sb.append('|');
        for (Square s : line) {
            if (s.getPlayer() != null) {
                sb.append(s.getPlayer().renderAscii());
            } else if (!s.getDaleks().isEmpty()){
                sb.append(s.getDaleks().get(0).renderAscii());
            } else {
                sb.append(' ');
            }

        }
        sb.append('|');
    }

    private void renderAsciiIntermediateLines(final StringBuilder sb) {
        Square[][] squares = getSquares();
        for (Square[] s : squares) {
            renderAsciiIntermediateLine(sb, s);
            sb.append('\n');
        }
    }
}
