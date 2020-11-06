package org.launchcode.todo.Models;

public class OutgoingTodoItem {

    int id;
    String text;
    boolean completed;

    public OutgoingTodoItem() {}

    public OutgoingTodoItem(int id, String text, boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    public static OutgoingTodoItem outgoingTodoItemFromTodoItem(TodoItem todoItem) {
        return new OutgoingTodoItem(todoItem.getId(), todoItem.getText(), todoItem.getCompleted());
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public boolean getCompleted() {
        return this.completed;
    }
}
