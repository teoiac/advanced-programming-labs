package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.example.model.City;
import org.example.model.Country;
import org.example.model.Continent;
import org.example.repository.CityRepository;
import org.example.repository.CountryRepository;
import org.example.repository.ContinentRepository;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void run(String... args) throws Exception {
        if (continentRepository.count() == 0) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        System.out.println("Initializing sample data...");
        cityRepository.deleteAll();
        countryRepository.deleteAll();
        continentRepository.deleteAll();


        Continent europe = new Continent("Europe");
        Continent asia = new Continent("Asia");
        Continent northAmerica = new Continent("North America");

        continentRepository.save(europe);
        continentRepository.save(asia);
        continentRepository.save(northAmerica);


        Country romania = new Country("Romania", "RO");
        romania.setContinent(europe);

        Country france = new Country("France", "FR");
        france.setContinent(europe);

        Country japan = new Country("Japan", "JP");
        japan.setContinent(asia);

        Country usa = new Country("United States", "US");
        usa.setContinent(northAmerica);

        countryRepository.save(romania);
        countryRepository.save(france);
        countryRepository.save(japan);
        countryRepository.save(usa);


        City bucharest = new City("Bucharest", true, 44.4268, 26.1025);
        bucharest.setCountry(romania);

        City cluj = new City("Cluj-Napoca", false, 46.7712, 23.6236);
        cluj.setCountry(romania);

        City paris = new City("Paris", true, 48.8566, 2.3522);
        paris.setCountry(france);

        City tokyo = new City("Tokyo", true, 35.6762, 139.6503);
        tokyo.setCountry(japan);

        City newYork = new City("New York", false, 40.7128, -74.0060);
        newYork.setCountry(usa);

        cityRepository.save(bucharest);
        cityRepository.save(cluj);
        cityRepository.save(paris);
        cityRepository.save(tokyo);
        cityRepository.save(newYork);

        System.out.println("Sample data initialized successfully!");
        System.out.println("Created " + continentRepository.count() + " continents");
        System.out.println("Created " + countryRepository.count() + " countries");
        System.out.println("Created " + cityRepository.count() + " cities");
    }
}