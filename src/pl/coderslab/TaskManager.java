package pl.coderslab;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static final String DB_FILE_NAME = "tasks.csv";
    static final String[] MENU_OPTS = {"add", "remove", "list", "exit"};

    static String[][] tasks;

    public static void main(String[] args) {
        String selectedOption;

        if (!loadDBFromFile()) {
            System.out.println("Loading file problem.");
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
                    System.exit(0);
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
        for (String[] task : tasks) {
            tasksList.add(String.join(",", task));
        }

        try {
            Files.write(path, tasksList);
        } catch (IOException ex) {
            System.out.println("Nie można zapisać pliku.");
        }
    }

    private static void addOption() {
        String description;
        String date = null;
        String isImportant;

        System.out.println(ConsoleColors.CYAN + "ADD" + ConsoleColors.RESET);
        System.out.println("Please add task description: ");
        Scanner scan = new Scanner(System.in);
        while (true) {
            description = scan.nextLine().trim();                   // usuwamy biale znaki z poczatku i konca tekstu
            if(description.length() > 0) {
                break;
            }
            System.out.println(ConsoleColors.RED_BOLD + "Please write appropriate task description!!" + ConsoleColors.RESET);
        }


        boolean isDateParsable = false;
        while (!isDateParsable) {
            System.out.println("Please add task due date: ");
            isDateParsable = true;
            date = scan.nextLine();                             // nextLine() zamiast next(), żeby zabiezpieczyć przed wpisaniem bialych znakow
            date = date.replaceAll("\\s", "");                  // usuwamy biale znaki
            String[] dateParts = date.split("-");

            if (dateParts.length != 3) {
                isDateParsable = false;
            } else {
                if (dateParts[0].length() != 4 || dateParts[1].length() != 2 || dateParts[2].length() != 2) {
                    isDateParsable = false;
                } else {
                    for (String datePart : dateParts) {                 // sprawdzamy poszczegolne czesci daty czy sa liczbami (proste sprawdzanie)
                        if (!NumberUtils.isParsable(datePart)) {
                            isDateParsable = false;
                            break;
                        }
                    }
                }
            }
            if (!isDateParsable) {
                System.out.println(ConsoleColors.RED_BOLD + "Wrong date format!!!" + ConsoleColors.RESET);
            }
        }
        System.out.println("Is your task is important: " + ConsoleColors.RED + "true/false");
        while (!scan.hasNextBoolean()) {
            scan.next();
            System.out.println(ConsoleColors.RED_BOLD + "Please write appropriate task important format!!" + ConsoleColors.RESET);
        }
        isImportant = scan.next();
        addToTasks(description, date, isImportant);

        System.out.print(ConsoleColors.RESET + "Value was successfully added: ");
        System.out.println(description + ", " + date + ", " + ConsoleColors.RED+isImportant+ConsoleColors.RESET);
    }

    private static void addToTasks(String description, String date, String isImportant) {
        tasks = Arrays.copyOf(tasks, tasks.length+1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = date;
        tasks[tasks.length-1][2] = isImportant;
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
                    //System.out.println(tasks[i][j]);
                }
                //if (j < tasks[i].length - 1)
                else
                {
                    System.out.print(ConsoleColors.RESET+ tasks[i][j] + ConsoleColors.RESET);
                    if (j < tasks[i].length - 1) {
                        System.out.print(ConsoleColors.RESET + "\t" + ConsoleColors.RESET);
                    }
                }
            }
        }
    }

    private static void exitOption() {
        System.out.println(ConsoleColors.CYAN + "EXIT" + ConsoleColors.RESET);
        saveDBToFile();
    }

}
