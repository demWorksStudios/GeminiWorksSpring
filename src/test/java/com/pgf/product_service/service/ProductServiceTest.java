package com.pgf.product_service.service;

import com.pgf.product_service.entity.Product;
import com.pgf.product_service.exception.ResourceNotFoundException;
import com.pgf.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product prod1;
    private Product prod2;

    @BeforeEach
    void setUp() {
        prod1 = Product.builder()
                .id(1L)
                .name("Wireless Noise-Canceling Headphones")
                .description("High-fidelity audio with active noise cancellation")
                .price(199.99)
                .stockQuantity(50)
                .category("Electronics")
                .sku("ELEC-WNC-001")
                .build();

        prod2 = Product.builder()
                .id(2L)
                .name("Ergonomic Mechanical Keyboard")
                .description("RGB backlit mechanical keyboard with hot-swappable switches")
                .price(129.50)
                .stockQuantity(30)
                .category("Electronics")
                .sku("ELEC-EMK-002")
                .build();
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(prod1);

        Product saved = productService.createProduct(prod1);

        assertNotNull(saved);
        assertEquals("Wireless Noise-Canceling Headphones", saved.getName());
        verify(productRepository, times(1)).save(prod1);
    }

    @Test
    void testSaveAllProducts() {
        List<Product> productList = Arrays.asList(prod1, prod2);
        when(productRepository.saveAll(productList)).thenReturn(productList);

        List<Product> savedList = productService.saveAllProducts(productList);

        assertNotNull(savedList);
        assertEquals(2, savedList.size());
        verify(productRepository, times(1)).saveAll(productList);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(prod1, prod2));

        List<Product> list = productService.getAllProducts();

        assertEquals(2, list.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(prod1));

        Product found = productService.getProductById(1L);

        assertEquals("Wireless Noise-Canceling Headphones", found.getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    void testGetProductsByCategory() {
        when(productRepository.findByCategory("Electronics")).thenReturn(Arrays.asList(prod1, prod2));

        List<Product> list = productService.getProductsByCategory("Electronics");

        assertEquals(2, list.size());
        verify(productRepository, times(1)).findByCategory("Electronics");
    }

    @Test
    void testSearchProductsByName() {
        when(productRepository.findByNameContainingIgnoreCase("Keyboard")).thenReturn(List.of(prod2));

        List<Product> list = productService.searchProductsByName("Keyboard");

        assertEquals(1, list.size());
        assertEquals("Ergonomic Mechanical Keyboard", list.get(0).getName());
    }

    @Test
    void testUpdateProduct() {
        Product updatedDetails = Product.builder()
                .name("Wireless Noise-Canceling Headphones Pro")
                .description("Upgraded battery life")
                .price(229.99)
                .stockQuantity(40)
                .category("Electronics")
                .sku("ELEC-WNC-001-PRO")
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(prod1));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.updateProduct(1L, updatedDetails);

        assertEquals("Wireless Noise-Canceling Headphones Pro", result.getName());
        assertEquals(229.99, result.getPrice());
        assertEquals(40, result.getStockQuantity());
    }

    @Test
    void testDeleteProduct_Success() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(99L));
    }
}
