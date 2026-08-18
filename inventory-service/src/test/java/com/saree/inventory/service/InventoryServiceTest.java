package com.saree.inventory.service;

import com.saree.inventory.dto.RestockRequest;
import com.saree.inventory.dto.StockCheckResponse;
import com.saree.inventory.dto.StockReservationRequest;
import com.saree.inventory.exception.InsufficientStockException;
import com.saree.inventory.exception.ResourceNotFoundException;
import com.saree.inventory.model.SareeInventory;
import com.saree.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

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
        when(inventoryRepository.findBySku("SAR-KAN-RED-001")).thenReturn(Optional.of(inv1));

        StockCheckResponse response = inventoryService.checkStock("SAR-KAN-RED-001");

        assertNotNull(response);
        assertTrue(response.isInStock());
        assertEquals(10, response.getAvailableQuantity());
    }

    @Test
    void testReserveStock_Success() {
        when(inventoryRepository.findBySku("SAR-KAN-RED-001")).thenReturn(Optional.of(inv1));
        when(inventoryRepository.save(any(SareeInventory.class))).thenAnswer(i -> i.getArgument(0));

        StockReservationRequest request = StockReservationRequest.builder()
                .sku("SAR-KAN-RED-001")
                .quantity(3)
                .build();

        SareeInventory updated = inventoryService.reserveStock(request);

        assertEquals(7, updated.getAvailableStock());
        assertEquals(5, updated.getReservedStock());
    }

    @Test
    void testReserveStock_InsufficientStock() {
        when(inventoryRepository.findBySku("SAR-KAN-RED-001")).thenReturn(Optional.of(inv1));

        StockReservationRequest request = StockReservationRequest.builder()
                .sku("SAR-KAN-RED-001")
                .quantity(20)
                .build();

        assertThrows(InsufficientStockException.class, () -> inventoryService.reserveStock(request));
    }

    @Test
    void testRestock() {
        when(inventoryRepository.findBySku("SAR-KAN-RED-001")).thenReturn(Optional.of(inv1));
        when(inventoryRepository.save(any(SareeInventory.class))).thenAnswer(i -> i.getArgument(0));

        RestockRequest request = RestockRequest.builder()
                .sku("SAR-KAN-RED-001")
                .additionalQuantity(15)
                .build();

        SareeInventory updated = inventoryService.restock(request);

        assertEquals(25, updated.getAvailableStock());
    }
}
