package com.company;

import java.time.LocalDate;

public class Task {
    public Integer id;
    public String title;
    public String description;
    public boolean done;
    public LocalDate dueDate;

    public Task(Integer id, String title, String desc, boolean done, LocalDate dueDate) {
        this(title, desc, done, dueDate);
        this.id = id;
    }

    public Task(String title, String desc, boolean done, LocalDate dueDate) {
        this.title = title;
        this.description = desc;
        this.done = done;
        this.dueDate = dueDate;
    }
}
