package com.saree.inventory.controller;

import com.saree.inventory.dto.RestockRequest;
import com.saree.inventory.dto.StockCheckResponse;
import com.saree.inventory.dto.StockReservationRequest;
import com.saree.inventory.model.SareeInventory;
import com.saree.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private SareeInventory inv1;

    @BeforeEach
    void setUp() {
        inv1 = SareeInventory.builder()
                .id(1L)
                .sku("SAR-KAN-RED-001")
                .availableStock(10)
                .reservedStock(2)
                .build();
    }

    @Test
    void testCheckStock() {
        when(inventoryService.checkStock("SAR-KAN-RED-001"))
                .thenReturn(StockCheckResponse.builder().sku("SAR-KAN-RED-001").inStock(true).availableQuantity(10).build());

        ResponseEntity<StockCheckResponse> response = inventoryController.checkStock("SAR-KAN-RED-001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SAR-KAN-RED-001", response.getBody().getSku());
    }

    @Test
    void testReserveStock() {
        StockReservationRequest request = StockReservationRequest.builder()
                .sku("SAR-KAN-RED-001")
                .quantity(2)
                .build();

        when(inventoryService.reserveStock(any(StockReservationRequest.class))).thenReturn(inv1);

        ResponseEntity<SareeInventory> response = inventoryController.reserveStock(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SAR-KAN-RED-001", response.getBody().getSku());
    }

    @Test
    void testRestock() {
        RestockRequest request = RestockRequest.builder()
                .sku("SAR-KAN-RED-001")
                .additionalQuantity(10)
                .build();

        when(inventoryService.restock(any(RestockRequest.class))).thenReturn(inv1);

        ResponseEntity<SareeInventory> response = inventoryController.restock(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllInventories() {
        when(inventoryService.getAllInventories()).thenReturn(List.of(inv1));

        ResponseEntity<List<SareeInventory>> response = inventoryController.getAllInventories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
