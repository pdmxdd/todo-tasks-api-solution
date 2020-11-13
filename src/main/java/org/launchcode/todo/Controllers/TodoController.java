package org.launchcode.todo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.launchcode.todo.Models.IncomingTodoItem;
import org.launchcode.todo.Models.OutgoingTodoItem;
import org.launchcode.todo.Models.TodoItem;
import org.launchcode.todo.data.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/todos")
@Api(value = "Operations on Todo Items", produces = "application/json")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @ApiOperation(
        value = "Get all Todo Items",
        notes = "Returns a List of all Todo Items",
        response = OutgoingTodoItem.class,
        responseContainer = "List",
        code = 200,
        produces = "application/json"
    )
    @GetMapping
    public ResponseEntity<Object> getTodos() {
        List<TodoItem> todoItems = todoRepository.findAll();
        List<OutgoingTodoItem> outgoingItems = new ArrayList<>();
        for(TodoItem todoItem : todoItems) {
            outgoingItems.add(OutgoingTodoItem.outgoingTodoItemFromTodoItem(todoItem));
        }
        return ResponseEntity.status(200).body(outgoingItems);
    }

    @ApiOperation(
        value = "Get Todo Item by ID",
        notes = "Returns a Todo Item matching ID",
        response = OutgoingTodoItem.class,
        code = 200,
        produces = "application/json"
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTodoById(@PathVariable int id) {
        Optional<TodoItem> todoItem = todoRepository.findById(id);
        if(todoItem.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TodoItem item = todoItem.get();
        OutgoingTodoItem outgoingItem = OutgoingTodoItem.outgoingTodoItemFromTodoItem(item);
        return ResponseEntity.status(200).body(outgoingItem);
    }

    @ApiOperation(
        value = "Create a Todo Item",
        notes = "Creates a Todo Item using an incoming HTTP Request body",
        response = OutgoingTodoItem.class,
        code = 201,
        produces = "application/json"
    )
    @PostMapping
    public ResponseEntity<Object> postTodo(@RequestBody IncomingTodoItem todoDto) {
        TodoItem todoItem = TodoItem.createItem(todoDto.getText());
        TodoItem updatedItem = todoRepository.save(todoItem);
        OutgoingTodoItem outgoing = OutgoingTodoItem.outgoingTodoItemFromTodoItem(updatedItem);
        return ResponseEntity.status(201).body(outgoing);
    }

    @ApiOperation(
        value = "Update Todo Item",
        notes = "Change a Todo Item completed property to true",
        response = OutgoingTodoItem.class,
        code = 200,
        produces = "application/json"
    )
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> patchTodo(@PathVariable int id) {
        Optional<TodoItem> todoItem = todoRepository.findById(id);
        if(todoItem.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        TodoItem updatedTodo = todoItem.get().markAsComplete();
        todoRepository.save(updatedTodo);
        OutgoingTodoItem outgoingTodo = OutgoingTodoItem.outgoingTodoItemFromTodoItem(updatedTodo);
        return ResponseEntity.status(200).body(outgoingTodo);
    }

    @ApiOperation(
        value = "Delete Todo Item",
        notes = "Delete Todo Item that matches ID",
        code = 204
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable int id) {
        Optional<TodoItem> todoItem = todoRepository.findById(id);
        if(todoItem.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        todoRepository.delete(todoItem.get());
        return ResponseEntity.status(204).build();
    }
    
}
