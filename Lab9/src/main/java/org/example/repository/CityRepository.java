package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.example.model.City;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.name LIKE %:name%")
    List<City> findByNameContaining(@Param("name") String name);

    List<City> findByCapital(boolean capital);

    List<City> findByCountryId(Long countryId);
}