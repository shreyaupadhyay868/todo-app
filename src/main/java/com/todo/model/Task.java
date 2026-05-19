package com.todo.model;

import java.io.Serializable;

public class Task implements Serializable {
    private String id;
    private String title;
    private boolean completed;
    private Priority priority;

    public Task(String id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.completed = false;
        this.priority = priority;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public Priority getPriority() { return priority; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return (completed ? "[X] " : "[ ] ") + "[" + priority + "] " + title + " (id=" + id + ")";
    }
}