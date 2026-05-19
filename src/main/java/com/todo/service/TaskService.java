package com.todo.service;

import com.todo.model.Priority;
import com.todo.model.Task;
import com.todo.storage.FileStorage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    private final FileStorage storage;
    private List<Task> tasks;

    public TaskService(FileStorage storage) throws IOException {
        this.storage = storage;
        this.tasks = storage.load();
    }

    public Task addTask(String title, Priority priority) throws IOException {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
        Task task = new Task(UUID.randomUUID().toString(), title.trim(), priority);
        tasks.add(task);
        storage.save(tasks);
        return task;
    }

    public boolean removeTask(String id) throws IOException {
        boolean removed = tasks.removeIf(t -> t.getId().equals(id));
        if (removed) storage.save(tasks);
        return removed;
    }

    public boolean completeTask(String id) throws IOException {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.setCompleted(true);
                storage.save(tasks);
                return true;
            }
        }
        return false;
    }

    public List<Task> getAllTasks() { return tasks; }

    public List<Task> getPendingTasks() {
        return tasks.stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream().filter(Task::isCompleted).collect(Collectors.toList());
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return tasks.stream().filter(t -> t.getPriority() == priority).collect(Collectors.toList());
    }
}