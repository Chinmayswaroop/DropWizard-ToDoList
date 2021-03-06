package com.dropwizard.pojo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDoMapper implements ResultSetMapper<Todo> {
    private static final String ID = "id";
    private static final String NAME = "task_name";
    private static final String STATUS = "description";
    private static final String PARENT_ID = "parent_id";

    public Todo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Todo todo = new Todo(resultSet.getString(NAME), resultSet.getString(STATUS));
        todo.setId(resultSet.getInt(ID));
        todo.setParent_id(resultSet.getInt(PARENT_ID));
        return todo;
    }


}
