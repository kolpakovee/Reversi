public class Field {
    Cell[][] cells = new Cell[8][8];

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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(Color.cyan.getCode()).append(i + 1);
            for (int j = 0; j < 8; j++) {
                stringBuilder.append(" ").append(cells[i][j]);
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(Color.cyan.getCode()).append("  1 2 3 4 5 6 7 8\n");
        return stringBuilder.toString();
    }

    public int[] getCurrentScore() {
        int[] scores = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].getColor() == Color.white) {
                    scores[0]++;
                } else if (cells[i][j].getColor() == Color.purple) {
                    scores[1]++;
                }
            }
        }
        return scores;
    }
}
