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
public class GetTodosTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    public void clearDatabase() {
        todoRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "GET TODOS Empty")
    public void getTodosEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName(value = "GET TODOS Populated")
    public void getTodosPopulated() throws Exception {
        TodoItem testItem = todoRepository.save(TodoItem.createItem("test todo item"));
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value(testItem.getText()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed").value(testItem.getCompleted()));
    }
    
}
