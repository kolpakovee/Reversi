import java.util.ArrayList;

public class Drawing {
    public static void repaintPossibleMoves(ArrayList<Cell> possibleMoves, Field field) {
        for (Cell cell : possibleMoves) {
            cell.setColor(Color.red);
        }
        System.out.println(field);
        for (Cell cell : possibleMoves) {
            cell.setColor(Color.gray);
        }
    }

    public static void repaint(Cell cell, Player p1, Player p2, Field field) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                repaintCells(cell, i, j, field);
            }
        }
        p1.getCells(field);
        p2.getCells(field);
    }

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
