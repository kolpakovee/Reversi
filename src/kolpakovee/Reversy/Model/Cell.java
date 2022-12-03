package kolpakovee.Reversy.Model;

/**
 * Класс ячейки поля
 */
public class Cell {
    private final int x;
    private final int y;
    private Color color;

    /**
     * Конструктор для создания шашки
     * @param x координата по оси OX
     * @param y координата по оси OY
     */
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.color = Color.gray;
    }

    /**
     * Устанавливает цвет шашки (ячейки)
     * @param color цвет, который необходимо установить
     */
    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor() {return color;}

    public int getX() {return x;}
    public int getY() {return y;}

    /**
     * переопределение метода toString()
     * @return строковое представление ячейки
     */
    public String toString(){
        if (color == Color.gray || color == Color.red){
            return color.getCode() + "□";
        }
        else{
            return color.getCode() + "●";
        }
    }

    /**
     * Проверка, является ли ячейка с заданными координатами угловой
     * @param x координата по оси OX
     * @param y координата по оси OY
     * @return true - ячейка угловая, false - иначе
     */
    public static boolean isCornerCell(int x, int y){
        return (x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7);
    }
    /**
     * Проверка, является ли ячейка с заданными координатами прижата к крам поля
     * @param x координата по оси OX
     * @param y координата по оси OY
     * @return true - ячейка прижата к краю поля, false - иначе
     */
    public static boolean isEdgeCell(int x, int y){
        return x == 0 || y == 0 || x == 7 || y == 7;
    }
}
