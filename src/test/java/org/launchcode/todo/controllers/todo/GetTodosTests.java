package org.launchcode.todo.controllers.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        // HTTP Response test:
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        // Database test:

        assertEquals(0, todoRepository.count());
    }

    @Test
    @DisplayName(value = "GET TODOS Populated")
    public void getTodosPopulated() throws Exception {
        // HTTP Response test:
        TodoItem testItem = todoRepository.save(TodoItem.createItem("test todo item"));
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value(testItem.getText()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed").value(testItem.getCompleted()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].tasks").doesNotExist());

        // Database test:

        assertEquals(1, todoRepository.count());
        assertEquals(testItem.getText(), todoRepository.findById(testItem.getId()).get().getText());
    }
    
}
