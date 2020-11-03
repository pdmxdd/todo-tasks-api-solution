package org.launchcode.todo.Controllers;


import org.launchcode.todo.Models.TaskDto;
import org.launchcode.todo.data.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/todos/{id}/tasks")
public class TaskController {
    
    @Autowired
    TodoRepository todoRepository;

    @GetMapping
    public ResponseEntity<Object> getTodoTasks(@PathVariable int id) {
        

        return ResponseEntity.status(418).build();
    }

    @PostMapping
    public ResponseEntity<Object> putTodoTasks(@PathVariable int id, @RequestBody TaskDto taskDto) {
        

        return ResponseEntity.status(418).build();
    }
}
