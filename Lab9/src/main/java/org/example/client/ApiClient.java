package org.example.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.model.City;
import org.example.model.Country;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiClient {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api";

    public ApiClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    // === COUNTRY OPERATIONS ===

    public List<Country> getAllCountries() {
        try {
            Country[] countries = restTemplate.getForObject(baseUrl + "/countries", Country[].class);
            return countries != null ? Arrays.asList(countries) : List.of();
        } catch (Exception e) {
            System.out.println("Error getting countries: " + e.getMessage());
            return List.of();
        }
    }

    public Country getCountryById(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/countries/" + id, Country.class);
        } catch (Exception e) {
            System.out.println("Error getting country " + id + ": " + e.getMessage());
            return null;
        }
    }

    public Country createCountry(Country country) {
        try {
            return restTemplate.postForObject(baseUrl + "/countries", country, Country.class);
        } catch (Exception e) {
            System.out.println("Error creating country: " + e.getMessage());
            return null;
        }
    }

    public boolean updateCountry(Long id, Country country) {
        try {
            restTemplate.put(baseUrl + "/countries/" + id, country);
            return true;
        } catch (Exception e) {
            System.out.println("Error updating country " + id + ": " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCountry(Long id) {
        try {
            restTemplate.delete(baseUrl + "/countries/" + id);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting country " + id + ": " + e.getMessage());
            return false;
        }
    }

    // === CITY OPERATIONS ===

    public List<City> getAllCities() {
        try {
            City[] cities = restTemplate.getForObject(baseUrl + "/cities", City[].class);
            return cities != null ? Arrays.asList(cities) : List.of();
        } catch (Exception e) {
            System.out.println("Error getting cities: " + e.getMessage());
            return List.of();
        }
    }

    public City getCityById(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/cities/" + id, City.class);
        } catch (Exception e) {
            System.out.println("Error getting city " + id + ": " + e.getMessage());
            return null;
        }
    }

    public City createCity(City city) {
        try {
            return restTemplate.postForObject(baseUrl + "/cities", city, City.class);
        } catch (Exception e) {
            System.out.println("Error creating city: " + e.getMessage());
            return null;
        }
    }

    public boolean updateCity(Long id, City city) {
        try {
            restTemplate.put(baseUrl + "/cities/" + id, city);
            return true;
        } catch (Exception e) {
            System.out.println("Error updating city " + id + ": " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCity(Long id) {
        try {
            restTemplate.delete(baseUrl + "/cities/" + id);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting city " + id + ": " + e.getMessage());
            return false;
        }
    }
}