package kolpakovee.Reversy;

import kolpakovee.Reversy.Model.*;

import java.util.Scanner;

public class ConsoleIO {
    private static Scanner scanner;

    public static void printScore(Player p1, Player p2) {
        System.out.println(Color.white.getCode() + p1.getName() + ": "
                + Color.purple.getCode() + p1.cellsOnTheField.size());
        System.out.println(Color.white.getCode() + p2.getName() + ": "
                + Color.purple.getCode() + p2.cellsOnTheField.size());
    }

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
        System.out.println("\n" + Color.red.getCode() + "Computer turn " +
                (cell.getX() + 1) + " " + (cell.getY() + 1));
    }

    public static Cell getCoordinatesFromConsole(Field field, Player player) {
        int x = 0;
        int y = 0;
        while (x < 1 || x > 8 || y < 1 || y > 8) {
            try {
                System.out.print(Color.red.getCode() + player.getName() + ", введите координаты хода: ");
                scanner = new Scanner(System.in);
                x = scanner.nextInt();
                y = scanner.nextInt();
            } catch (Exception ignored) {
            }
        }

        return field.cells[x - 1][y - 1];
    }

    public static void printBestResult(Player player) {
        System.out.print(Color.purple.getCode() + "Лучший результат игрока "
                + player.getName() + " - " + Color.cyan.getCode() + player.getBestScore());
    }

    public static void printField(Field field, Player player1, Player player2) {
        for (int i = 0; i < 8; i++) {
            System.out.print(Color.cyan.getCode() + (i + 1));
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + field.cells[i][j]);
            }
            if (i == 2){
                System.out.print("\t\t\t" + Color.cyan.getCode() + "Счёт:");
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
}
