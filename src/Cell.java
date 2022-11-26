public class Cell {
    private final int x;
    private final int y;
    private Color color;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.color = Color.gray;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor() {return color;}

    public int getX() {return x;}
    public int getY() {return y;}

    public void changeColor(){
        if (color == Color.white){
            color = Color.purple;
        }
        else if (color == Color.purple){
            color = Color.white;
        }
    }

    public String toString(){
        if (color == Color.gray || color == Color.red){
            return color.getCode() + "⊡" + Color.white.getCode();
        }
        else{
            return color.getCode() + "◎" + Color.white.getCode();
        }
    }

    public static boolean isCornerCell(int x, int y){
        return (x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7);
    }

    public static boolean isEdgeCell(int x, int y){
        return x == 0 || y == 0 || x == 7 || y == 7;
    }
}
