import java.util.ArrayList;

public class Player {
    private final String name;
    private final Color color;
    ArrayList<Cell> cellsOnTheField = new ArrayList<>();
    private int bestScore;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void setScore(int score) {
        if (score > bestScore) {
            bestScore = score;
        }
    }

    public int getBestScore() {
        return bestScore;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

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
