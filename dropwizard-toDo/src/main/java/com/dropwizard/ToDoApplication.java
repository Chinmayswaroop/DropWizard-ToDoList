package com.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.dropwizard.controller.ToDoResource;
import com.dropwizard.controller.ToDoService;
import com.dropwizard.health.ToDoHealthCheck;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

public class ToDoApplication extends Application<ToDoConfiguration> {

    private static final String SQL = "sql";
    private static final String TODO_SERVICE = "todo";

    public static void main(final String[] args) throws Exception {
        new ToDoApplication().run(args);
    }

    @Override
    public String getName() {
        return "todo";
    }

    @Override
    public void initialize(final Bootstrap<ToDoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ToDoConfiguration configuration,
                    final Environment environment) {
        // Data source configuration
        final DataSource dataSource =
                configuration.getDataSourceFactory().build(environment.metrics(), SQL);
        DBI dbi = new DBI(dataSource);
        // Register Health Check
        ToDoHealthCheck healthCheck =
                new ToDoHealthCheck(dbi.onDemand(ToDoService.class));
        environment.healthChecks().register(TODO_SERVICE, healthCheck);
        environment.jersey().register(new ToDoResource(dbi.onDemand(ToDoService.class)));
    }

}
