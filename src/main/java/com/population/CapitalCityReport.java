package com.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CapitalCityReport {

    public static void generate() {
        try {
            //create connection
            Connection conn = DatabaseConnector.connect();  
            Statement stmt = conn.createStatement();

            //SQL Query
            String query = "SELECT city.Name AS CapitalCity, country.Name AS CountryName, city.Population " +
                           "FROM city JOIN country ON city.ID = country.Capital " +
                           "ORDER BY city.Population DESC";

            //Execute Query
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n📊 Report 3: Capital Cities by Population (High to Low)");
            System.out.println("---------------------------------------------------------");
            System.out.printf("%-30s %-30s %-12s\n",
                    "Capital City", "Country", "Population");

            while (rs.next()) {
                System.out.printf("%-30s %-30s %-12d\n",
                        rs.getString("CapitalCity"),
                        rs.getString("CountryName"),
                        rs.getInt("Population"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to generate Capital City Report: " + e.getMessage());
        }
    }
}
