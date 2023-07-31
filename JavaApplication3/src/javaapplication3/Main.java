package javaapplication3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tristan attard flores
 */

public class Main {
    // Global variables to store events and tasks
    private static List<Event> events = new ArrayList<>();
    private static List<Task> tasks = new ArrayList<>();

    // Global variables for selected event and task
    private static Event selectedEvent;
    private static Task selectedTask;

    // Global variables for GUI components
    private static DefaultListModel<Event> eventListModel;
    private static JList<Event> eventList;
    private static DefaultListModel<Task> taskListModel;
    private static JList<Task> taskList;
    private static JTextField selectedEventTitleField;
    private static JTextArea selectedEventDescriptionArea;
    private static JTextField selectedTaskTitleField;
    private static JTextArea selectedTaskDescriptionArea;
    private static JButton editEventButton;
    private static JButton deleteEventButton;
    private static JButton editTaskButton;
    private static JButton deleteTaskButton;

    // Global constant for data file
    private static final String DATA_FILE = "data.dat";

    public static void main(String[] args) {
        // Load data from file (if available)
        loadData(events, tasks);

        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("Event and Task Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Create the tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Create the event panel
            JPanel eventPanel = new JPanel(new GridBagLayout());
            GridBagConstraints eventConstraints = new GridBagConstraints();
            eventConstraints.anchor = GridBagConstraints.WEST;
            eventConstraints.insets = new Insets(5, 5, 5, 5);

            // Create fields for entering event details
            JTextField eventTitleField = new JTextField(20);
            JTextArea eventDescriptionArea = new JTextArea(5, 20);

            // Button to add a new event
            JButton addEventButton = new JButton("Add Event");
            // Button to display events
            JButton displayEventsButton = new JButton("Display Events");

            // ActionListener for the addEventButton
            addEventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String title = eventTitleField.getText();
                    String description = eventDescriptionArea.getText();

                    if (!title.isEmpty()) {
                        Event newEvent = new Event(title, description);
                        events.add(newEvent);
                        eventTitleField.setText("");
                        eventDescriptionArea.setText("");
                        updateEventList(eventListModel, events);
                        saveData(events, tasks);
                        System.out.println("Event added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a title for the event.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // ActionListener for the displayEventsButton
            displayEventsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StringBuilder eventsString = new StringBuilder();
                    for (Event event : events) {
                        eventsString.append(event.getTitle()).append(": ").append(event.getDescription()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, eventsString.toString(), "Events", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            // Layout for Event panel
            eventConstraints.gridx = 0;
            eventConstraints.gridy = 0;
            eventPanel.add(new JLabel("Event Title:"), eventConstraints);
            eventConstraints.gridx++;
            eventPanel.add(eventTitleField, eventConstraints);
            eventConstraints.gridx = 0;
            eventConstraints.gridy++;
            eventConstraints.gridwidth = 2;
            eventPanel.add(new JLabel("Event Description:"), eventConstraints);
            eventConstraints.gridx = 0;
            eventConstraints.gridy++;
            eventConstraints.gridwidth = 2;
            eventPanel.add(new JScrollPane(eventDescriptionArea), eventConstraints);
            eventConstraints.gridx = 0;
            eventConstraints.gridy++;
            eventConstraints.gridwidth = 1;
            eventPanel.add(addEventButton, eventConstraints);
            eventConstraints.gridx++;
            eventPanel.add(displayEventsButton, eventConstraints); // Add the display button

            // Create the event list and add a ListSelectionListener to update the selected event details
            eventListModel = new DefaultListModel<>();
            updateEventList(eventListModel, events);
            eventList = new JList<>(eventListModel);
            eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane eventScrollPane = new JScrollPane(eventList);

            // Add the ListSelectionListener for events
            eventList.addListSelectionListener(e -> {
                int selectedIndex = eventList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    // Update the selectedEvent with the selected event details
                    selectedEvent = events.get(selectedIndex);
                    updateSelectedEvent(selectedEvent);
                } else {
                    // Clear the selectedEvent if no event is selected
                    selectedEvent = null;
                    updateSelectedEvent(null);
                }
                // Enable or disable the edit and delete buttons based on selection
                editEventButton.setEnabled(selectedIndex >= 0);
                deleteEventButton.setEnabled(selectedIndex >= 0);
            });

            // Add the event list to the event panel
            eventConstraints.gridy = 1;
            eventPanel.add(eventScrollPane, eventConstraints);

            // Create the task panel
            JPanel taskPanel = new JPanel(new GridBagLayout());
            GridBagConstraints taskConstraints = new GridBagConstraints();
            taskConstraints.anchor = GridBagConstraints.WEST;
            taskConstraints.insets = new Insets(5, 5, 5, 5);

            // Create fields for entering task details
            JTextField taskTitleField = new JTextField(20);
            JTextArea taskDescriptionArea = new JTextArea(5, 20);

            // Button to add a new task
            JButton addTaskButton = new JButton("Add Task");
            // Button to display tasks
            JButton displayTasksButton = new JButton("Display Tasks");

            // ActionListener for the addTaskButton
            addTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String title = taskTitleField.getText();
                    String description = taskDescriptionArea.getText();

                    if (!title.isEmpty()) {
                        Task newTask = new Task(title);
                        newTask.setDescription(description);
                        tasks.add(newTask);
                        taskTitleField.setText("");
                        taskDescriptionArea.setText("");
                        updateTaskList(taskListModel, tasks);
                        saveData(events, tasks);
                        System.out.println("Task added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a title for the task.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // ActionListener for the displayTasksButton
            displayTasksButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StringBuilder tasksString = new StringBuilder();
                    for (Task task : tasks) {
                        tasksString.append(task.getTitle()).append(": ").append(task.getDescription()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, tasksString.toString(), "Tasks", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            // Layout for Task panel
            taskConstraints.gridx = 0;
            taskConstraints.gridy = 0;
            taskPanel.add(new JLabel("Task Title:"), taskConstraints);
            taskConstraints.gridx++;
            taskPanel.add(taskTitleField, taskConstraints);
            taskConstraints.gridx = 0;
            taskConstraints.gridy++;
            taskConstraints.gridwidth = 2;
            taskPanel.add(new JLabel("Task Description:"), taskConstraints);
            taskConstraints.gridx = 0;
            taskConstraints.gridy++;
            taskConstraints.gridwidth = 2;
            taskPanel.add(new JScrollPane(taskDescriptionArea), taskConstraints);
            taskConstraints.gridx = 0;
            taskConstraints.gridy++;
            taskConstraints.gridwidth = 1;
            taskPanel.add(addTaskButton, taskConstraints);
            taskConstraints.gridx++;
            taskPanel.add(displayTasksButton, taskConstraints); // Add the display button

            // Create the task list and add a ListSelectionListener to update the selected task details
            taskListModel = new DefaultListModel<>();
            updateTaskList(taskListModel, tasks);
            taskList = new JList<>(taskListModel);
            taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane taskScrollPane = new JScrollPane(taskList);

            // Add the ListSelectionListener for tasks
            taskList.addListSelectionListener(e -> {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    // Update the selectedTask with the selected task details
                    selectedTask = tasks.get(selectedIndex);
                    updateSelectedTask(selectedTask);
                } else {
                    // Clear the selectedTask if no task is selected
                    selectedTask = null;
                    updateSelectedTask(null);
                }
                // Enable or disable the edit and delete buttons based on selection
                editTaskButton.setEnabled(selectedIndex >= 0);
                deleteTaskButton.setEnabled(selectedIndex >= 0);
            });

            // Add the task list to the task panel
            taskConstraints.gridy = 1;
            taskPanel.add(taskScrollPane, taskConstraints);

            // Create the selected event and task panel
            JPanel selectedEventPanel = new JPanel(new GridBagLayout());
            GridBagConstraints selectedEventConstraints = new GridBagConstraints();
            selectedEventConstraints.anchor = GridBagConstraints.WEST;
            selectedEventConstraints.insets = new Insets(5, 5, 5, 5);

            // Fields to display and edit the selected event details
            selectedEventTitleField = new JTextField(20);
            selectedEventTitleField.setEditable(false);
            selectedEventDescriptionArea = new JTextArea(5, 20);

            // Buttons to edit and delete the selected event
            editEventButton = new JButton("Edit Event");
            deleteEventButton = new JButton("Delete Event");

            // ActionListener for the editEventButton
            editEventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedEvent != null) {
                        // Show input dialogs to get the new title and description
                        String newTitle = JOptionPane.showInputDialog(frame, "Enter Event Title:",
                                selectedEvent.getTitle());
                        String newDescription = JOptionPane.showInputDialog(frame, "Enter Event Description:",
                                selectedEvent.getDescription());

                        if (newTitle != null && !newTitle.isEmpty()) {
                            // Update the selected event with the new title and description
                            selectedEvent.setTitle(newTitle);
                            selectedEvent.setDescription(newDescription);
                            updateEventList(eventListModel, events);
                            saveData(events, tasks);
                            System.out.println("Event edited successfully.");
                        } else {
                            // Show an error message if the new title is empty
                            JOptionPane.showMessageDialog(frame, "Please enter a title for the event.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Show an error message if no event is selected for editing
                        JOptionPane.showMessageDialog(frame, "Please select an event to edit.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // ActionListener for the deleteEventButton
            deleteEventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = eventList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        // Remove the selected event from the events list
                        events.remove(selectedIndex);
                        updateEventList(eventListModel, events);
                        saveData(events, tasks);
                        System.out.println("Event deleted successfully.");
                    } else {
                        // Show an error message if no event is selected for deletion
                        JOptionPane.showMessageDialog(frame, "Please select an event to delete.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Layout for Selected Event panel
            selectedEventConstraints.gridx = 0;
            selectedEventConstraints.gridy = 0;
            selectedEventPanel.add(new JLabel("Selected Event Title:"), selectedEventConstraints);
            selectedEventConstraints.gridx++;
            selectedEventPanel.add(selectedEventTitleField, selectedEventConstraints);
            selectedEventConstraints.gridx = 0;
            selectedEventConstraints.gridy++;
            selectedEventConstraints.gridwidth = 2;
            selectedEventPanel.add(new JLabel("Selected Event Description:"), selectedEventConstraints);
            selectedEventConstraints.gridx = 0;
            selectedEventConstraints.gridy++;
            selectedEventConstraints.gridwidth = 2;
            selectedEventPanel.add(new JScrollPane(selectedEventDescriptionArea), selectedEventConstraints);
            selectedEventConstraints.gridx = 0;
            selectedEventConstraints.gridy++;
            selectedEventConstraints.gridwidth = 1;
            selectedEventPanel.add(editEventButton, selectedEventConstraints);
            selectedEventConstraints.gridx++;
            selectedEventPanel.add(deleteEventButton, selectedEventConstraints);

            // Create the selected task panel
            JPanel selectedTaskPanel = new JPanel(new GridBagLayout());
            GridBagConstraints selectedTaskConstraints = new GridBagConstraints();
            selectedTaskConstraints.anchor = GridBagConstraints.WEST;
            selectedTaskConstraints.insets = new Insets(5, 5, 5, 5);

            // Fields to display and edit the selected task details
            selectedTaskTitleField = new JTextField(20);
            selectedTaskTitleField.setEditable(false);
            selectedTaskDescriptionArea = new JTextArea(5, 20);

            // Buttons to edit and delete the selected task
            editTaskButton = new JButton("Edit Task");
            deleteTaskButton = new JButton("Delete Task");

            // ActionListener for the editTaskButton
            editTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedTask != null) {
                        // Show input dialogs to get the new title and description
                        String newTitle = JOptionPane.showInputDialog(frame, "Enter Task Title:",
                                selectedTask.getTitle());
                        String newDescription = JOptionPane.showInputDialog(frame, "Enter Task Description:",
                                selectedTask.getDescription());

                        if (newTitle != null && !newTitle.isEmpty()) {
                            // Update the selected task with the new title and description
                            selectedTask.setTitle(newTitle);
                            selectedTask.setDescription(newDescription);
                            updateTaskList(taskListModel, tasks);
                            saveData(events, tasks);
                            System.out.println("Task edited successfully.");
                        } else {
                            // Show an error message if the new title is empty
                            JOptionPane.showMessageDialog(frame, "Please enter a title for the task.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Show an error message if no task is selected for editing
                        JOptionPane.showMessageDialog(frame, "Please select a task to edit.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // ActionListener for the deleteTaskButton
            deleteTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = taskList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        // Remove the selected task from the tasks list
                        tasks.remove(selectedIndex);
                        updateTaskList(taskListModel, tasks);
                        saveData(events, tasks);
                        System.out.println("Task deleted successfully.");
                    } else {
                        // Show an error message if no task is selected for deletion
                        JOptionPane.showMessageDialog(frame, "Please select a task to delete.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Layout for Selected Task panel
            selectedTaskConstraints.gridx = 0;
            selectedTaskConstraints.gridy = 0;
            selectedTaskPanel.add(new JLabel("Selected Task Title:"), selectedTaskConstraints);
            selectedTaskConstraints.gridx++;
            selectedTaskPanel.add(selectedTaskTitleField, selectedTaskConstraints);
            selectedTaskConstraints.gridx = 0;
            selectedTaskConstraints.gridy++;
            selectedTaskConstraints.gridwidth = 2;
            selectedTaskPanel.add(new JLabel("Selected Task Description:"), selectedTaskConstraints);
            selectedTaskConstraints.gridx = 0;
            selectedTaskConstraints.gridy++;
            selectedTaskConstraints.gridwidth = 2;
            selectedTaskPanel.add(new JScrollPane(selectedTaskDescriptionArea), selectedTaskConstraints);
            selectedTaskConstraints.gridx = 0;
            selectedTaskConstraints.gridy++;
            selectedTaskConstraints.gridwidth = 1;
            selectedTaskPanel.add(editTaskButton, selectedTaskConstraints);
            selectedTaskConstraints.gridx++;
            selectedTaskPanel.add(deleteTaskButton, selectedTaskConstraints);

            // Add the panels to the tabbed pane
            tabbedPane.addTab("Events", eventPanel);
            tabbedPane.addTab("Tasks", taskPanel);
            tabbedPane.addTab("Selected Event", selectedEventPanel);
            tabbedPane.addTab("Selected Task", selectedTaskPanel);

            // Add the tabbed pane to the frame and display the GUI
            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }

    // Helper method to update the event list model
    private static void updateEventList(DefaultListModel<Event> listModel, List<Event> eventList) {
        listModel.clear();
        for (Event event : eventList) {
            listModel.addElement(event);
        }
    }

    // Helper method to update the task list model
    private static void updateTaskList(DefaultListModel<Task> listModel, List<Task> taskList) {
        listModel.clear();
        for (Task task : taskList) {
            listModel.addElement(task);
        }
    }

    // Helper method to update the selected event details
    private static void updateSelectedEvent(Event event) {
        if (event != null) {
            selectedEventTitleField.setText(event.getTitle());
            selectedEventDescriptionArea.setText(event.getDescription());
        } else {
            selectedEventTitleField.setText("");
            selectedEventDescriptionArea.setText("");
        }
    }

    // Helper method to update the selected task details
    private static void updateSelectedTask(Task task) {
        if (task != null) {
            selectedTaskTitleField.setText(task.getTitle());
            selectedTaskDescriptionArea.setText(task.getDescription());
        } else {
            selectedTaskTitleField.setText("");
            selectedTaskDescriptionArea.setText("");
        }
    }

    // Helper method to load data from the data file
    private static void loadData(List<Event> events, List<Task> tasks) {
        try {
            FileInputStream fis = new FileInputStream(DATA_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            events.addAll((List<Event>) ois.readObject());
            tasks.addAll((List<Task>) ois.readObject());
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    // Helper method to save data to the data file
    private static void saveData(List<Event> events, List<Task> tasks) {
        try {
            FileOutputStream fos = new FileOutputStream(DATA_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(events);
            oos.writeObject(tasks);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
}
