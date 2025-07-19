package com.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LanguageReport {

    public static void generate() {
        try {
            // //create connection
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();

            //SQL Query
            String query =
                "SELECT cl.Language, " +
                "ROUND(SUM(c.Population * cl.Percentage / 100)) AS TotalSpeakers, " +
                "ROUND((SUM(c.Population * cl.Percentage / 100) / " +
                "      (SELECT SUM(Population) FROM country)) * 100, 2) AS WorldPercent " +
                "FROM countrylanguage cl " +
                "JOIN country c ON cl.CountryCode = c.Code " +
                "WHERE cl.Language IN ('Chinese', 'English', 'Spanish') " +
                "GROUP BY cl.Language " +
                "ORDER BY TotalSpeakers DESC";
            //Execute Query
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n📊 Report 8: Number of People Speaking Selected Languages");
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-15s\n", "Language", "Speakers", "World %");

            while (rs.next()) {
                System.out.printf("%-15s %,20d %15.2f%%\n",
                        rs.getString("Language"),
                        rs.getLong("TotalSpeakers"),
                        rs.getDouble("WorldPercent"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to generate Language Report: " + e.getMessage());
        }
    }
}
