package com.todo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {

    private String id;
    private String title;
    private boolean completed;
    private LocalDateTime createdAt;

    public Task(String title) {
        this.id        = UUID.randomUUID().toString().substring(0, 8);
        this.title     = title;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public Task(String id, String title,
                boolean completed, LocalDateTime createdAt) {
        this.id        = id;
        this.title     = title;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public String getId()               { return id; }
    public String getTitle()            { return title; }
    public boolean isCompleted()        { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setTitle(String title)     { this.title = title; }
    public void setCompleted(boolean done) { this.completed = done; }

    @Override
    public String toString() {
        String status = completed ? "[x]" : "[ ]";
        return status + " " + title + " (ID: " + id + ")";
    }
}