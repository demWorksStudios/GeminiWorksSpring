package com.saree.catalog.repository;

import com.saree.catalog.model.Saree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SareeRepository extends JpaRepository<Saree, Long> {

    Optional<Saree> findBySku(String sku);

    List<Saree> findByFabricIgnoreCase(String fabric);

    List<Saree> findByOccasionIgnoreCase(String occasion);

    List<Saree> findByColorIgnoreCase(String color);

    List<Saree> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT s FROM Saree s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(s.fabric) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(s.occasion) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Saree> searchSarees(@Param("query") String query);
}
