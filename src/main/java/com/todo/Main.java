package com.todo;

import com.todo.model.Priority;
import com.todo.model.Task;
import com.todo.service.TaskService;
import com.todo.storage.FileStorage;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileStorage storage = new FileStorage("tasks.dat");
        TaskService service = new TaskService(storage);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add task\n2. List all tasks\n3. Complete task\n4. Remove task\n5. Filter by priority\n6. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Priority (HIGH/MEDIUM/LOW): ");
                    Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
                    Task t = service.addTask(title, priority);
                    System.out.println("Added: " + t);
                }
                case "2" -> {
                    List<Task> all = service.getAllTasks();
                    if (all.isEmpty()) System.out.println("No tasks.");
                    else all.forEach(System.out::println);
                }
                case "3" -> {
                    System.out.print("Task ID: ");
                    System.out.println(service.completeTask(scanner.nextLine()) ? "Done!" : "Not found.");
                }
                case "4" -> {
                    System.out.print("Task ID: ");
                    System.out.println(service.removeTask(scanner.nextLine()) ? "Removed!" : "Not found.");
                }
                case "5" -> {
                    System.out.print("Priority (HIGH/MEDIUM/LOW): ");
                    Priority p = Priority.valueOf(scanner.nextLine().toUpperCase());
                    service.getTasksByPriority(p).forEach(System.out::println);
                }
                case "6" -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}