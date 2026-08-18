package com.saree.inventory.repository;

import com.saree.inventory.model.SareeInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<SareeInventory, Long> {

    Optional<SareeInventory> findBySku(String sku);

    List<SareeInventory> findBySkuIn(List<String> skus);
}
