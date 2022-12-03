package kolpakovee.Reversy.Model;

import java.util.ArrayList;

/**
 * Класс игрок
 */
public class Player {
    private final String name;
    private final Color color;
    public ArrayList<Cell> cellsOnTheField = new ArrayList<>();
    private int bestScore;

    /**
     * Конструктор с параметрами
     * @param name имя игрока
     * @param color цвет его шашек
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Устанавливает значение лучшего счёта для игрока
     * @param score счёт для установки
     */
    public void setScore(int score) {
        if (score > bestScore) {
            bestScore = score;
        }
    }

    /**
     * Возвращает лучший счёт игрока
     * @return счёт игрока
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * Возвращает имя игрока
     * @return имя игрока
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает цвет шашаек игрока
     * @return цвет шашек
     */
    public Color getColor() {
        return color;
    }

    /**
     * Пересчитывает шашки игрока на игровом поле
     * @param field игровое поле
     */
    public void getCells(Field field) {
        cellsOnTheField.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (field.cells[i][j].getColor() == this.color) {
                    cellsOnTheField.add(field.cells[i][j]);
                }
            }
        }
    }
}
