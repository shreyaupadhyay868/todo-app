package com.todo.service;

import com.todo.model.Task;
import com.todo.storage.FileStorage;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskService {

    private List<Task> tasks;
    private final FileStorage storage;

    public TaskService(FileStorage storage) throws IOException {
        this.storage = storage;
        this.tasks = storage.loadTasks();
    }

    public Task addTask(String title) throws IOException {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        Task task = new Task(title.trim());
        tasks.add(task);
        storage.saveTasks(tasks);
        return task;
    }

    public boolean removeTask(String id) throws IOException {
        boolean removed = tasks.removeIf(t -> t.getId().equals(id));
        if (removed) {
            storage.saveTasks(tasks);
        }
        return removed;
    }

    public boolean completeTask(String id) throws IOException {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.setCompleted(true);
                storage.saveTasks(tasks);
                return true;
            }
        }
        return false;
    }

    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }

    public List<Task> getPendingTasks() {
        return tasks.stream()
            .filter(t -> !t.isCompleted())
            .collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream()
            .filter(Task::isCompleted)
            .collect(Collectors.toList());
    }
}