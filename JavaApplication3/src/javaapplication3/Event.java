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

// The Event class represents an event with a title and description
public class Event implements Serializable {
    private String title;
    private String description;

    // Constructor to create a new event with a title and description
    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getter method to retrieve the event title
    public String getTitle() {
        return title;
    }

    // Setter method to set the event title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method to retrieve the event description
    public String getDescription() {
        return description;
    }

    // Setter method to set the event description
    public void setDescription(String description) {
        this.description = description;
    }

    // Override the toString method to provide a string representation of the event
    @Override
    public String toString() {
        return title;
    }
}