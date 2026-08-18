package com.pgf.product_service.controller;

import com.pgf.product_service.entity.Product;
import com.pgf.product_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

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
        when(productService.createProduct(any(Product.class))).thenReturn(prod1);

        ResponseEntity<Product> response = productController.createProduct(prod1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Wireless Noise-Canceling Headphones", response.getBody().getName());
    }

    @Test
    void testSaveAllProducts() {
        List<Product> list = Arrays.asList(prod1, prod2);
        when(productService.saveAllProducts(list)).thenReturn(list);

        ResponseEntity<List<Product>> response = productController.saveAllProducts(list);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(prod1, prod2));

        ResponseEntity<List<Product>> response = productController.getAllProducts(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllProductsByCategory() {
        when(productService.getProductsByCategory("Electronics")).thenReturn(Arrays.asList(prod1, prod2));

        ResponseEntity<List<Product>> response = productController.getAllProducts("Electronics", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllProductsBySearch() {
        when(productService.searchProductsByName("Headphones")).thenReturn(List.of(prod1));

        ResponseEntity<List<Product>> response = productController.getAllProducts(null, "Headphones");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(1L)).thenReturn(prod1);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wireless Noise-Canceling Headphones", response.getBody().getName());
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(prod1);

        ResponseEntity<Product> response = productController.updateProduct(1L, prod1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wireless Noise-Canceling Headphones", response.getBody().getName());
    }

    @Test
    void testDeleteProduct() {
        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
