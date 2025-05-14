package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.Continent;
import org.example.Country;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataImporter {
    private final ContinentDAO continentDAO;
    private final CountryDAO countryDAO;
    private final CityDAO cityDAO;

    // Cache to avoid redundant database queries
    private final Map<String, Integer> continentCache = new HashMap<>();
    private final Map<String, Integer> countryCache = new HashMap<>();

    public DataImporter() {
        this.continentDAO = new ContinentDAO();
        this.countryDAO = new CountryDAO();
        this.cityDAO = new CityDAO();
    }

    /**
     * Import data from a CSV file with city information
     * Expected CSV format:
     * city_name,country_name,country_code,continent,capital,latitude,longitude
     *
     * @param csvFilePath Path to the CSV file
     * @throws IOException if file cannot be read
     * @throws SQLException if database operations fail
     */
    public void importCitiesFromCsv(String csvFilePath) throws IOException, SQLException {
        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withHeader("city", "country", "code", "continent", "capital", "latitude", "longitude")
                     .withFirstRecordAsHeader())) {

            System.out.println("Starting import from: " + csvFilePath);
            int count = 0;

            for (CSVRecord record : csvParser) {
                String cityName = record.get("city");
                String countryName = record.get("country");
                String countryCode = record.get("code");
                String continentName = record.get("continent");
                boolean isCapital = "true".equalsIgnoreCase(record.get("capital"));

                double latitude = 0;
                double longitude = 0;

                try {
                    latitude = Double.parseDouble(record.get("latitude"));
                    longitude = Double.parseDouble(record.get("longitude"));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid coordinates for " + cityName + ": " + e.getMessage());
                    // Continue with default coordinates (0,0)
                }

                // Get or create continent
                int continentId = getOrCreateContinent(continentName);

                // Get or create country
                int countryId = getOrCreateCountry(countryName, countryCode, continentId);

                // Create city
                cityDAO.create(cityName, countryId, isCapital, latitude, longitude);

                count++;
                if (count % 100 == 0) {
                    System.out.println("Imported " + count + " cities...");
                }
            }

            System.out.println("Import completed. " + count + " cities imported.");
        }
    }

    private int getOrCreateContinent(String continentName) throws SQLException {
        if (continentCache.containsKey(continentName)) {
            return continentCache.get(continentName);
        }

        Integer continentId = continentDAO.findByName(continentName);
        if (continentId == null) {
            continentDAO.create(continentName);
            continentId = continentDAO.findByName(continentName);
        }

        continentCache.put(continentName, continentId);
        return continentId;
    }

    private int getOrCreateCountry(String countryName, String countryCode, int continentId) throws SQLException {
        if (countryCache.containsKey(countryName)) {
            return countryCache.get(countryName);
        }

        Country country = countryDAO.findByName(countryName);
        if (country == null) {
            countryDAO.create(countryName, countryCode, continentId);
            country = countryDAO.findByName(countryName);
        }

        countryCache.put(countryName, country.getId());
        return country.getId();
    }
}