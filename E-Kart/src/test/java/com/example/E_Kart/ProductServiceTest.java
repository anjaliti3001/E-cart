package com.example.E_Kart;

import com.example.E_Kart.model.Product;
import com.example.E_Kart.repo.ProductRepo;
import com.example.E_Kart.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepo repo;

    @InjectMocks
    private ProductService service;

    private Product sampleProduct;

    @BeforeEach
    void setup(){
        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setName("Laptop");
        sampleProduct.setDescription("Gaming Laptop");
        sampleProduct.setPrice(new BigDecimal("580000"));
    }

    @Test
    void testGetAllProducts(){
        List<Product> productList = Arrays.asList(sampleProduct);
        when(repo.findAll()).thenReturn(productList);

        List<Product> result = service.getAllProducts();
        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found(){
        when(repo.findById(1)).thenReturn(Optional.of(sampleProduct));

       Product result = service.getProductById(1);
       assertEquals("Laptop", result.getName());
       verify(repo, times(1)).findById(1);

    }

    @Test
    void testGetProductById_NotFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        Product result = service.getProductById(99);

        assertNotNull(result);
        verify(repo, times(1)).findById(99);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(repo).deleteById(1);

        service.deleteProduct(1);

        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void testSearchProducts() {
        List<Product> products = Arrays.asList(sampleProduct);
        when(repo.searchProducts("Laptop")).thenReturn(products);

        List<Product> result = service.searchProducts("Laptop");

        assertEquals(1, result.size());
        verify(repo, times(1)).searchProducts("Laptop");
    }
}
