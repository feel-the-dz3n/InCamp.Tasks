package com.company;

import java.time.LocalDate;

public class Main {

    public static String taskToString(Task task){
        return String.format(
                "[%s] %s\n    Description: %s\n    Due Date: %s",
                task.done ? "x" : " ",
                task.title,
                task.description,
                task.dueDate);
    }

    public static void main(String[] args) {
        var tasks = new Task[] {
                new Task(
                        "Complete InterLink inCamp",
                        "InterLink inCamp internship ends in 3 months",
                        false,
                        LocalDate.of(2021, 03, 10)),
                new Task(
                        "Complete school",
                        "My school studying should end in 2019",
                        true,
                        LocalDate.of(2019, 05, 1)),
                new Task(
                        "Complete ChSBC",
                        "I will be a junior specialist after 4 years of studying",
                        false,
                        LocalDate.of(2022, 04, 28)),
        };

        for (var task : tasks) {
            System.out.println();
            System.out.println(taskToString(task));
        }

    }
}
