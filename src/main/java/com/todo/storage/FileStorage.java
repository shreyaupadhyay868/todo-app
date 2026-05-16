package com.todo.storage;

import com.todo.model.Task;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private final String filePath;

    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(List<Task> tasks) throws IOException {
        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(filePath))) {
            for (Task t : tasks) {
                writer.write(String.join("|",
                    t.getId(),
                    t.getTitle().replace("|", "\\|"),
                    String.valueOf(t.isCompleted()),
                    t.getCreatedAt().toString()
                ));
                writer.newLine();
            }
        }
    }

    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return tasks;

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("(?<!\\\\)\\|");
                if (parts.length == 4) {
                    tasks.add(new Task(
                        parts[0],
                        parts[1].replace("\\|", "|"),
                        Boolean.parseBoolean(parts[2]),
                        LocalDateTime.parse(parts[3])
                    ));
                }
            }
        }
        return tasks;
    }
}