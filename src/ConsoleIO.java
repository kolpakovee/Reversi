import java.util.Scanner;

public class ConsoleIO {
    public static void printScore(Field field, GameMode gameMode, Player p1, Player p2) {
        System.out.print(field);
        System.out.println(Color.cyan.getCode() + "-----------------");
        System.out.println(Color.white.getCode() + p1.getName() + ": "
                + Color.purple.getCode() + p1.cellsOnTheField.size());
        System.out.println(Color.white.getCode() + p2.getName() + ": "
                + Color.purple.getCode() + p2.cellsOnTheField.size());
        System.out.println(Color.cyan.getCode() + "-----------------");
    }

    public static Player getPlayer(Color color) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        return new Player(scanner.nextLine(), color);
    }

    public static void MenuToConsole() {
        System.out.println(Color.cyan.getCode() + "REVERSI");
        System.out.println(Color.white.getCode() + "1. Easy mode\n" +
                "2. Advanced mode\n" +
                "3. PVP mode\n" +
                "4. Quit");
    }

    public static void moveToConsole(Cell cell) {
        System.out.println(Color.red.getCode() + "Computer turn " +
                (cell.getX() + 1) + " " + (cell.getY() + 1));
    }

    public static Cell getCoordinatesFromConsole(Field field) {
        System.out.print(Color.white.getCode() + "Введите координаты хода: ");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        if (x < 1 || x > 8 || y < 1 || y > 8){
            return null;
        }
        return field.cells[x - 1][y - 1];
    }
}
