package com.dropwizard.utils;

public class Constants {
    public static final String TODO_NOT_FOUND = "Todo id %s not found.";
    public static final String SUCCESS = "Success";
    public static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting todo.";

    public static final String DATABASE_ACCESS_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    public static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    public static final String UNEXPECTED_DATABASE_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";

}
