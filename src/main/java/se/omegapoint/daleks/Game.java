package se.omegapoint.daleks;

import se.omegapoint.daleks.model.Board;
import se.omegapoint.daleks.model.Dalek;
import se.omegapoint.daleks.model.Direction;
import se.omegapoint.daleks.model.Player;
import se.omegapoint.daleks.model.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final static Random RAND = new Random();
    private final static List<String> commands = Arrays.asList("x", "n", "ne", "e", "se", "s", "sw", "w", "nw", "t", "z");
    private final static List<String> commands2 = Arrays.asList("q");

    private final int widthX;
    private final int widthY;
    private final int numberOfDaleks;
    private final Board board;

    public Game(final int widthX, final int widthY, int numberOfDaleks, String name) {
        this.widthX = widthX;
        this.widthY = widthY;
        this.numberOfDaleks = numberOfDaleks;
        this.board = createBoard(name);
    }

    public static void main(String[] args) {
        int widthX = 10;
        int widthY = 40;
        int numberOfDaleks = widthX * widthY * 5 / 100;

        System.out.println("Commands:"
                + "\n\tx - Exit"
                + "\n\tn - North"
                + "\n\tne - NorthEast"
                + "\n\te - East"
                + "\n\tse - SouthEast"
                + "\n\ts - South"
                + "\n\tsw - SouthWest"
                + "\n\tw - West"
                + "\n\tnw - NorthWest"
                + "\n\tt - Teleport"
                + "\n\tz - Zero, i.e. stay");

        System.out.println("Enter yor name!");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Welcome " + name + "!");

        Game game = new Game(widthX, widthY, numberOfDaleks, name);
        Board board = game.board;
        Player player = board.getPlayer();

        long rounds = 0;

        String command = "";

        do {
            String renderAscii = board.renderAscii();
            System.out.println(renderAscii);

            if (player.isAlive() && board.isDaleksAlive()) {
                do {
                    System.out.println("You are alive. " + board.numberDaleksAlive() + " daleks alive. Enter command: " + commands);
                    command = scanner.nextLine().trim();
                } while (!isCommand(command));

                Optional<Direction> whereto = Direction.getDirectionFor(command);
                if (whereto.isPresent()) {
                    board.movePlayer(whereto.get().getStep());
                    if (player.isAlive()) {
                        board.moveDaleks();
                    }
                } else if (command.equalsIgnoreCase("t")) {
                    board.movePlayerTo(getRandomPosition(widthX, widthY));
                }

                rounds++;
            } else {
                command = "q";
            }
        } while (!command.equalsIgnoreCase("x") && !command.equalsIgnoreCase("q"));

        if (command.equalsIgnoreCase("x")) {
            System.out.println("" + player.getName() + ", you did quit after " + rounds + " rounds!");
        } else if (player.isAlive()) {
            System.out.println("Congratulations " + player.getName() + ", you won after " + rounds + " rounds!");
        } else if (board.isDaleksAlive()) {
            System.out.println("Sorry " + player.getName() + ", you lost after " + rounds + " rounds!");
        } else {
            System.out.println("Hmmm... " + player.getName() + ", how did you end up here after " + rounds + " rounds!");
        }
    }

    private static boolean isCommand(final String candiate) {
        for (String c : commands) {
            if (c.equalsIgnoreCase(candiate)) {
                return true;
            }
        }
        return false;
    }

    private static Point2D getRandomPosition(final int widthX, final int widthY) {
        Point2D width = new Point2D(widthX, widthY);
        Point2D offset = new Point2D(0, 0);
        return Point2D.getRandomPoint2D(RAND, width, offset);
    }

    private Player createPlayer(final String name, final Dalek[] daleks) {
        Player player = null;
        do {
            player = new Player(name, getRandomPosition(widthX, widthY));
        } while (collidingWithDaleks(Arrays.asList(daleks), player.getPosition()));
        return player;
    }

    private Dalek[] createDaleks() {
        List<Dalek> daleks = new ArrayList<>(numberOfDaleks);
        for (int i = 0; i < numberOfDaleks; ++i) {
            Dalek dalek = null;
            do {
                dalek = new Dalek(getRandomPosition(widthX, widthY));
            } while (collidingWithDaleks(daleks, dalek.getPosition()));
            daleks.add(dalek);
        }
        return daleks.toArray(new Dalek[0]);
    }

    private boolean collidingWithDaleks(final List<Dalek> daleks, final Point2D pos) {
        return daleks.stream().anyMatch(d -> d.getPosition().equals(pos));
    }

    private Board createBoard(String name) {
        Dalek[] daleks = createDaleks();
        Player player = createPlayer(name, daleks);

        Board board = new Board(widthX, widthY, player, daleks);

        return board;
    }
}
