package com.saree.catalog.service;

import com.saree.catalog.exception.ResourceNotFoundException;
import com.saree.catalog.model.Saree;
import com.saree.catalog.repository.SareeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SareeServiceTest {

    @Mock
    private SareeRepository sareeRepository;

    @InjectMocks
    private SareeService sareeService;

    private Saree saree1;

    @BeforeEach
    void setUp() {
        saree1 = Saree.builder()
                .id(1L)
                .name("Royal Crimson Kanjeevaram Pure Silk Saree")
                .description("Handwoven pure mulberry silk")
                .fabric("Kanjeevaram Silk")
                .color("Crimson Red")
                .pattern("Temple Border Zari")
                .occasion("Bridal")
                .blousePieceIncluded(true)
                .price(18999.00)
                .discountPercentage(10.0)
                .sku("SAR-KAN-RED-001")
                .imageUrl("https://example.com/saree.jpg")
                .rating(4.9)
                .build();
    }

    @Test
    void testCreateSaree() {
        when(sareeRepository.save(any(Saree.class))).thenReturn(saree1);
        Saree created = sareeService.createSaree(saree1);
        assertNotNull(created);
        assertEquals("Royal Crimson Kanjeevaram Pure Silk Saree", created.getName());
    }

    @Test
    void testSaveAllSarees() {
        List<Saree> list = List.of(saree1);
        when(sareeRepository.saveAll(list)).thenReturn(list);
        List<Saree> result = sareeService.saveAllSarees(list);
        assertEquals(1, result.size());
    }

    @Test
    void testGetSareeById() {
        when(sareeRepository.findById(1L)).thenReturn(Optional.of(saree1));
        Saree found = sareeService.getSareeById(1L);
        assertEquals("Kanjeevaram Silk", found.getFabric());
    }

    @Test
    void testGetSareeById_NotFound() {
        when(sareeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sareeService.getSareeById(99L));
    }

    @Test
    void testDeleteSaree() {
        when(sareeRepository.existsById(1L)).thenReturn(true);
        sareeService.deleteSaree(1L);
        verify(sareeRepository, times(1)).deleteById(1L);
    }
}
