/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tristan attard flores
 */
// The TaskManager class manages a list of tasks and provides methods to add, edit, and delete tasks
public class TaskManager implements Serializable {
    private List<Task> tasks;

    // Constructor to create a TaskManager with a list of tasks
    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Method to add a new task to the list
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Method to edit an existing task in the list
    public void editTask(int index, Task task) {
        tasks.set(index, task);
    }

    // Method to delete an existing task from the list
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    // Getter method to retrieve the list of tasks
    public List<Task> getTasks() {
        return tasks;
    }
}