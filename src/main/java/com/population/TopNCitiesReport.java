package com.population;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TopNCitiesReport {

    public static void generate() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\n🔢 Enter the number of top populated cities to display (N): ");
            int n = scanner.nextInt();

            Connection conn = DatabaseConnector.connect();
            String query = "SELECT city.Name AS CityName, country.Name AS CountryName, " +
                           "city.District, city.Population " +
                           "FROM city JOIN country ON city.CountryCode = country.Code " +
                           "ORDER BY city.Population DESC LIMIT ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, n);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n📊 Report 4: Top " + n + " Populated Cities in the World");
            System.out.println("-------------------------------------------------------------");
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
            System.out.println("❌ Failed to generate Top N Cities Report: " + e.getMessage());
        }
    }
}
