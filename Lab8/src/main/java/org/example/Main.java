package org.example;

import org.example.Continent;
import org.example.Country;
import org.example.City;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Constants for menu display
    private static final String SEPARATOR = "===================================================";
    private static final String MENU =
            "\nCITY DATABASE APPLICATION\n" +
                    SEPARATOR + "\n" +
                    "1. Show all continents\n" +
                    "2. Show all countries in a continent\n" +
                    "3. Show all cities in a country\n" +
                    "4. Calculate distance between two cities\n" +
                    "5. Import cities from CSV file\n" +
                    "6. Execute custom SQL query\n" +
                    "0. Exit\n" +
                    SEPARATOR + "\n" +
                    "Enter your choice: ";

    private static final ContinentDAO continentDAO = new ContinentDAO();
    private static final CountryDAO countryDAO = new CountryDAO();
    private static final CityDAO cityDAO = new CityDAO();
    private static final DistanceCalculator distanceCalculator = new DistanceCalculator(cityDAO);
    private static final DataImporter dataImporter = new DataImporter();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Get a connection to verify it works
            Connection conn = Database.getConnection();
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM continents");
                while (rs.next()) {
                    System.out.println("Continent: " + rs.getString("name"));
                }
            }



            int choice;
            do {
                System.out.print(MENU);
                choice = getIntInput();

                try {
                    processMenuChoice(choice);
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }

            } while (choice != 0);

            System.out.println("Thank you for using the City Database Application!");

            // Close connections
            DatabasePooled.closeConnection();
            ConnectionPool.closePool();

        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    private static void processMenuChoice(int choice) throws Exception {
        switch (choice) {
            case 0:
                // Exit - handled in the main loop
                break;
            case 1:
                showAllContinents();
                break;
            case 2:
                showCountriesInContinent();
                break;
            case 3:
                showCitiesInCountry();
                break;
            case 4:
                calculateDistanceBetweenCities();
                break;
            case 5:
                importCitiesFromCsv();
                break;
            case 6:
                executeCustomSql();
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private static void showAllContinents() throws SQLException {
        List<Continent> continents = continentDAO.findAll();

        System.out.println("\nList of Continents:");
        System.out.println(SEPARATOR);
        System.out.printf("%-5s | %-20s\n", "ID", "Name");
        System.out.println(SEPARATOR);

        for (Continent continent : continents) {
            System.out.printf("%-5d | %-20s\n",
                    continent.getId(),
                    continent.getName());
        }
    }

    private static void showCountriesInContinent() throws SQLException {
        showAllContinents();
        System.out.print("\nEnter continent ID: ");
        int continentId = getIntInput();

        Continent continent = continentDAO.findById(continentId);
        if (continent == null) {
            System.out.println("Continent not found with ID: " + continentId);
            return;
        }

        List<Country> countries = countryDAO.findByContinent(continentId);

        System.out.println("\nCountries in " + continent.getName() + ":");
        System.out.println(SEPARATOR);
        System.out.printf("%-5s | %-30s | %-5s\n", "ID", "Name", "Code");
        System.out.println(SEPARATOR);

        for (Country country : countries) {
            System.out.printf("%-5d | %-30s | %-5s\n",
                    country.getId(),
                    country.getName(),
                    country.getCode());
        }
    }

    private static void showCitiesInCountry() throws SQLException {
        System.out.print("Enter country name: ");
        String countryName = scanner.nextLine();

        Country country = countryDAO.findByName(countryName);
        if (country == null) {
            System.out.println("Country not found: " + countryName);
            return;
        }

        List<City> cities = cityDAO.findByCountry(country.getId());

        System.out.println("\nCities in " + country.getName() + ":");
        System.out.println(SEPARATOR);
        System.out.printf("%-5s | %-30s | %-8s | %-10s | %-10s\n",
                "ID", "Name", "Capital", "Latitude", "Longitude");
        System.out.println(SEPARATOR);

        for (City city : cities) {
            System.out.printf("%-5d | %-30s | %-8s | %-10.6f | %-10.6f\n",
                    city.getId(),
                    city.getName(),
                    city.isCapital() ? "Yes" : "No",
                    city.getLatitude(),
                    city.getLongitude());
        }
    }

    private static void calculateDistanceBetweenCities() throws SQLException {
        System.out.print("Enter first city name: ");
        String city1 = scanner.nextLine();

        System.out.print("Enter second city name: ");
        String city2 = scanner.nextLine();

        double distance = distanceCalculator.calculateDistance(city1, city2);
        System.out.println("\nDistance between cities: " + distance);
    }


    private static void importCitiesFromCsv() {
        System.out.print("Enter CSV file path (or press enter for default sample file): ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            URL resource = Main.class.getClassLoader().getResource("world_cities.csv");
            if (resource == null) {
                System.out.println("Default file not found in resources.");
                return;
            }
            filePath = resource.getPath();
            System.out.println("Resource file path: " + filePath); // Add this for debugging
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try {
            dataImporter.importCitiesFromCsv(filePath);
            System.out.println("Import completed successfully.");
        } catch (Exception e) {
            System.err.println("Import failed: " + e.getMessage());
        }
    }


    private static void executeCustomSql() {
        System.out.println("Enter SQL query (SELECT only):");
        String sql = scanner.nextLine();

        if (!sql.trim().toLowerCase().startsWith("select")) {
            System.out.println("Only SELECT queries are allowed.");
            return;
        }

        try (Connection conn = DatabasePooled.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column headers
            System.out.println(SEPARATOR);
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i));
                if (i < columnCount) {
                    System.out.print(" | ");
                }
            }
            System.out.println("\n" + SEPARATOR);

            // Print data rows
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i));
                    if (i < columnCount) {
                        System.out.print(" | ");
                    }
                }
                System.out.println();
            }

            System.out.println(SEPARATOR);
            System.out.println(rowCount + " row(s) returned");

        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        }
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
}