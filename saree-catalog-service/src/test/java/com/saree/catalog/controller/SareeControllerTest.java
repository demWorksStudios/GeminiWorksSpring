package com.saree.catalog.controller;

import com.saree.catalog.model.Saree;
import com.saree.catalog.service.SareeService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SareeControllerTest {

    @Mock
    private SareeService sareeService;

    @InjectMocks
    private SareeController sareeController;

    private Saree saree1;

    @BeforeEach
    void setUp() {
        saree1 = Saree.builder()
                .id(1L)
                .name("Royal Crimson Kanjeevaram Pure Silk Saree")
                .fabric("Kanjeevaram Silk")
                .color("Crimson Red")
                .price(18999.00)
                .sku("SAR-KAN-RED-001")
                .build();
    }

    @Test
    void testCreateSaree() {
        when(sareeService.createSaree(any(Saree.class))).thenReturn(saree1);
        ResponseEntity<Saree> response = sareeController.createSaree(saree1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Royal Crimson Kanjeevaram Pure Silk Saree", response.getBody().getName());
    }

    @Test
    void testSaveAllSarees() {
        List<Saree> list = List.of(saree1);
        when(sareeService.saveAllSarees(list)).thenReturn(list);
        ResponseEntity<List<Saree>> response = sareeController.saveAllSarees(list);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllSarees() {
        when(sareeService.getAllSarees()).thenReturn(List.of(saree1));
        ResponseEntity<List<Saree>> response = sareeController.getAllSarees(null, null, null, null, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetSareeById() {
        when(sareeService.getSareeById(1L)).thenReturn(saree1);
        ResponseEntity<Saree> response = sareeController.getSareeById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SAR-KAN-RED-001", response.getBody().getSku());
    }

    @Test
    void testDeleteSaree() {
        ResponseEntity<Void> response = sareeController.deleteSaree(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sareeService, times(1)).deleteSaree(1L);
    }
}
