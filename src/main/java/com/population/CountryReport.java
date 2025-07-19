package com.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CountryReport {

    public static void generate() {
        try {
             //create connection
            Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();

            //SQL Query
            String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                           "FROM country ORDER BY Population DESC";

            //Execute Query
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n📊 Report 1: Countries by Population (High to Low)");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-5s %-30s %-15s %-20s %-12s %-8s\n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");

            while (rs.next()) {
                System.out.printf("%-5s %-30s %-15s %-20s %-12d %-8d\n",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getInt("Capital"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Failed to generate Country Report: " + e.getMessage());
        }
    }
}
