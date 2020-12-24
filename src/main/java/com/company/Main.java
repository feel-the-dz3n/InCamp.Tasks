package com.company;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DatabaseFactory database;
    static TasksRepository tasksRepository;

    public static String taskToString(Task task) {
        return String.format(
                "%-3d. [%s] %s (%s)\n%s\n",
                task.id,
                task.done ? "x" : " ",
                task.title,
                formatDueDate(task.dueDate),
                task.description);
    }

    private static String formatDueDate(LocalDate dueDate) {
        return dueDate.toString(); // FIXME: out example: Aug 25
    }

    private static void tasksMenu() {
        Collection<Task> tasks;

        try {
            tasks = tasksRepository.fetchTasks();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        printTasks(tasks);

        while (true) {
            System.out.println();
            System.out.println("Type task' ID to mark task");
            System.out.println("Type 'edit ID' to edit some task");
            System.out.println("Type 'remove ID' to remove some task");
            System.out.println("Type anything else to go back");

            var line = scanner.nextLine();
            try {
                Integer id;

                boolean isEdit = line.startsWith("edit"), isRemove = line.startsWith("remove");

                if (isEdit || isRemove) {
                    var split = line.split(" ");
                    id = Integer.parseInt(split[1]);

                    for (var task : tasks) {
                        if (task.id == id) {
                            if (isEdit) {
                                editTask(task);
                                tasksRepository.updateTask(task);
                            } else if (isRemove) {
                                tasksRepository.removeTask(task);
                            }
                            break;
                        }
                    }
                } else {
                    id = Integer.parseInt(line);

                    for (var task : tasks) {
                        if (task.id == id) {
                            task.done = !task.done;
                            tasksRepository.updateTask(task);
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            break;
        }
    }

    private static String askInput(String prompt) {
        return askInput(prompt, null, false);
    }

    private static String askInput(String prompt, String secondaryPrompt, boolean askSecondary) {
        System.out.println("\n" + prompt);

        if (askSecondary)
            System.out.println(secondaryPrompt);

        return scanner.nextLine();
    }

    private static void editTask(Task task) {
        String line;

        line = askInput(
                "Type the title for the task",
                "(press enter for '" + task.title + "')",
                !task.title.isEmpty());
        if (!line.isEmpty()) task.title = line;

        line = askInput(
                "Type the description for the task",
                "(press enter for '" + task.description + "')",
                !task.description.isEmpty());
        if (!line.isEmpty()) task.description = line;

        line = askInput(
                "Type the due date for the task",
                "(press enter for '" + task.dueDate + "')",
                task.dueDate != null);
        if (!line.isEmpty()) task.dueDate = LocalDate.parse(line);
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
            System.out.println("3) Change database credentials");
            System.out.println("4) Quit");
            System.out.println();
            System.out.println("Your choice: ");

            var line = scanner.nextLine();
            Integer menuEntry = Integer.parseInt(line);

            if (menuEntry == 1)
                tasksMenu();
            else if (menuEntry == 2)
                createTask();
            else if (menuEntry == 3)
                updateDatabaseCreds();
            else if (menuEntry == 4)
                break;
        }
    }

    private static void updateDatabaseCreds() {
        database = new DatabaseFactory(
                askInput("Driver name"),
                askInput("Database URL"),
                askInput("Database username"),
                askInput("Database password"));

        tasksRepository = new TasksRepository(database);
    }

    private static void createTask() {
        var task = new Task();
        editTask(task);
        try {
            tasksRepository.addTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        database = new DatabaseFactory(
                "org.postgresql.Driver",
                "jdbc:postgresql://127.0.0.1:5432/",
                "postgres",
                "123456");

        tasksRepository = new TasksRepository(database);

        processMenu();
    }
}
