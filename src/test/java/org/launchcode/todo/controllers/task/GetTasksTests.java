package org.launchcode.todo.controllers.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.todo.IntegrationTestConfig;
import org.launchcode.todo.Models.Task;
import org.launchcode.todo.Models.TodoItem;
import org.launchcode.todo.data.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTestConfig
public class GetTasksTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName(value = "GET /todos/{id}/tasks Not Found")
    public void getTodoTasksNotFound() throws Exception {
        // HTTP Response test:
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/-1/tasks"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Database test:
        assertEquals(0, todoRepository.count());
    }

    @Test
    @DisplayName(value = "GET /todos/{id}/tasks empty list")
    public void getTodoTasksEmpty() throws Exception {
        // HTTP Response test:
        TodoItem testTodoItem = TodoItem.createItem("test todo item"); // does not have an id
        TodoItem updatedTestTodoItem = todoRepository.save(testTodoItem); // does have an id

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/"+ updatedTestTodoItem.getId() + "/tasks"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        // Database test:
        assertEquals(1, todoRepository.count());
    }

    @Test
    @DisplayName(value = "GET /todos/{id}/tasks populated")
    public void getTodoTasksPopuated() throws Exception {
        TodoItem testTodoItem = TodoItem.createItem("test todo item"); // this doesn't have an id
        TodoItem updatedTestTodoItem = todoRepository.save(testTodoItem); // puts it in the database and returns an id
        // create a new task
        Task testTask = new Task("test sub task 1", updatedTestTodoItem); // passed in a TodoItem, set it to todoItem field of that
        updatedTestTodoItem.addTask(testTask);
        todoRepository.save(updatedTestTodoItem);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + updatedTestTodoItem.getId() + "/tasks"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(testTask.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].todoItem").doesNotExist())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist());

        // Database test:
        assertEquals(1, todoRepository.count());
        assertEquals(updatedTestTodoItem.getText(), todoRepository.findById(updatedTestTodoItem.getId()).get().getText());
    }

}
