package com.company;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static String taskToString(Task task) {
        return String.format(
                "[%s] %s\n    Description: %s\n    Due Date: %s",
                task.done ? "x" : " ",
                task.title,
                task.description,
                task.dueDate);
    }

    private static void fetchAndPrintTasks() {

    }

    private static void printTasks(Collection<Task> tasks) {
        for (var task : tasks) {
            System.out.println();
            System.out.println(taskToString(task));
        }
    }

    private static void processMenu() {
        while (true) {
            System.out.println();
            System.out.println("1) Fetch and print tasks list");
            System.out.println("2) Create task");
            System.out.println("3) Update task by ID");
            System.out.println("4) Remove task by ID");
            System.out.println("5) Change database credentials");
            System.out.println("0) Quit");
            System.out.println();
            System.out.println("Your choice: ");

            var line = scanner.nextLine();

            if (line == "1")
                fetchAndPrintTasks();
            else if (line == "2")
                createTask();
            else if (line == "3")
                updateTaskMenu();
            else if (line == "4")
                removeTaskMenu();
            else if (line == "5")
                updateDatabaseCreds();
            else if (line == "0")
                break;
        }
    }

    private static void updateDatabaseCreds() {
    }

    private static void removeTaskMenu() {
    }

    private static void updateTaskMenu() {
    }

    private static void createTask() {
    }

    public static void main(String[] args) {
        processMenu();
    }
}
