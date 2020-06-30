package com.dropwizard.pojo;
import java.util.List;

public class TodoActivity {
    Todo todo;
    List<Todo> activityList;

    public Todo getTodo() {
        return this.todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public List<Todo> getActivityList() {
        return this.activityList;
    }

    public void setActivityList(List<Todo> activityList) {
        this.activityList = activityList;
    }

}