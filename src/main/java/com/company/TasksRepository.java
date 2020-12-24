package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public class TasksRepository {
    private DatabaseFactory database;

    public TasksRepository(DatabaseFactory database) {
        this.database = database;
    }

    public Task addTask(Task task) throws Exception {
        var conn = database.connect();

        var stmt = conn.prepareStatement("INSERT INTO public.tasks(\"Title\", \"Description\", \"IsDone\", \"DueDate\") VALUES (?, ?, ?, ?) RETURNING \"Id\";");
        stmt.setString(1, task.title);
        stmt.setString(2, task.description);
        stmt.setBoolean(3, task.done);
        stmt.setDate(4, Date.valueOf(task.dueDate));

        int id = stmt.executeUpdate();
        task.id = id;

        stmt.close();
        conn.close();

        return task;
    }

    public Task updateTask(Task task) throws Exception {
        var conn = database.connect();

        var stmt = conn.prepareStatement(String.format(
                "UPDATE public.tasks SET \"Title\"=?, \"Description\"=?, \"IsDone\"=?, \"DueDate\"=? WHERE \"Id\"=%d;",
                task.id));

        stmt.setString(1, task.title);
        stmt.setString(2, task.description);
        stmt.setBoolean(3, task.done);
        stmt.setDate(4, Date.valueOf(task.dueDate));

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return task;
    }

    public void removeTask(Integer id) throws Exception {
        var conn = database.connect();
        var stmt = conn.createStatement();

        stmt.execute(String.format("DELETE FROM public.tasks WHERE \"Id\" = %d;", id));

        stmt.close();
        conn.close();
    }

    public void removeTask(Task task) throws Exception {
        removeTask(task.id);
    }

    public Collection<Task> fetchTasks() throws Exception {
        HashSet<Task> employees = new HashSet<>();
        var conn = database.connect();

        var stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.tasks");

        while (rs.next()) {
            employees.add(new Task(
                    rs.getInt("Id"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getBoolean("IsDone"),
                    convertToLocalDateViaSqlDate(rs.getDate("DueDate"))
            ));
        }

        stmt.close();
        conn.close();

        return employees;
    }

    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
