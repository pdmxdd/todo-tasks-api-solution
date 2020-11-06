package org.launchcode.todo.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.launchcode.todo.data.TodoRepository;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TodoItem todoItem;

    public Task() {}

    public Task(String title, TodoItem todoItem) {
        this.title = title;
        this.todoItem = todoItem;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public TodoItem getTodoItem() {
        return this.todoItem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTodoItem(TodoItem todoItem) {
        this.todoItem = todoItem;
    }
}
