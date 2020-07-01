package com.dropwizard.controller;
import com.dropwizard.repository.ToDoRepository;
import com.dropwizard.pojo.Todo;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import com.dropwizard.utils.Constants;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Service for the module dropwizardToDo and will be used to call the DSP(DAO)
 *
 * @author  Chinmay Swaroop Saini
 */
public abstract class ToDoService {
    private static final Logger logger = LoggerFactory.getLogger(ToDoService.class);

    /*  Information
        The @CreateSqlObject methods can be used to create DAO instances inside methods in the SqlObject
        class, in order to have them participate in the same transaction

        WebApplicationException extends Runtime exception.
        Constructor used :: WebApplicationException(String message, int status)
        Construct a new instance with a blank message and specified HTTP status code.
    */
    @CreateSqlObject
    abstract ToDoRepository todoDao();

    public List<Todo> getTodos() {
        return todoDao().getTodos();
    }

    public int getLastInsertedId() {
        return todoDao().lastInsertId();
    }

    public Todo getTodo(Integer id) {
        Todo todo = todoDao().getTodo(id);
        if(Objects.isNull(todo)) {
            throw new WebApplicationException(String.format(Constants.TODO_NOT_FOUND, id), Status.NOT_FOUND);
        }
        return todo;
    }

    public List<Todo> getActivities(Integer parent_id) {
        List<Todo> todo = todoDao().getTodoActivity(parent_id);
        if(Objects.isNull(todo)) {
            throw new WebApplicationException(String.format(Constants.TODO_NOT_FOUND, parent_id), Status.NOT_FOUND);
        }
        return todo;
    }

    public Todo createTodo(Todo todo) {
        todoDao().createTodo(todo);
        return todoDao().getTodo(todoDao().lastInsertId());
    }

    public Todo editTodo(Todo todo) {
        if(Objects.isNull(todoDao().getTodo(todo.getId()))) {
            throw new WebApplicationException(String.format(Constants.TODO_NOT_FOUND, todo.getId()), Status.NOT_FOUND);
        }
        todoDao().editTodo(todo);
        return todoDao().getTodo(todo.getId());
    }

    public String deleteTodo(final Integer id) {
        Integer result = todoDao().deleteTodo(id);
        switch (result) {
            case 1:
                return Constants.SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(Constants.TODO_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(String.format(Constants.UNEXPECTED_DELETE_ERROR, id), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public String performHealthCheck() {
        try {
            todoDao().getTodos();
        } catch (UnableToObtainConnectionException ex) {
            return checkUnableToObtainConnectionException(ex);
        } catch (UnableToExecuteStatementException ex) {
            return checkUnableToExecuteStatementException(ex);
        } catch (Exception ex) {
            return Constants.UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

    private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
        if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
            return Constants.DATABASE_ACCESS_ERROR + ex.getCause().getLocalizedMessage();
        } else if (ex.getCause() instanceof java.sql.SQLException) {
            return Constants.DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return Constants.UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
    }

    private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
        if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
            return Constants.DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return Constants.UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
    }

}
