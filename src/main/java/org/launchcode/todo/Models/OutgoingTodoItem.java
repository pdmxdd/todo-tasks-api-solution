package org.launchcode.todo.Models;

public class OutgoingTodoItem {

    public OutgoingTodoItem() {}

    public static OutgoingTodoItem outgoingTodoItemFromTodoItem(TodoItem todoItem) {
        return new OutgoingTodoItem();
    }
}
