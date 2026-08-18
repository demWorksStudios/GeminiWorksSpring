package com.saree.inventory.config;

import com.saree.inventory.model.SareeInventory;
import com.saree.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InventoryDataInitializer {

    @Bean
    CommandLineRunner initInventoryData(InventoryRepository inventoryRepository) {
        return args -> {
            if (inventoryRepository.count() == 0) {
                List<SareeInventory> inventories = List.of(
                        SareeInventory.builder().sku("SAR-KAN-RED-001").availableStock(25).reservedStock(0).build(),
                        SareeInventory.builder().sku("SAR-BAN-GRN-002").availableStock(18).reservedStock(0).build(),
                        SareeInventory.builder().sku("SAR-CHA-BLU-003").availableStock(40).reservedStock(0).build(),
                        SareeInventory.builder().sku("SAR-TUS-YEL-004").availableStock(15).reservedStock(0).build(),
                        SareeInventory.builder().sku("SAR-ORG-PNK-005").availableStock(50).reservedStock(0).build(),
                        SareeInventory.builder().sku("SAR-BAN-MAR-006").availableStock(12).reservedStock(0).build()
                );
                inventoryRepository.saveAll(inventories);
            }
        };
    }
}
