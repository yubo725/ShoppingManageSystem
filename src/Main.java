import java.util.Scanner;

/**
 * Created by admin on 2017/7/31.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private Menu menu = new Menu();

    public void start() {
        menu.showSystemMenu();
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void main(String[] args) {
        new Main().start();
    }

}
