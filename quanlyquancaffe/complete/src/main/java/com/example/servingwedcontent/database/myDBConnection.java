package com.example.servingwebcontent.database;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class myDBConnection {

    private final String myDatabaseURL = "jdbc:mysql://mysql-2954f5bb-opp-data.j.aivencloud.com:14833/defaultdb?sslMode=REQUIRED";
    private final String username = "avnadmin";  
    private final String password = "AVNS_fIeg8rQ_jgkVDcDFWyn"; 
    private final String myDatabaseDriver = "com.mysql.cj.jdbc.Driver";

    private Connection conn = null;

    public Connection getConnection() {
        try {
            Class.forName(myDatabaseDriver);
            return DriverManager.getConnection(myDatabaseURL, username, password);
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public Statement getMyConn() {
        try {
            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL, username, password);
            return conn.createStatement();
        } catch (Exception e) {
            System.out.println("myDBConnection at getMyConn() error: " + e);
        }
        return null;
    }

    public Connection getOnlyConn() {
        try {
            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL, username, password);
            return conn;
        } catch (Exception e) {
            System.out.println("Database connection error: " + e);
        }
        return null;
    }

    public boolean testConnection() {
        try (Connection conn = getOnlyConn()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection test successful");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Database connection test failed: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
