    package com.sidecar.demo.controller;

    import com.sidecar.demo.entity.TodoItem;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.concurrent.atomic.AtomicLong;

    @RestController
    @RequestMapping("/api/todos")
    public class TodoController {
        private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
        private final Map<Long, TodoItem> todos = new HashMap<>();
        private final AtomicLong counter = new AtomicLong();

        // Get all todos
        @GetMapping
        public ResponseEntity<List<TodoItem>> getAllTodos() {
            logger.info("Getting all todo items");
            List<TodoItem> todoList = new ArrayList<>(todos.values());
            return ResponseEntity.ok(todoList);
        }

        // Get todo by ID
        @GetMapping("/{id}")
        public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
            logger.info("Getting todo item with ID: {}", id);
            TodoItem item = todos.get(id);
            if (item == null) {
                logger.error("Todo item with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returning 404 for not found
            }
            return ResponseEntity.ok(item);
        }

        // Create a new todo
        @PostMapping
        public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {
            Long id = counter.incrementAndGet();
            todoItem.setId(id);
            todos.put(id, todoItem);
            logger.info("Created new todo item with ID: {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(todoItem); // Returning 201 for resource creation
        }

        // Update an existing todo
        @PutMapping("/{id}")
        public ResponseEntity<TodoItem> updateTodo(@PathVariable Long id, @RequestBody TodoItem todoItem) {
            if (!todos.containsKey(id)) {
                logger.error("Failed to update. Todo item with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returning 404 for not found
            }
            todoItem.setId(id);
            todos.put(id, todoItem);
            logger.info("Updated todo item with ID: {}", id);
            return ResponseEntity.ok(todoItem); // Returning updated item
        }

        // Delete a todo
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
            if (todos.remove(id) != null) {
                logger.info("Deleted todo item with ID: {}", id);
                return ResponseEntity.noContent().build(); // Returning 204 for successful deletion
            } else {
                logger.error("Failed to delete. Todo item with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returning 404 for not found
            }
        }
    }
