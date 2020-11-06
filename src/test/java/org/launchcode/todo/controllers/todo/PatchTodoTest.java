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
public class PatchTodoTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    public void clearDatabase() {
        todoRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "PATCH /todos/{id} Not found")
    public void patchTodoNotFound() throws Exception {
        // HTTP Response test:
        mockMvc.perform(MockMvcRequestBuilders.patch("/todos/-1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Database test:
        assertEquals(0, todoRepository.count());
    }

    @Test
    @DisplayName(value = "PATCH /todos/{id} 200 and reflection")
    public void patchTodo() throws Exception {
        // HTTP Response test:
        TodoItem testTodo = todoRepository.save(TodoItem.createItem("test item"));
        mockMvc.perform(MockMvcRequestBuilders.patch("/todos/" + testTodo.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testTodo.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(testTodo.getText()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.tasks").doesNotExist());

        // Database test:
        assertEquals(testTodo.getText(), todoRepository.findById(testTodo.getId()).get().getText());
    }

}
