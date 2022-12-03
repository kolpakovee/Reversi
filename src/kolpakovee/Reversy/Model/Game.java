package kolpakovee.Reversy.Model;


import kolpakovee.Reversy.ConsoleIO;
import kolpakovee.Reversy.View.Drawing;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс игра
 */
public class Game {
    private GameMode gameMode;
    private Field field;
    private Player player1, player2;

    /**
     * метод для старта игры
     */
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
                    playerMove(player1, player2);
                    easyComputerMove(player1, player2);
                } else if (gameMode == GameMode.pvp) {
                    playerMove(player1, player2);
                    playerMove(player2, player1);
                } else if (gameMode == GameMode.advanced) {
                    playerMove(player1, player2);
                    advancedComputerMove(player1, player2);
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

    /**
     * Конструктор без параметров
     */
    public Game() {
    }

    /**
     * @param freeCells свободные клетки поля
     * @param player игрок, делающий ход
     * @return клетки, в которые можно сходить
     */
    public ArrayList<Cell> possibleMoves(ArrayList<Cell> freeCells, Player player) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell cell : freeCells) {
            if (getFunctionResult(cell, player.getColor()) >= 1) {
                cells.add(cell);
            }
        }
        return cells;
    }

    /**
     * Метод для подсчёта значения функции хода
     * @param cell предполагаемая ячейка для хода
     * @param color цвет игрока, который будет ходить (передаётся именно цвет, потому что эта же функция используется для хода компьютера)
     * @return значение функции для предполагаемого хода
     */
    public double getFunctionResult(Cell cell, Color color) {
        double result = 0;
        if (Cell.isCornerCell(cell.getX(), cell.getY())) {
            result += 0.8;
        } else if (Cell.isEdgeCell(cell.getX(), cell.getY())) {
            result += 0.4;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                result += getFunctionResultHelper(cell, color, i, j);
            }
        }
        return result;
    }

    /**
     * Метод для того, чтобы сходить от ячейки в одну из 8-ми сторон и посчитать значение функции ходаа
     * @param cell предполагаемая ячейка для хода
     * @param color цвет игрока, который совершает ход
     * @param forX параметр для перемещения по поля по оси OX
     * @param forY параметр для перемещения по поля по оси OY
     * @return значение функции для 1 из 8 возможных направлений
     */
    public double getFunctionResultHelper(Cell cell, Color color, int forX, int forY) {
        double result = 0;
        int x = cell.getX() + forX;
        int y = cell.getY() + forY;
        boolean canReturn = false;
        while (x >= 0 && y >= 0 && x <= 7 && y <= 7) {
            // если цвет врага
            if (field.cells[x][y].getColor() != color && field.cells[x][y].getColor() != Color.gray) {
                x += forX;
                y += forY;
                if (Cell.isEdgeCell(x, y)) {
                    result += 2;
                } else {
                    result += 1;
                }
            }
            // если наш цвет
            else if (field.cells[x][y].getColor() == color) {
                canReturn = true;
                break;
            }
            // если серый
            else {
                break;
            }
        }
        if (canReturn) {
            return result;
        }
        return 0;
    }

    /**
     * Функция поиска возможных ходов рядом с клетками противника
     * @param cells клетки противника
     * @return ячейки, в которые теоретически можно сходить
     */
    public ArrayList<Cell> findFreeCells(ArrayList<Cell> cells) {
        ArrayList<Cell> resultList = new ArrayList<>();
        for (Cell cell : cells) {
            for (int j = cell.getX() - 1; j <= cell.getX() + 1; j++) {
                for (int k = cell.getY() - 1; k <= cell.getY() + 1; k++) {
                    if (j >= 0 && j <= 7 &&
                            k >= 0 && k <= 7 &&
                            field.cells[j][k].getColor() == Color.gray) {
                        resultList.add(field.cells[j][k]);
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * Функция для совершения хода за игрока
     * @param player игрок, совершающий ход
     * @param enemy противник
     */
    public void playerMove(Player player, Player enemy) {
        ArrayList<Cell> freeCells = findFreeCells(enemy.cellsOnTheField);
        // Находим возможные ходы для player1
        ArrayList<Cell> possibleMoves = possibleMoves(freeCells, player);
        if (possibleMoves.size() == 0) {
            return;
        }
        // Показываем игроку, куда он может сходить
        Drawing.repaintPossibleMoves(possibleMoves, field, player, enemy);
        // Метод получения координаты от пользователя
        Cell cell = ConsoleIO.getCoordinatesFromConsole(field, player, possibleMoves);
        // Делаем ход за пользователя
        cell.setColor(player.getColor());
        Drawing.repaint(cell, player, enemy, field);
    }

    /**
     * Метод совершает ход компьютера в лёгком режиме игры
     * @param player игрок, который играет против компьютера
     * @param computer компьютер
     */
    public void easyComputerMove(Player player, Player computer) {
        double bestRes = 0;
        ArrayList<Cell> freeCells = findFreeCells(player.cellsOnTheField);
        Cell c = new Cell(0, 0);
        boolean canMove = false;
        for (Cell cell : freeCells) {
            double res = getFunctionResult(cell, Color.purple);
            if (res > bestRes && res >= 1) {
                canMove = true;
                bestRes = res;
                c = cell;
            }
        }
        if (canMove) {
            c.setColor(Color.purple);
            Drawing.repaint(c, player, computer, field);
            ConsoleIO.moveToConsole(c);
        }
    }

    /**
     * Метод совершает ход компьютера в продвинутом режиме игры
     * @param player игрок, который играет против компьютера
     * @param computer компьютер
     */
    public void advancedComputerMove(Player player, Player computer) {
        // делаю -100, потому что могут быть ситуации, когда все ходы res могут быть < 0
        double bestComputerRes = -100;
        ArrayList<Cell> freeCells = findFreeCells(player.cellsOnTheField);
        Cell c = new Cell(0, 0);
        boolean canMove = false;
        // проходимся по всем ходам компьютера
        for (Cell cell : freeCells) {
            double res = getFunctionResult(cell, Color.purple);
            // проверяем, можем ли так ходить
            if (res >= 1) {
                // если можем, то временно перекрашиваем ячейку
                cell.setColor(Color.purple);
                double bestPlayerRes = 0;
                // находим все ходы для пользователя
                ArrayList<Cell> freeCellsForPlayer = findFreeCells(computer.cellsOnTheField);
                // проходимся по возможным ходам игрока
                for (Cell playerCell : freeCellsForPlayer) {
                    // получаем значение функции для хода пользователя
                    double playerRes = getFunctionResult(playerCell, Color.white);
                    if (playerRes > bestPlayerRes) {
                        bestPlayerRes = playerRes;
                    }
                }
                // уже есть лучший ход пользователя bestPlayerRes проверяем, подходит ли нам
                if (res - bestPlayerRes > bestComputerRes) {
                    bestComputerRes = res - bestPlayerRes;
                    canMove = true;
                    c = cell;
                }
                cell.setColor(Color.gray);
            }
        }
        if (canMove) {
            c.setColor(Color.purple);
            Drawing.repaint(c, player, computer, field);
            ConsoleIO.moveToConsole(c);
        }
    }

    /**
     * Метод, устанавливающий режим игры и инициализирующий поля
     * @return true -> инициализация прошла успешно,
     * false -> пользователь ввёл что-то не то или решил завершить выполнение программы
     */
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
