import java.util.Scanner;

public class Menu {
    private static Game game;
    static Scanner scanner = new Scanner(System.in);

    public static void getMainMenu() {
        System.out.println(Color.cyan.getCode() + "REVERSI");
        System.out.println(Color.white.getCode() + "1. Easy mode\n" +
                "2. Advanced mode\n" +
                "3. PVP mode\n" +
                "4. Quit");
    }

    public static Game setGameMode() {
        System.out.print(Color.purple.getCode() + "Select game mode: ");
        int menuItem = scanner.nextInt();
        switch (menuItem) {
            case 1:
                return new Game(GameMode.easy, getPlayer(Color.white), new Player("computer", Color.purple));
            case 2:
                return new Game(GameMode.advanced, getPlayer(Color.white), new Player("computer", Color.purple));
            case 3:
                return new Game(GameMode.pvp, getPlayer(Color.white), getPlayer(Color.purple));
            case 4:
                return null;
            default:
                System.out.println("You entered the wrong value!");
                return null;
        }
    }
// подумать куда убрать отсюда
    public static Player getPlayer(Color color) {
        scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        return new Player(scanner.nextLine(), color);
    }
}
