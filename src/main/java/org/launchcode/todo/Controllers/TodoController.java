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
// import io.swagger.annotations.ApiResponse;
// import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/todos")
@Api(value = "todo", description = "Operations about Todo Items")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @ApiOperation(
        value = "Find all Todo Items", 
        notes = "Returns all Todo Items that are currently stored in the database as OutgoingTodoItem DTOs",
        produces = "application/json",
        response = OutgoingTodoItem.class,
        responseContainer = "List")
    // @ApiResponses(value = {
    //     @ApiResponse(code = 404, message = "Todo ID not found")
    // })
    @Operation(
        summary = "Find All Todo Items",
        description = "Returns a list of all Todo Items",
        responses = {
            @ApiResponse(
                description = "List of Todo Items",
                content = @Content(schema = @Schema(implementation = OutgoingTodoItem.class))
            )
        }
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

    @Operation(
        summary = "Find todo by ID",
        description = "Returns a Todo Item by ID",
        responses = {
            @ApiResponse(
                description = "The Todo Item",
                content = @Content(schema = @Schema(implementation = OutgoingTodoItem.class))
            ),
            @ApiResponse(
                responseCode = "404", description = "Invalid Todo Item"
            ),
            @ApiResponse(
                description = "The Todo Item",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = OutgoingTodoItem.class))
            )
        }
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

    @PostMapping
    public ResponseEntity<Object> postTodo(@RequestBody IncomingTodoItem todoDto) {
        TodoItem todoItem = TodoItem.createItem(todoDto.getText());
        TodoItem updatedItem = todoRepository.save(todoItem);
        OutgoingTodoItem outgoing = OutgoingTodoItem.outgoingTodoItemFromTodoItem(updatedItem);
        return ResponseEntity.status(201).body(outgoing);
    }

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
