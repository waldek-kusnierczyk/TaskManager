package pl.coderslab;

import java.util.Scanner;

public class TaskManager {
    static final String DB_FILE_NAME = "tasks.csv";
    static final String[] MENU_OPTS = {"add", "remove", "list", "exit"};

    static String[][] tasks;

    public static void main(String[] args) {
        String selectedOption = "";

        while(true) {
            menu();
            selectedOption = getOption();
            switch (selectedOption) {
                case "add":
                    System.out.println(ConsoleColors.CYAN + "ADD"+ConsoleColors.RESET);
                    break;
                case "remove":
                    System.out.println(ConsoleColors.CYAN + "REMOVE"+ConsoleColors.RESET);
                    break;
                case "list":
                    System.out.println(ConsoleColors.CYAN + "LIST"+ConsoleColors.RESET);
                    break;
                case "exit":
                    System.out.println(ConsoleColors.CYAN + "EXIT"+ConsoleColors.RESET);
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "Please select an appropriate option!!"+ConsoleColors.RESET);
            }
        }
    }

    private static void menu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:"+ConsoleColors.RESET );
        for (String menuOpt : MENU_OPTS) {
            System.out.println(menuOpt);
        }
    }

    private static String getOption() {
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }
}
