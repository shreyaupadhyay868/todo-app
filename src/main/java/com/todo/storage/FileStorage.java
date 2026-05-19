package com.todo.storage;

import com.todo.model.Task;
import java.io.*;
import java.util.*;

public class FileStorage {
    private final String filePath;

    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    public void save(List<Task> tasks) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(tasks);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Task> load() throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            return (List<Task>) ois.readObject();
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}