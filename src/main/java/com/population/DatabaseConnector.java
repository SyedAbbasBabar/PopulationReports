package com.population;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    public static Connection connect() throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/world";  // Correct connection URL

        String user = "root";
        String password = "waleed123";

        return DriverManager.getConnection(url, user, password);
    }
}
