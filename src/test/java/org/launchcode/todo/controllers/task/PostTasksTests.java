package org.launchcode.todo.controllers.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.launchcode.todo.IntegrationTestConfig;
@IntegrationTestConfig
public class PostTasksTests {
    
    @Test
    @DisplayName(value = "POST /todos/{id}/tasks Not Found")
    public void postTodoTasksNotFound() throws Exception {
        
    }

    @Test
    @DisplayName(value = "POST /todos/{id}/tasks")
    public void postTodoTasks() throws Exception {
        
    }
}
