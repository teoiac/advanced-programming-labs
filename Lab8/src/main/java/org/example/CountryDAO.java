package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    private final ContinentDAO continentDAO = new ContinentDAO();

    public void create(String name, String code, int continentID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setInt(3, continentID);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Country ID: " + id);
                }
            }

            Database.commit();
        } catch (SQLException e) {
            Database.rollback();
            throw e;
        }
    }

    public Country findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM countries WHERE id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCode(rs.getString("code"));
                int continentID = rs.getInt("continent_id");
                Continent continent = continentDAO.findById(continentID);
                country.setContinent(continent);

                return country;

            }
            return null;
        }
    }

    public Country findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM countries WHERE name = ?")) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCode(rs.getString("code"));
                int continentID = rs.getInt("continent_id");
                Continent continent = continentDAO.findById(continentID);
                country.setContinent(continent);

                return country;
            }
            return null;
        }
    }

    public List<Country> findByContinent(int continentId) throws SQLException {
        List<Country> countries = new ArrayList<>();
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM countries WHERE continent_id = ?")) {

            pstmt.setInt(1, continentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCode(rs.getString("code"));
                Continent continent = continentDAO.findById(continentId);
                country.setContinent(continent);

                countries.add(country);
            }
        }

        return countries;
    }


}
