package org.example;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CityDAO {

    private final CountryDAO countryDAO = new CountryDAO();

    public void create(String name, int countryId, boolean capital, double latitude, double longitude) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country_id, capital, latitude, longitude) " +
                        "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            pstmt.setBoolean(3, capital);
            pstmt.setDouble(4, latitude);
            pstmt.setDouble(5, longitude);
            pstmt.executeUpdate();

            // Get the generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Created city with ID: " + id);
                }
            }

            Database.commit();
        } catch (SQLException e) {
            Database.rollback();
            throw e;
        }
    }

    public City findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT c.id, c.name, c.country_id, c.capital, c.latitude, c.longitude " +
                        "FROM cities c WHERE c.id = ?")) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                City city = new City();
                city.setId(String.valueOf(rs.getInt("id")));
                city.setName(rs.getString("name"));
                city.setCapital(rs.getBoolean("capital"));
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));

                // Get the country
                int countryId = rs.getInt("country_id");
                Country country = countryDAO.findById(countryId);
                city.setCountry(country);

                return city;
            }
            return null;
        }
    }

    public List<City> findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        List<City> cities = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT c.id, c.name, c.country_id, c.capital, c.latitude, c.longitude " +
                        "FROM cities c WHERE c.name = ?")) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                City city = new City();
                city.setId(String.valueOf(rs.getInt("id")));
                city.setName(rs.getString("name"));
                city.setCapital(rs.getBoolean("capital"));
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));

                // Get the country
                int countryId = rs.getInt("country_id");
                Country country = countryDAO.findById(countryId);
                city.setCountry(country);

                cities.add(city);
            }
        }

        return cities;
    }

    public List<City> findByCountry(int countryId) throws SQLException {
        Connection con = Database.getConnection();
        List<City> cities = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT c.id, c.name, c.capital, c.latitude, c.longitude " +
                        "FROM cities c WHERE c.country_id = ?")) {

            pstmt.setInt(1, countryId);
            ResultSet rs = pstmt.executeQuery();

            Country country = countryDAO.findById(countryId);

            while (rs.next()) {
                City city = new City();
                city.setId(String.valueOf(rs.getInt("id")));
                city.setName(rs.getString("name"));
                city.setCapital(rs.getBoolean("capital"));
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));
                city.setCountry(country);

                cities.add(city);
            }
        }

        return cities;
    }

    public List<City> findCapitals() throws SQLException {
        Connection con = Database.getConnection();
        List<City> capitals = new ArrayList<>();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT c.id, c.name, c.country_id, c.latitude, c.longitude " +
                             "FROM cities c WHERE c.capital = true")) {

            while (rs.next()) {
                City city = new City();
                city.setId(String.valueOf(rs.getInt("id")));
                city.setName(rs.getString("name"));
                city.setCapital(true);
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));

                // Get the country
                int countryId = rs.getInt("country_id");
                Country country = countryDAO.findById(countryId);
                city.setCountry(country);

                capitals.add(city);
            }
        }

        return capitals;
    }

    public List<City> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<City> cities = new ArrayList<>();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT c.id, c.name, c.country_id, c.capital, c.latitude, c.longitude " +
                             "FROM cities c")) {

            while (rs.next()) {
                City city = new City();
                city.setId(String.valueOf(rs.getInt("id")));
                city.setName(rs.getString("name"));
                city.setCapital(rs.getBoolean("capital"));
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));

                // Get the country
                int countryId = rs.getInt("country_id");
                Country country = countryDAO.findById(countryId);
                city.setCountry(country);

                cities.add(city);
            }
        }

        return cities;
    }
}