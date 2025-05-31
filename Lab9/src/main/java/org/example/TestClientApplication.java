//package org.example;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.example.client.ApiClient;
//import org.example.model.City;
//import org.example.model.Country;
//import java.util.List;
//
//@Component
//public class TestClientApplication implements CommandLineRunner {
//
//    @Autowired
//    private ApiClient apiClient;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // testApiClient();
//    }
//
//    private void testApiClient() {
//        System.out.println("=== Testing Geography API Client ===");
//
//        try {
//            // Test getting all countries
//            System.out.println("\n1. Getting all countries:");
//            List<Country> countries = apiClient.getAllCountries();
//            countries.forEach(System.out::println);
//
//            // Test creating a new country
//            System.out.println("\n2. Creating a new country:");
//            Country newCountry = new Country("Test Country", "TC");
//            Country createdCountry = apiClient.createCountry(newCountry);
//            System.out.println("Created: " + createdCountry);
//
//            // Test getting country by ID
//            if (createdCountry != null) {
//                System.out.println("\n3. Getting country by ID:");
//                Country fetchedCountry = apiClient.getCountryById(createdCountry.getId());
//                System.out.println("Fetched: " + fetchedCountry);
//            }
//
//            // Test getting all cities
//            System.out.println("\n4. Getting all cities:");
//            List<City> cities = apiClient.getAllCities();
//            cities.forEach(System.out::println);
//
//            // Test creating a new city
//            System.out.println("\n5. Creating a new city:");
//            City newCity = new City("Test City", false, 45.0, 25.0);
//            if (createdCountry != null) {
//                newCity.setCountry(createdCountry);
//            }
//            City createdCity = apiClient.createCity(newCity);
//            System.out.println("Created: " + createdCity);
//            if (createdCity != null) {
//                System.out.println("\n6. Updating city name:");
//                createdCity.setName("Updated Test City");
//                apiClient.updateCity(createdCity.getId(), createdCity);
//                City updatedCity = apiClient.getCityById(createdCity.getId());
//                System.out.println("Updated: " + updatedCity);
//            }
//
//            if (createdCity != null) {
//                System.out.println("\n7. Deleting created city:");
//                apiClient.deleteCity(createdCity.getId());
//                System.out.println("City deleted");
//            }
//
//            if (createdCountry != null) {
//                System.out.println("\n8. Deleting created country:");
//                apiClient.deleteCountry(createdCountry.getId());
//                System.out.println("Country deleted");
//            }
//
//        } catch (Exception e) {
//            System.err.println("Error testing API client: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}