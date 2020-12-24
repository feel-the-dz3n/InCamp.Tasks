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
        throw new Exception("Not implemented");
    }

    public Task updateTask(Task task) throws Exception {
        throw new Exception("Not implemented");
    }

    public void removeTask(Integer id) throws Exception {
        throw new Exception("Not implemented");
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
