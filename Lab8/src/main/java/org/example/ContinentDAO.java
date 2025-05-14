package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContinentDAO {
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO continents (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Continent ID: " + id);
                }
            }

            Database.commit();
        } catch (SQLException ex) {
            Database.rollback();
            throw ex;
        }
    }


    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT id FROM continents WHERE name = ?")) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            return rs.next() ? rs.getInt("id") : null;
        }
    }

    public Continent findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT id, name FROM continents WHERE id = ?")) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Continent continent = new Continent();
                continent.setId(rs.getInt("id"));
                continent.setName(rs.getString("name"));
                return continent;
            }
            return null;
        }
    }

    public List<Continent> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<Continent> continents = new ArrayList<>();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM continents")) {

            while (rs.next()) {
                Continent continent = new Continent();
                continent.setId(rs.getInt("id"));
                continent.setName(rs.getString("name"));
                continents.add(continent);
            }
        }

        return continents;
    }


}
