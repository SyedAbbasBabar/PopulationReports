package com.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PopulationByCountryReport {

    public static void generate() {
        try {
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();

            String query =
                "SELECT c.Name AS Country, " +
                "c.Population AS TotalPopulation, " +
                "IFNULL(SUM(ci.Population), 0) AS CityPopulation, " +
                "ROUND((IFNULL(SUM(ci.Population), 0) / c.Population) * 100, 2) AS CityPercent, " +
                "c.Population - IFNULL(SUM(ci.Population), 0) AS NonCityPopulation, " +
                "ROUND(((c.Population - IFNULL(SUM(ci.Population), 0)) / c.Population) * 100, 2) AS NonCityPercent " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Code = ci.CountryCode " +
                "GROUP BY c.Code, c.Name, c.Population " +
                "ORDER BY c.Population DESC";

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n📊 Report 5: Population by Country (City vs Non-City)");
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-30s %-15s %-15s %-10s %-15s %-10s\n",
                    "Country", "Total", "In Cities", "% Cities", "Not in Cities", "% Non-Cities");

            while (rs.next()) {
                System.out.printf("%-30s %-15d %-15d %-10.2f %-15d %-10.2f\n",
                        rs.getString("Country"),
                        rs.getInt("TotalPopulation"),
                        rs.getInt("CityPopulation"),
                        rs.getDouble("CityPercent"),
                        rs.getInt("NonCityPopulation"),
                        rs.getDouble("NonCityPercent"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to generate Population by Country Report: " + e.getMessage());
        }
    }
}
