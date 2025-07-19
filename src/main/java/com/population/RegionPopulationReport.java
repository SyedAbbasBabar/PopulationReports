package com.population;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class RegionPopulationReport {

    public static void generate() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n📊 Report 7: Population by Area (Continent, Region, Country, District, City)");
            System.out.println("Select the type of area:");
            System.out.println("1. Continent");
            System.out.println("2. Region");
            System.out.println("3. Country");
            System.out.println("4. District");
            System.out.println("5. City");

            System.out.print("🔢 Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            String areaType = "";
            String query = "";
           // Choose the Option, Which Report do you want?
            switch (choice) {
                case 1:
                    areaType = "Continent";
                    query = "SELECT Continent AS Name, SUM(Population) AS Population " +
                            "FROM country WHERE Continent = ? GROUP BY Continent";
                    break;
                case 2:
                    areaType = "Region";
                    query = "SELECT Region AS Name, SUM(Population) AS Population " +
                            "FROM country WHERE Region = ? GROUP BY Region";
                    break;
                case 3:
                    areaType = "Country";
                    query = "SELECT Name, Population FROM country WHERE Name = ?";
                    break;
                case 4:
                    areaType = "District";
                    query = "SELECT District AS Name, SUM(Population) AS Population " +
                            "FROM city WHERE District = ? GROUP BY District";
                    break;
                case 5:
                    areaType = "City";
                    query = "SELECT Name, Population FROM city WHERE Name = ?";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            System.out.print("📝 Enter the name of the " + areaType + ": ");
            String name = scanner.nextLine();
            
            // //create connection
            Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);

            //Execute Query
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.printf("✅ Population of %s \"%s\": %,d\n",
                        areaType,
                        rs.getString("Name"),
                        rs.getLong("Population"));
            } else {
                System.out.println("No data found for " + areaType + ": " + name);
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Failed to generate Region/Country/City Population Report: " + e.getMessage());
        }
    }
}
