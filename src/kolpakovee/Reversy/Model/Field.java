package kolpakovee.Reversy.Model;

/**
 * Класс игровое поле
 */
public class Field {
    public Cell[][] cells = new Cell[8][8];

    /**
     * Конструктор для создания и заполнения пустыми ячейками поля
     */
    public Field() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(i, j);
                if (i == 3 && j == 3 || i == 4 && j == 4) {
                    cells[i][j].setColor(Color.white);
                }
                if (i == 4 && j == 3 || i == 3 && j == 4) {
                    cells[i][j].setColor(Color.purple);
                }
            }
        }
    }
}
