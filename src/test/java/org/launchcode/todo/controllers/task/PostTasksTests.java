package org.launchcode.todo.controllers.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.todo.IntegrationTestConfig;
import org.launchcode.todo.Models.Task;
import org.launchcode.todo.Models.TaskDto;
import org.launchcode.todo.Models.TodoItem;
import org.launchcode.todo.data.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@IntegrationTestConfig
public class PostTasksTests {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName(value = "POST /todos/{id}/tasks Not Found")
    public void postTodoTasksNotFound() throws Exception {
        TaskDto testTaskDto = new TaskDto("sub task 1");
        String testTaskDtoJSON = new ObjectMapper().writeValueAsString(testTaskDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/todos/-1/tasks").contentType(MediaType.APPLICATION_JSON).content(testTaskDtoJSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
        
    }

    @Test
    @DisplayName(value = "POST /todos/{id}/tasks")
    public void postTodoTasks() throws Exception {
        TodoItem testTodoItem = todoRepository.save(TodoItem.createItem("todo item"));
        TaskDto testTaskDto = new TaskDto("sub task 1");
        String testTaskDtoJSON = new ObjectMapper().writeValueAsString(testTaskDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/todos/" + testTodoItem.getId() + "/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(testTaskDtoJSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
        
        Optional<TodoItem> optionalCheckTodoItem = todoRepository.findById(testTodoItem.getId());
        if(optionalCheckTodoItem.isEmpty()) {
            fail("TODO ITEM SHOULD BE IN DATABASE");
        }
        TodoItem checkTodoItem = optionalCheckTodoItem.get();
        List<Task> tasks = checkTodoItem.getTasks();
        assertEquals(tasks.get(0).getTitle(), "sub task 1");
    }
}
