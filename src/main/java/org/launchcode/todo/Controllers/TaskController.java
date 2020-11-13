package org.launchcode.todo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.launchcode.todo.Models.Task;
import org.launchcode.todo.Models.TaskDto;
import org.launchcode.todo.Models.TodoItem;
import org.launchcode.todo.data.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/todos/{id}/tasks")
@Api(value = "Operations on a Todo Item's sub-tasks", produces = "application/json")
public class TaskController {
    
    @Autowired
    TodoRepository todoRepository;

    @ApiOperation(
        value = "Get Todo Item's tasks",
        notes = "Get a list of tasks associated with Todo Item matching ID",
        response = TaskDto.class,
        responseContainer = "List",
        produces = "application/json"
    )
    @GetMapping
    public ResponseEntity<Object> getTodoTasks(@PathVariable int id) {
        Optional<TodoItem> optionalTodoItem = todoRepository.findById(id);
        if(optionalTodoItem.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        TodoItem foundTodoItem = optionalTodoItem.get();
        List<Task> tasksAssociatedWithFoundTodoItem = foundTodoItem.getTasks();

        List<TaskDto> taskDtos = new ArrayList<>();
        for(Task task : tasksAssociatedWithFoundTodoItem) {
            taskDtos.add(TaskDto.buildDtoFromTask(task));
        }

        return ResponseEntity.status(200).body(taskDtos);
    }

    @ApiOperation(
        value = "Add a Task to a Todo Item",
        notes = "Add a task to a Todo Item matching ID"
    )
    @PostMapping
    public ResponseEntity<Object> putTodoTasks(@PathVariable int id, @RequestBody TaskDto taskDto) {
        
        Optional<TodoItem> optionalTodoItem = todoRepository.findById(id);

        if(optionalTodoItem.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        TodoItem todoItem = optionalTodoItem.get();
        Task task = new Task(taskDto.getTitle(), todoItem);
        todoItem.addTask(task);
        todoRepository.save(todoItem);

        return ResponseEntity.status(201).build();
    }
}
