package org.launchcode.todo.Models;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Incoming Todo Item", description = "Todo Item incoming JSON representation. Used for creating new Todo Items.")
public class IncomingTodoItem {

    private String text;

    public IncomingTodoItem() {}

    public IncomingTodoItem(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
