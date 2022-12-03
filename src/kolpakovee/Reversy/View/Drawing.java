package kolpakovee.Reversy.View;

import kolpakovee.Reversy.ConsoleIO;
import kolpakovee.Reversy.Model.Cell;
import kolpakovee.Reversy.Model.Color;
import kolpakovee.Reversy.Model.Field;
import kolpakovee.Reversy.Model.Player;

import java.util.ArrayList;

/**
 * Класс для визуализации действий на игровом поле
 */
public class Drawing {
    /**
     * Визуализирует на поле возможные ходы и выводит это поле
     * @param possibleMoves возможные ходы
     * @param field поле для визуализации
     * @param player1 игрок, совершающий ход
     * @param player2 противник игрока, совершающего ход
     */
    public static void repaintPossibleMoves(ArrayList<Cell> possibleMoves, Field field, Player player1, Player player2) {
        for (Cell cell : possibleMoves) {
            cell.setColor(Color.red);
        }
        ConsoleIO.printField(field, player1, player2);
        for (Cell cell : possibleMoves) {
            cell.setColor(Color.gray);
        }
    }

    /**
     * Метод для перекраски ячеек при совершении хода
     * @param cell ячейка, в которую совершают ход
     * @param player1 игрок, чтобы обновить у него его шашки
     * @param player2 игрок, чтобы обновить у него его шашки
     * @param field поле, на котором перекрашиваем
     */
    public static void repaint(Cell cell, Player player1, Player player2, Field field) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                repaintCells(cell, i, j, field);
            }
        }
        player1.getCells(field);
        player2.getCells(field);
    }

    /**
     * Вспомогательный метод который занимается перекраской в 1 из 8 сторон от клетки после совершения хода
     * @param cell ячейка - совершённый ход
     * @param forX параметр для перемещения по оси OX
     * @param forY параметр для перемещения по оси OY
     * @param field поле, на котором перекрашиваем
     */
    public static void repaintCells(Cell cell, int forX, int forY, Field field) {
        int x = cell.getX() + forX;
        int y = cell.getY() + forY;
        boolean canDraw = false;
        while (x >= 0 && y >= 0 && x <= 7 && y <= 7) {
            // если цвет врага
            if (field.cells[x][y].getColor() != cell.getColor() && field.cells[x][y].getColor() != Color.gray) {
                x += forX;
                y += forY;
            }
            // если наш цвет
            else if (field.cells[x][y].getColor() == cell.getColor()) {
                canDraw = true;
                break;
            }
            // если серый
            else {
                break;
            }
        }
        if (canDraw) {
            int x2 = cell.getX();
            int y2 = cell.getY();
            while (x2 != x || y2 != y) {
                x2 += forX;
                y2 += forY;
                field.cells[x2][y2].setColor(cell.getColor());
            }
        }
    }
}
