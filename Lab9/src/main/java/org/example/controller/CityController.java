package org.example.controller;

import org.example.model.City;
import org.example.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cities")
public class CityController {

    private CityService cityService;
    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    //get all cities

    @GetMapping
    public List<City> getAllCities(){
        return this.cityService.getAllCities();
    }

    //get cities by id

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        Optional<City> optionalCity = cityService.getCityById(id);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            return ResponseEntity.ok().body(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //post city

    @PostMapping
    public  ResponseEntity<City> createCity(@RequestBody City city){
        City createdCity = this.cityService.createCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    //put - edit a existing city

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        try {
            City updatedCity = cityService.updateCity(id, cityDetails);
            return ResponseEntity.ok(updatedCity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //delete a city

    @DeleteMapping
    public ResponseEntity<City> deleteCityById(@PathVariable Long id) {
        try{
            cityService.deleteCityById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
