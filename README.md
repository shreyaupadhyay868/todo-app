\# CLI Todo App — Java



A command-line task manager built in Java. Tasks are saved to disk and persist between sessions.



\## What I Built



\- Add, list, complete, and remove tasks from the terminal

\- Tasks saved to a local file (`tasks.dat`) — survive program restart

\- Built with a 3-layer architecture: Model → Service → Storage



\## How to Run



\*\*Requirements:\*\* Java 17+ and Maven installed



\*\*Build:\*\*

```bash

mvn package -DskipTests

```



\*\*Commands:\*\*

```bash

java -jar target/todo.jar add Buy milk

java -jar target/todo.jar list

java -jar target/todo.jar list pending

java -jar target/todo.jar list completed

java -jar target/todo.jar complete a1b2c3d4

java -jar target/todo.jar remove a1b2c3d4

```



\## Project Structure



src/main/java/com/todo/

├── model/

│   └── Task.java          # Data class — id, title, completed, createdAt

├── service/

│   └── TaskService.java   # Business logic — add, remove, complete

├── storage/

│   └── FileStorage.java   # Reads and writes tasks.dat

└── Main.java              # CLI entry point — parses commands



\## Concepts Practised



\- Object-Oriented Programming (encapsulation, abstraction)

\- File I/O with BufferedReader / BufferedWriter

\- Java Stream API (filter, collect)

\- Dependency Injection

\- 3-layer architecture (same pattern used in Spring Boot)

\- JUnit 5 testing



\## What I Learned



This was my Week 1–2 Java project. I learned how to structure a real Java application

with separated layers, how to persist data to disk, and how to parse command-line arguments.



\## Built With



\- Java 24

\- Maven

\- JUnit 5

