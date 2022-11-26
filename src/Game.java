import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private GameMode gameMode;
    private Field field;
    private Player player1, player2;

    public void startGame() {
        while (true) {
            Menu.getMainMenu();
            Game game = Menu.setGameMode();

            if (game == null) {
                break;
            }

            this.gameMode = game.gameMode;
            this.field = game.field;
            this.player1 = game.player1;
            this.player2 = game.player2;

            System.out.print(field);
            printScore();
            Cell c;
            ArrayList<Cell> freeCells1 = new ArrayList<>();
            ArrayList<Cell> freeCells2 = new ArrayList<>();
            do {
                if (gameMode == GameMode.easy) {
                    freeCells1 = findFreeCells(field, player2.cellsOnTheField);
                    c = playerTurn(freeCells1, player1);
                    // для дебага
                    System.out.println(Color.white.getCode() + "Player 1 turn " + (c.getX() + 1) + " " + (c.getY()+ 1));
                    System.out.print(field);
                    printScore();
                    freeCells2 = findFreeCells(field, player1.cellsOnTheField);
                    c = playerTurn(freeCells2, player2);
                    // для дебага
                    System.out.println(Color.white.getCode() + "Player 2 turn " + (c.getX() + 1) + " " + (c.getY()+ 1));
                    System.out.print(field);
                    printScore();
                }
                else if (gameMode == GameMode.pvp){
                    freeCells1 = findFreeCells(field, player2.cellsOnTheField);
                    ArrayList<Cell> possibleMoves = possibleMoves(freeCells1, player1);
                    repaintPossibleMoves(possibleMoves);
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("введите ход: ");
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    field.cells[x-1][y-1].setColor(player1.getColor());
                    repaint(field.cells[x-1][y-1]);
                    System.out.print(field);
                    printScore();
                    freeCells2 = findFreeCells(field, player1.cellsOnTheField);
                    c = playerTurn(freeCells2, player2);
                    // для дебага
                    System.out.println(Color.white.getCode() + "Player 2 turn " + (c.getX() + 1) + " " + (c.getY()+ 1));
                    System.out.print(field);
                    printScore();
                }
            }
            while (freeCells1.size() != 0 || freeCells2.size() != 0
                    || player1.cellsOnTheField.size() + player2.cellsOnTheField.size() == 64);
        }
    }

    public Game(GameMode gameMode, Player player1, Player player2) {
        this.gameMode = gameMode;
        this.field = new Field();
        this.player1 = player1;
        player1.getCells(field);
        this.player2 = player2;
        player2.getCells(field);
    }

    public Game(Game game){
        this.gameMode = game.gameMode;
        this.field = game.field;
        this.player1 = game.player1;
        this.player2 = game.player2;
    }

    public Game() {
    }
// в игроках научиться выводить красиво счёт и написать норм. Удалить метод getCurrentScore()
    public void printScore() {
        int[] scores = field.getCurrentScore();
        if (gameMode == GameMode.pvp) {
            System.out.println(Color.white.getCode() + player1.getName() + ": "
                    + Color.purple.getCode() + scores[0]);
            System.out.println(Color.white.getCode() + player2.getName() + ": "
                    + Color.purple.getCode() + scores[1]);
        } else {
            System.out.println(Color.white.getCode() + player1.getName() + ": "
                    + Color.purple.getCode() + scores[0]);
            System.out.println(Color.white.getCode() + "computer: "
                    + Color.purple.getCode() + scores[1]);
        }
    }
// в класс ход
    public Cell playerTurn(ArrayList<Cell> freeCells, Player player) {
        double bestRes = 0;
        Cell c = new Cell(0, 0);
        for (Cell cell : freeCells) {
            // менять цвет по игроку
            double res = getFunctionResult(cell, player.getColor());
            if (res > bestRes) {
                bestRes = res;
                c = cell;
            }
        }
        c.setColor(player.getColor());
        repaint(c);
        player1.getCells(field);
        player2.getCells(field);
        return c;
    }

    /**
     * Функция поиска возможных ходов
     *
     * @param field поле для поиска
     * @param cells клетки врага
     * @return массив клеток - возможных ходов
     */
    // в класс ход
    public ArrayList<Cell> findFreeCells(Field field, ArrayList<Cell> cells) {
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
    // в класс ход
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
// в класс ход
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
// в класс Draw
    public void repaintCells(Cell cell, int forX, int forY) {
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
// в класс Draw
    public void repaint(Cell cell) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                repaintCells(cell, i, j);
            }
        }
    }
//  в класс ход
    public ArrayList<Cell> possibleMoves(ArrayList<Cell> freeCells, Player player){
        ArrayList<Cell> cells = new ArrayList<>();
        for(Cell cell : freeCells){
            if (getFunctionResult(cell, player.getColor()) >= 1){
                cells.add(cell);
            }
        }
        return cells;
    }
// в класс Draw
    public void repaintPossibleMoves(ArrayList<Cell> possibleMoves){
        for(Cell cell : possibleMoves){
            cell.setColor(Color.red);
        }
        System.out.println(field);
        for(Cell cell : possibleMoves){
            cell.setColor(Color.gray);
        }
    }
}
