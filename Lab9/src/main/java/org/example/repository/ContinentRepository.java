package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.example.model.Continent;
import java.util.List;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
    @Query("SELECT c FROM Continent c WHERE c.name LIKE %:name%")
    List<Continent> findByNameContaining(@Param("name") String name);
}