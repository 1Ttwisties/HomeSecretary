/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication3;

import java.io.Serializable;

/**
 *
 * @author tristan attard flores
 */
// The Task class represents a task with a title and description
public class Task implements Serializable {
    private String title;
    private String description;

    // Constructor to create a new task with a title
    public Task(String title) {
        this.title = title;
    }

    // Getter method to retrieve the task title
    public String getTitle() {
        return title;
    }

    // Setter method to set the task title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method to retrieve the task description
    public String getDescription() {
        return description;
    }

    // Setter method to set the task description
    public void setDescription(String description) {
        this.description = description;
    }

    // Override the toString method to provide a string representation of the task
    @Override
    public String toString() {
        return title;
    }
}