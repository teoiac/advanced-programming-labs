package org.example.service;

import org.example.model.City;
import org.example.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities(){
        return this.cityRepository.findAll();
    }

    public Optional<City> getCityById(Long id){
        return cityRepository.findById(id);
    }

    public City createCity(City city){
        return this.cityRepository.save(city);
    }

    public City updateCity(Long id, City cityDetails) {
        Optional<City> optionalCity = cityRepository.findById(id);

        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setName(cityDetails.getName());
            city.setCapital(cityDetails.isCapital());
            city.setLatitude(cityDetails.getLatitude());
            city.setLongitude(cityDetails.getLongitude());
            if (cityDetails.getCountry() != null) {
                city.setCountry(cityDetails.getCountry());
            }
            return cityRepository.save(city);
        } else {
            throw new RuntimeException("city not found");
        }
    }

    public void deleteCityById(Long id){
        cityRepository.deleteById(id);
    }

}
