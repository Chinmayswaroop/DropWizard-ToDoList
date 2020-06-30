package com.dropwizard.pojo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String task_name;
    @JsonProperty
    private String description;
    @JsonProperty
    private Integer parent_id;


    public Todo() {
        super();
    }

    public Todo(String name, String status) {
        super();
        this.task_name = name;
        this.description = status;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask_name() {
        return this.task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", task_name='" + task_name + '\'' +
                ", description='" + description + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
