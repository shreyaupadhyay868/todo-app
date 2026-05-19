package com.todo;

import com.todo.model.Priority;
import com.todo.model.Task;
import com.todo.service.TaskService;
import com.todo.storage.FileStorage;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private TaskService service;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("todo-test", ".dat");
        service = new TaskService(new FileStorage(tempFile.toString()));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void addTask_shouldCreateTaskWithTitle() throws IOException {
        Task task = service.addTask("Buy milk", Priority.MEDIUM);
        assertEquals("Buy milk", task.getTitle());
        assertFalse(task.isCompleted());
        assertNotNull(task.getId());
        assertEquals(Priority.MEDIUM, task.getPriority());
    }

    @Test
    void addTask_shouldThrowOnEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> service.addTask("", Priority.LOW));
    }

    @Test
    void removeTask_shouldReturnTrueWhenFound() throws IOException {
        Task task = service.addTask("Buy milk", Priority.LOW);
        assertTrue(service.removeTask(task.getId()));
        assertEquals(0, service.getAllTasks().size());
    }

    @Test
    void removeTask_shouldReturnFalseWhenNotFound() throws IOException {
        assertFalse(service.removeTask("nonexistent"));
    }

    @Test
    void completeTask_shouldMarkAsComplete() throws IOException {
        Task task = service.addTask("Buy milk", Priority.HIGH);
        service.completeTask(task.getId());
        assertTrue(service.getAllTasks().get(0).isCompleted());
    }

    @Test
    void getPendingTasks_shouldExcludeCompleted() throws IOException {
        Task t1 = service.addTask("Buy milk", Priority.HIGH);
        service.addTask("Call dentist", Priority.LOW);
        service.completeTask(t1.getId());
        List<Task> pending = service.getPendingTasks();
        assertEquals(1, pending.size());
        assertEquals("Call dentist", pending.get(0).getTitle());
    }

    @Test
    void getTasksByPriority_shouldFilterCorrectly() throws IOException {
        service.addTask("Buy milk", Priority.HIGH);
        service.addTask("Call dentist", Priority.LOW);
        service.addTask("Do laundry", Priority.HIGH);
        List<Task> high = service.getTasksByPriority(Priority.HIGH);
        assertEquals(2, high.size());
    }

    @Test
    void persistence_shouldSurviveReload() throws IOException {
        Task task = service.addTask("Buy milk", Priority.HIGH);
        service.completeTask(task.getId());
        TaskService reloaded = new TaskService(new FileStorage(tempFile.toString()));
        List<Task> tasks = reloaded.getAllTasks();
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).isCompleted());
        assertEquals(Priority.HIGH, tasks.get(0).getPriority());
    }
}