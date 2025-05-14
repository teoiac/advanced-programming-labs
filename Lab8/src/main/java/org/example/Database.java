package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?searchpath=public";
    private static final String USER = "postgres";
    private static final String PASSWORD = "hera2004";
    private static Connection connection = null;

    private Database(){}

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        if (connection == null) {
            throw new IllegalStateException("Failed to create database connection.");
        }
        return connection;
    }



    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Connected to database: " + connection);
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            throw new RuntimeException("Failed to create database connection.", e);
        }
    }



    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void commit(){
        try{
            if(connection != null){
                connection.commit();
            }
        } catch (SQLException e){
            System.err.println(e);
        }
    }

    public static void rollback(){
        try{
            if(connection != null){
                connection.rollback();
            }
        } catch (SQLException e){
            System.err.println(e);
        }
    }

}
