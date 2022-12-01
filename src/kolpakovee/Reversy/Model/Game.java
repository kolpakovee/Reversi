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
            ConsoleIO.printScore(player1, player2);
            do {
                if (gameMode == GameMode.easy) {
                    if (!PlayerTurn.playerMove(field, player1, player2)) {
                        break;
                    }
                    if (!PlayerTurn.easyComputerMove(field, player1, player2)) {
                        break;
                    }
                } else if (gameMode == GameMode.pvp) {
                    if (!PlayerTurn.playerMove(field, player1, player2)) {
                        break;
                    }
                    if (!PlayerTurn.playerMove(field, player2, player1)) {
                        break;
                    }
                } else if (gameMode == GameMode.advanced) {
                    if (!PlayerTurn.playerMove(field, player1, player2)) {
                        break;
                    }
                    if (!PlayerTurn.advancedComputerMode(field, player1, player2)) {
                        break;
                    }
                }
            }
            // Доделать условие
            while (player1.cellsOnTheField.size() + player2.cellsOnTheField.size() != 64);
            player1.setScore(player1.cellsOnTheField.size());
        }
        ConsoleIO.printBestResult(player1);
    }

    public Game() {
    }

    public boolean setGameMode() {
        System.out.print(Color.purple.getCode() + "Select game mode: ");
        Scanner scanner = new Scanner(System.in);
        int menuItem = scanner.nextInt();
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
