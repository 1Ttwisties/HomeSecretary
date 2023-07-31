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
// The EventManager class manages a list of events and provides methods to add, edit, and delete events
public class EventManager implements Serializable {
    private List<Event> events;

    // Constructor to create an EventManager with a list of events
    public EventManager(List<Event> events) {
        this.events = events;
    }

    // Method to add a new event to the list
    public void addEvent(Event event) {
        events.add(event);
    }

    // Method to edit an existing event in the list
    public void editEvent(int index, Event event) {
        events.set(index, event);
    }

    // Method to delete an existing event from the list
    public void deleteEvent(int index) {
        events.remove(index);
    }

    // Getter method to retrieve the list of events
    public List<Event> getEvents() {
        return events;
    }
}