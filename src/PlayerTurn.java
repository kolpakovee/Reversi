import java.util.ArrayList;

public class PlayerTurn {
    public static ArrayList<Cell> possibleMoves(Field field, ArrayList<Cell> freeCells, Player player) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell cell : freeCells) {
            if (getFunctionResult(field, cell, player.getColor()) >= 1) {
                cells.add(cell);
            }
        }
        return cells;
    }

    public static double getFunctionResult(Field field, Cell cell, Color color) {
        double result = 0;
        if (Cell.isCornerCell(cell.getX(), cell.getY())) {
            result += 0.8;
        } else if (Cell.isEdgeCell(cell.getX(), cell.getY())) {
            result += 0.4;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                result += getFunctionResultHelper(field, cell, color, i, j);
            }
        }
        return result;
    }

    public static double getFunctionResultHelper(Field field, Cell cell, Color color, int forX, int forY) {
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
     * Функция поиска возможных ходов
     *
     * @param field поле для поиска
     * @param cells клетки врага
     * @return массив клеток - возможных ходов
     */
    // в класс ход
    public static ArrayList<Cell> findFreeCells(Field field, ArrayList<Cell> cells) {
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

    public static boolean playerMove(Field field, Player player, Player enemy) {
        boolean canMove = false;
        ArrayList<Cell> freeCells = PlayerTurn.findFreeCells(field, enemy.cellsOnTheField);
        // Находим возможные ходы для player1
        ArrayList<Cell> possibleMoves = PlayerTurn.possibleMoves(field, freeCells, player);
        if (possibleMoves.size() > 0) {
            canMove = true;
        }
        // Показываем игроку, куда он может сходить
        Drawing.repaintPossibleMoves(possibleMoves, field);
        // Метод получения координаты от пользователя
        Cell cell = ConsoleIO.getCoordinatesFromConsole(field);
        // Делаем ход за пользователя
        cell.setColor(player.getColor());
        Drawing.repaint(cell, player, enemy, field);
        return canMove;
    }

    /**
     * Метод для поиска лучшего хода режима Easy
     *
     * @return ячейку - наилучшуй ход
     */
    public static boolean easyComputerMove(Field field, Player player, Player computer) {
        double bestRes = 0;
        ArrayList<Cell> freeCells = PlayerTurn.findFreeCells(field, player.cellsOnTheField);
        Cell c = new Cell(0, 0);
        boolean canMove = false;
        for (Cell cell : freeCells) {
            double res = PlayerTurn.getFunctionResult(field, cell, Color.purple);
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
        return canMove;
    }

    public static boolean advancedComputerMode(Field field, Player player, Player computer) {
        // делаю -100, потому что могут быть ситуации, когда все ходы res могут быть < 0
        double bestComputerRes = -100;
        ArrayList<Cell> freeCells = PlayerTurn.findFreeCells(field, player.cellsOnTheField);
        Cell c = new Cell(0, 0);
        boolean canMove = false;
        // проходимся по всем ходам компьютера
        for (Cell cell : freeCells) {
            double res = PlayerTurn.getFunctionResult(field, cell, Color.purple);
            // проверяем, можем ли так ходить
            if (res >= 1) {
                // если можем, то временно перекрашиваем ячейку
                cell.setColor(Color.purple);
                double bestPlayerRes = 0;
                // находим все ходы для пользователя
                ArrayList<Cell> freeCellsForPlayer = PlayerTurn.findFreeCells(field, computer.cellsOnTheField);
                // проходимся по возможным ходам игрока
                for (Cell playerCell : freeCellsForPlayer) {
                    // получаем значение функции для хода пользователя
                    double playerRes = PlayerTurn.getFunctionResult(field, playerCell, Color.white);
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
        return canMove;
    }
}
