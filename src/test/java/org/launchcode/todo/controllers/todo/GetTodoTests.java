package org.launchcode.todo.controllers.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.todo.IntegrationTestConfig;
import org.launchcode.todo.Models.TodoItem;
import org.launchcode.todo.data.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class GetTodoTests {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    public void clearDatabase() {
        todoRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "GET TODO by id Not Found")
    public void getTodoNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/-1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName(value = "GET TODO by id")
    public void getTodo() throws Exception {
        TodoItem testTodo = todoRepository.save(TodoItem.createItem("test item"));
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + testTodo.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(testTodo.getText()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testTodo.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(testTodo.getCompleted()));
    }


}
