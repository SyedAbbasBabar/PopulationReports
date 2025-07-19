package com.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CityReport {

    public static void generate() {
        try {
             //create connection
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();

            //SQL Query
            String query = "SELECT city.Name AS CityName, country.Name AS CountryName, " +
                           "city.District, city.Population " +
                           "FROM city JOIN country ON city.CountryCode = country.Code " +
                           "ORDER BY city.Population DESC";

            //Execute Query
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n📊 Report 2: Cities by Population (High to Low)");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-30s %-30s %-20s %-12s\n",
                    "City", "Country", "District", "Population");

            while (rs.next()) {
                System.out.printf("%-30s %-30s %-20s %-12d\n",
                        rs.getString("CityName"),
                        rs.getString("CountryName"),
                        rs.getString("District"),
                        rs.getInt("Population"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to generate City Report: " + e.getMessage());
        }
    }
}
