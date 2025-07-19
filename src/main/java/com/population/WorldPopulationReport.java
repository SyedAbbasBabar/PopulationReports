package com.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class WorldPopulationReport {

    public static void generate() {
        try {
            //Create Connection
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();

            //SQL Query
            String query = "SELECT SUM(Population) AS WorldPopulation FROM country";

            //Execute the SQL Query
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n📊 Report 6: Total World Population");
            System.out.println("-----------------------------------");

            if (rs.next()) {
                long worldPopulation = rs.getLong("WorldPopulation");
                System.out.printf("🌍 World Population: %,d\n", worldPopulation);
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Failed to generate World Population Report: " + e.getMessage());
        }
    }
}
