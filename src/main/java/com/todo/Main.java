package com.todo;

import com.todo.model.Task;
import com.todo.service.TaskService;
import com.todo.storage.FileStorage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        try {
            FileStorage storage = new FileStorage("tasks.dat");
            TaskService service = new TaskService(storage);
            String command = args[0].toLowerCase();

            switch (command) {
                case "add"      -> handleAdd(service, args);
                case "list"     -> handleList(service, args);
                case "complete" -> handleComplete(service, args);
                case "remove"   -> handleRemove(service, args);
                default -> {
                    System.out.println("Unknown command: " + command);
                    printHelp();
                }
            }
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }

    private static void handleAdd(TaskService service,
                                   String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: todo add <title>");
            return;
        }
        String title = String.join(" ",
            Arrays.copyOfRange(args, 1, args.length));
        Task task = service.addTask(title);
        System.out.println("Added: " + task);
    }

    private static void handleList(TaskService service,
                                    String[] args) throws IOException {
        String filter = args.length > 1 ? args[1].toLowerCase() : "all";
        List<Task> tasks = switch (filter) {
            case "pending"   -> service.getPendingTasks();
            case "completed" -> service.getCompletedTasks();
            default          -> service.getAllTasks();
        };

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        tasks.forEach(System.out::println);
        System.out.println("--- " + tasks.size() + " task(s) ---");
    }

    private static void handleComplete(TaskService service,
                                        String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: todo complete <id>");
            return;
        }
        boolean done = service.completeTask(args[1]);
        System.out.println(done
            ? "Marked complete: " + args[1]
            : "Task not found: " + args[1]);
    }

    private static void handleRemove(TaskService service,
                                      String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: todo remove <id>");
            return;
        }
        boolean removed = service.removeTask(args[1]);
        System.out.println(removed
            ? "Removed: " + args[1]
            : "Task not found: " + args[1]);
    }

    private static void printHelp() {
        System.out.println("Todo App — commands:");
        System.out.println("  todo add <title>         Add a new task");
        System.out.println("  todo list                List all tasks");
        System.out.println("  todo list pending        List incomplete tasks");
        System.out.println("  todo list completed      List completed tasks");
        System.out.println("  todo complete <id>       Mark a task done");
        System.out.println("  todo remove <id>         Delete a task");
    }
}