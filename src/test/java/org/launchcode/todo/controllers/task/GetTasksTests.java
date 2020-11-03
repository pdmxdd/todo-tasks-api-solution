package org.launchcode.todo.controllers.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.todo.IntegrationTestConfig;

@IntegrationTestConfig
public class GetTasksTests {

    @Test
    @DisplayName(value = "GET /todos/{id}/tasks Not Found")
    public void getTodoTasksNotFound() throws Exception {
        
    }

    @Test
    @DisplayName(value = "GET /todos/{id}/tasks empty list")
    public void getTodoTasksEmpty() throws Exception {

    }

    @Test
    @DisplayName(value = "GET /todos/{id}/tasks populated")
    public void getTodoTasksPopuated() throws Exception {
        
    }

}
