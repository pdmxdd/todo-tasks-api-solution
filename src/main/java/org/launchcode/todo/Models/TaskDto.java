package org.launchcode.todo.Models;

public class TaskDto {

    private String title;

    public TaskDto() {}

    public TaskDto(String title) {
        this.title = title;
    }

    public static TaskDto buildDtoFromTask(Task task) {
        return new TaskDto(task.getTitle());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
