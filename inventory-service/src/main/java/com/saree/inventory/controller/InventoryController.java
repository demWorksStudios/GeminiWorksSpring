package com.saree.inventory.controller;

import com.saree.inventory.dto.RestockRequest;
import com.saree.inventory.dto.StockCheckResponse;
import com.saree.inventory.dto.StockReservationRequest;
import com.saree.inventory.model.SareeInventory;
import com.saree.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<SareeInventory> createInventory(@Valid @RequestBody SareeInventory inventory) {
        SareeInventory saved = inventoryService.createInventoryRecord(inventory);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<SareeInventory>> saveAllInventories(@Valid @RequestBody List<SareeInventory> inventories) {
        List<SareeInventory> saved = inventoryService.saveAllInventoryRecords(inventories);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SareeInventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/check/{sku}")
    public ResponseEntity<StockCheckResponse> checkStock(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.checkStock(sku));
    }

    @GetMapping("/check-bulk")
    public ResponseEntity<List<StockCheckResponse>> checkBulkStock(@RequestParam List<String> skus) {
        return ResponseEntity.ok(inventoryService.checkBulkStock(skus));
    }

    @PostMapping("/reserve")
    public ResponseEntity<SareeInventory> reserveStock(@Valid @RequestBody StockReservationRequest request) {
        return ResponseEntity.ok(inventoryService.reserveStock(request));
    }

    @PutMapping("/restock")
    public ResponseEntity<SareeInventory> restock(@Valid @RequestBody RestockRequest request) {
        return ResponseEntity.ok(inventoryService.restock(request));
    }
}
