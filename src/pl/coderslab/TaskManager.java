package pl.coderslab;

import com.sun.jdi.connect.Connector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static final String DB_FILE_NAME = "tasks.csv";
    static final String[] MENU_OPTS = {"add", "remove", "list", "exit"};

    static String[][] tasks;

    public static void main(String[] args) {
        String selectedOption = "";

        if (!loadDBFromFile()) {
            System.out.println("Problem z Odczytem pliku bazy.");
            System.exit(0);
        }

        while (true) {
            menu();
            selectedOption = getOption();
            switch (selectedOption) {
                case "add":
                    addOption();
                    break;
                case "remove":
                    removeOption();
                    break;
                case "list":
                    listOption();
                    break;
                case "exit":
                    exitOption();
                default:
                    System.out.println(ConsoleColors.RED + "Please select an appropriate option!!" + ConsoleColors.RESET);
            }
        }
    }

    private static void menu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String menuOpt : MENU_OPTS) {
            System.out.println(menuOpt);
        }
    }

    private static String getOption() {
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }

    private static boolean loadDBFromFile() {
        Path path = Paths.get(DB_FILE_NAME);
        if (!Files.exists(path)) {
            return false;
        }
        try {
            List<String> tasksLines = Files.readAllLines(path);
            int numberOftasks = tasksLines.size();
            System.out.println("taskSize = " + numberOftasks);

            tasks = new String[numberOftasks][];

            /*for (String line : tasksLines) {
                System.out.println(line);
            }*/
            for (int i = 0; i < tasks.length; i++) {
                tasks[i] = tasksLines.get(i).split(",");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private static void saveDBToFile() {
        Path path = Paths.get(DB_FILE_NAME);

        List<String> tasksList = new ArrayList<>();
        for (int i = 0; i < tasks.length; i++) {
            StringBuilder task = new StringBuilder();
//            for (int j = 0; j < tasks[i].length; j++) {
//                task.append(tasks[i][j]).append(",");
//            }

            tasksList.add(String.join("-", tasks[i]));
        }

        try {
            Files.write(path, tasksList);
        } catch (IOException ex) {
            System.out.println("Nie można zapisać pliku.");
        }
    }

    private static void addOption() {
        System.out.println(ConsoleColors.CYAN + "ADD" + ConsoleColors.RESET);
    }

    private static void removeOption() {
        System.out.println(ConsoleColors.CYAN + "REMOVE" + ConsoleColors.RESET);
    }

    private static void listOption() {
        System.out.println(ConsoleColors.CYAN + "LIST" + ConsoleColors.RESET);
        for (int i = 0; i < tasks.length; i++) {
            System.out.printf("%2d: ", i);
            for (int j = 0; j < tasks[i].length; j++) {
                if (j == tasks[i].length - 1) {
                    System.out.println(ConsoleColors.RED + tasks[i][j] + ConsoleColors.RESET);
                } else {
                    System.out.print(tasks[i][j]);
                    if (j < tasks[i].length-1) {
                        System.out.print("\t");
                    }
                }
            }
        }
    }

    private static void exitOption() {
        System.out.println(ConsoleColors.CYAN + "EXIT" + ConsoleColors.RESET);
        saveDBToFile();
        System.exit(0);
    }

}
