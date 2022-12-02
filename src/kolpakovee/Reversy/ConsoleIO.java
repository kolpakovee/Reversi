package kolpakovee.Reversy;

import kolpakovee.Reversy.Model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleIO {
    private static Scanner scanner;

    public static Player getPlayer(Color color) {
        scanner = new Scanner(System.in);
        if (color == Color.white) {
            System.out.print("Enter first player name: ");
        } else {
            System.out.print("Enter second player name: ");
        }
        return new Player(scanner.nextLine(), color);
    }

    public static void MenuToConsole() {
        System.out.println(Color.cyan.getCode() + "REVERSI");
        System.out.println(Color.white.getCode() + "1. Easy mode\n" +
                "2. Advanced mode\n" +
                "3. PVP mode\n" +
                "4. Quit");
    }

    public static void moveToConsole(Cell cell) {
        System.out.println(Color.red.getCode() + "The computer went to the cell (" +
                (cell.getX() + 1) + " " + (cell.getY() + 1) + ")");
    }

    public static Cell getCoordinatesFromConsole(Field field, Player player, ArrayList<Cell> possibleMoves) {
        int x;
        int y;
        while (true) {
            try {
                System.out.print(Color.green.getCode() + player.getName() + " enter the coordinates of the moves: ");
                scanner = new Scanner(System.in);
                x = scanner.nextInt();
                y = scanner.nextInt();
                boolean canMove = false;
                for (Cell c : possibleMoves) {
                    if (c.getX() == (x - 1) && c.getY() == (y - 1)) {
                        canMove = true;
                        break;
                    }
                }
                if (canMove) {
                    break;
                }
            } catch (Exception ignored) {
            }
        }

        return field.cells[x - 1][y - 1];
    }

    public static void printBestResult(Player player) {
        System.out.print(Color.purple.getCode() + player.getName() + "'s best result "
                + " - " + Color.cyan.getCode() + player.getBestScore());
    }

    public static void printField(Field field, Player player1, Player player2) {
        for (int i = 0; i < 8; i++) {
            System.out.print(Color.cyan.getCode() + (i + 1));
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + field.cells[i][j]);
            }
            if (i == 2) {
                System.out.print("\t\t\t" + Color.cyan.getCode() + "Score:");
            }
            if (i == 3) {
                System.out.print("\t\t\t" + player1.getColor().getCode() + player1.getName() + ": "
                        + player1.cellsOnTheField.size());
            }
            if (i == 4) {
                System.out.print("\t\t\t" + player2.getColor().getCode() + player2.getName() + ": "
                        + player2.cellsOnTheField.size());
            }
            System.out.println();
        }
        System.out.println(Color.cyan.getCode() + "  1 2 3 4 5 6 7 8");
    }

    public static void printWinner(Player player1, Player player2) {
        if (player1.cellsOnTheField.size() > player2.cellsOnTheField.size()) {
            System.out.println(player1.getColor().getCode() + "Congratulations " + player1.getName() + " you won!");
        } else if (player1.cellsOnTheField.size() < player2.cellsOnTheField.size()) {
            System.out.println(player2.getColor().getCode() + "Congratulations " + player2.getName() + " you won!");
        } else {
            System.out.println(Color.cyan.getCode() + "Friendship won in a fair fight!");
        }
    }
}
