package com.dropwizard.controller;

import com.codahale.metrics.annotation.Timed;
import com.dropwizard.pojo.Todo;
import com.dropwizard.pojo.TodoActivity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * All the methods are serving api's here
 *
 * @author  Chinmay Swaroop Saini
 */

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class ToDoResource {
    private final ToDoService todoDSP;
    private static final Logger logger = LoggerFactory.getLogger(ToDoResource.class);

    public ToDoResource(ToDoService todoDSP) {
        this.todoDSP = todoDSP;
    }

    /**
     * @return  all the todos with all their respective activites
    [
    {
    "todo": {
    "id": 1,
    "task_name": "Chinmay's Tasks :: Week 26",
    "description": " Lean1X-Assignment",
    "parent_id": null
    },
    "activityList": [
    {
    "id": 2,
    "task_name": "BE",
    "description": "writeCode",
    "parent_id": null
    }
    ]
    }
    ]
     */
    @GET
    @Timed
    public Response getTodos() {
        List<TodoActivity> result = new ArrayList<>();

        /* get the todos first */
        List<Todo> todos = todoDSP.getTodos();
        /* Corresponding to each get activities */
        for(Todo each : todos){
            TodoActivity tempResult = new TodoActivity();
            tempResult.setTodo(each);
            tempResult.setActivityList(todoDSP.getActivities(each.getId()));
            result.add(tempResult);
        }
        return Response.ok(result).build();

    }
    /**
     @return a specific todo and it's activities with provided id
     */
    @GET
    @Timed
    @Path("{id}")
    public Response getTodo(@PathParam("id") final Integer id) {
        List<Todo> activities = todoDSP.getActivities(id);
        TodoActivity result = new TodoActivity();
        result.setTodo(todoDSP.getTodo(id));
        result.setActivityList(activities);
        return Response.ok(result).build();
    }

    /**
     @return response of type ToDoActivity i.e is generated by the post request
     */
    @POST
    @Timed
    public Response createTodo(@NotNull @Valid final TodoActivity input) {
        // logger.debug("Check::"+input.toString());
        /* first creates parent to in the database and then adds activities */
        TodoActivity result = new TodoActivity();
        Todo parent = todoDSP.createTodo(new Todo(input.getTodo().getTask_name(),
                input.getTodo().getDescription()));

        int lastInsertId = todoDSP.getLastInsertedId();

        List<Todo> activities =  new ArrayList<>();
        if(input.getActivityList().size()!=0){
            for(Todo activity : input.getActivityList()){
                activity.setParent_id(lastInsertId);
                todoDSP.createTodo(activity);
                activities.add(activity);
            }
        }
        result.setTodo(parent);
        result.setActivityList(activities);
        return Response.ok(result).build();
    }

    /**
     * Deletes the toDo along it's activities too.
     @return
     {
     "code": 200,
     "data": "Success"
     }
     */
    @DELETE
    @Timed
    @Path("{id}")
    public Response deleteTodo(@PathParam("id") final Integer id) {
        return Response.ok(todoDSP.deleteTodo(id)).build();
    }

    @PUT
    @Timed
    @Path("{id}")
    public Response updateTodo(@PathParam("id") final Integer id, @NotNull @Valid final Todo input) {
        /* the input here maybe an activity or a todo(Parent) as both have same structure*/
        Todo edited = todoDSP.editTodo(input,id);
        int check = todoDSP.getTodo(id).getParent_id();
        logger.debug("Check:: "+check);
        return Response.ok(edited).build();
    }
}

