package kolpakovee.Reversy.Model;


import kolpakovee.Reversy.ConsoleIO;
import kolpakovee.Reversy.PlayerTurn;

import java.util.Scanner;

public class Game {
    private GameMode gameMode;
    private Field field;
    private Player player1, player2;

    public void startGame() {
        while (true) {
            ConsoleIO.MenuToConsole();
            if (!setGameMode()) {
                break;
            }
            int countOfCells;
            do {
                // посчитали кол-во до ходов
                countOfCells = player1.cellsOnTheField.size() +
                        player2.cellsOnTheField.size();
                // сделали ходы
                if (gameMode == GameMode.easy) {
                    PlayerTurn.playerMove(field, player1, player2);
                    PlayerTurn.easyComputerMove(field, player1, player2);
                } else if (gameMode == GameMode.pvp) {
                    PlayerTurn.playerMove(field, player1, player2);
                    PlayerTurn.playerMove(field, player2, player1);
                } else if (gameMode == GameMode.advanced) {
                    PlayerTurn.playerMove(field, player1, player2);
                    PlayerTurn.advancedComputerMove(field, player1, player2);
                }
            }
            // если ходить некуда или кол-во шашек не изменилось -> break
            while (player1.cellsOnTheField.size() + player2.cellsOnTheField.size() != 64
                    && player1.cellsOnTheField.size() + player2.cellsOnTheField.size() != countOfCells);
            player1.setScore(player1.cellsOnTheField.size());
            ConsoleIO.printWinner(player1, player2);
            ConsoleIO.printField(field, player1, player2);

        }
        if (player1 != null) {
            ConsoleIO.printBestResult(player1);
        }
    }

    public Game() {
    }

    public boolean setGameMode() {
        Scanner scanner;
        int menuItem = 0;
        while (menuItem == 0) {
            try {
                System.out.print(Color.purple.getCode() + "Select game mode: ");
                scanner = new Scanner(System.in);
                menuItem = scanner.nextInt();
            } catch (Exception ignored) {
                System.out.println("Enter a number from 1 to 4!");
            }
        }
        switch (menuItem) {
            case 1:
                this.field = new Field();
                this.gameMode = GameMode.easy;
                if (player1 == null) {
                    this.player1 = ConsoleIO.getPlayer(Color.white);
                }
                player1.getCells(field);
                this.player2 = new Player("Computer", Color.purple);
                player2.getCells(field);
                return true;
            case 2:
                this.field = new Field();
                this.gameMode = GameMode.advanced;
                if (player1 == null) {
                    this.player1 = ConsoleIO.getPlayer(Color.white);
                }
                player1.getCells(field);
                this.player2 = new Player("Computer", Color.purple);
                player2.getCells(field);
                return true;
            case 3:
                this.field = new Field();
                this.gameMode = GameMode.pvp;
                if (player1 == null) {
                    this.player1 = ConsoleIO.getPlayer(Color.white);
                }
                player1.getCells(field);
                this.player2 = ConsoleIO.getPlayer(Color.purple);
                player2.getCells(field);
                return true;
            case 4:
                return false;
            default:
                System.out.println("You entered the wrong value!");
                return false;
        }
    }
}
