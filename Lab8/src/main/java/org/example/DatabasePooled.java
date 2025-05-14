package org.example;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Enhanced database connection manager that uses HikariCP connection pool
 */
public class DatabasePooled {
    private static Connection connection = null;

    private DatabasePooled() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = ConnectionPool.getConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void commit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("Error committing transaction: " + e.getMessage());
        }
    }

    public static void rollback() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Error rolling back transaction: " + e.getMessage());
        }
    }
}