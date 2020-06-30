package com.dropwizard.repository;
import com.dropwizard.pojo.Todo;
import com.dropwizard.pojo.ToDoMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import java.util.List;

//TODO :: create interface for this
@RegisterMapper(ToDoMapper.class)
public interface ToDoRepository {

    @SqlQuery("select * from todo where parent_id is null")
    public List<Todo> getTodos();

    @SqlQuery("select * from todo where id = :id")
    public Todo getTodo(@Bind("id") final int id);

    @SqlQuery("select * from todo where parent_id = :parent_id")
    public List<Todo> getTodoActivity(@Bind("parent_id") final int parent_id);

    @SqlUpdate("update todo set name = coalesce(:name, name), " +
            " status = coalesce(:status, status)" +
            " where id = :id")
    void editTodo(@BindBean final Todo todo);

    @SqlUpdate("delete from todo where id = :id")
    int deleteTodo(@Bind("id") final int id);

    @SqlUpdate("insert into todo(task_name, description, parent_id) values(:task_name, :description, :parent_id)")
    void createTodo(@BindBean final Todo todo);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}