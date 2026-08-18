package com.saree.inventory.service;

import com.saree.inventory.dto.RestockRequest;
import com.saree.inventory.dto.StockCheckResponse;
import com.saree.inventory.dto.StockReservationRequest;
import com.saree.inventory.exception.InsufficientStockException;
import com.saree.inventory.exception.ResourceNotFoundException;
import com.saree.inventory.model.SareeInventory;
import com.saree.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public SareeInventory createInventoryRecord(SareeInventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<SareeInventory> saveAllInventoryRecords(List<SareeInventory> inventories) {
        return inventoryRepository.saveAll(inventories);
    }

    @Transactional(readOnly = true)
    public List<SareeInventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SareeInventory getInventoryBySku(String sku) {
        return inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory record not found for SKU: " + sku));
    }

    @Transactional(readOnly = true)
    public StockCheckResponse checkStock(String sku) {
        SareeInventory inventory = getInventoryBySku(sku);
        return StockCheckResponse.builder()
                .sku(inventory.getSku())
                .inStock(inventory.getAvailableStock() > 0)
                .availableQuantity(inventory.getAvailableStock())
                .build();
    }

    @Transactional(readOnly = true)
    public List<StockCheckResponse> checkBulkStock(List<String> skus) {
        return inventoryRepository.findBySkuIn(skus).stream()
                .map(inv -> StockCheckResponse.builder()
                        .sku(inv.getSku())
                        .inStock(inv.getAvailableStock() > 0)
                        .availableQuantity(inv.getAvailableStock())
                        .build())
                .collect(Collectors.toList());
    }

    public SareeInventory reserveStock(StockReservationRequest request) {
        SareeInventory inventory = getInventoryBySku(request.getSku());

        if (inventory.getAvailableStock() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for SKU " + request.getSku() +
                    ". Available: " + inventory.getAvailableStock() + ", Requested: " + request.getQuantity());
        }

        inventory.setAvailableStock(inventory.getAvailableStock() - request.getQuantity());
        int reserved = (inventory.getReservedStock() != null ? inventory.getReservedStock() : 0) + request.getQuantity();
        inventory.setReservedStock(reserved);

        return inventoryRepository.save(inventory);
    }

    public SareeInventory restock(RestockRequest request) {
        SareeInventory inventory = getInventoryBySku(request.getSku());
        inventory.setAvailableStock(inventory.getAvailableStock() + request.getAdditionalQuantity());
        return inventoryRepository.save(inventory);
    }
}
