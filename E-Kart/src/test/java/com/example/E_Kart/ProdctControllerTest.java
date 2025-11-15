package com.example.E_Kart;

import com.example.E_Kart.model.Product;
import com.example.E_Kart.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdctControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Test
    void testGetProduct() throws Exception{
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");

        List<Product> productList = Arrays.asList(product);
        when(service.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].name").value("Laptop"));

    }
    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");

        when(service.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");

        MockMultipartFile imageFile =
                new MockMultipartFile("imageFile", "image.png", "image/png", "fake-image".getBytes());
        MockMultipartFile jsonFile =
                new MockMultipartFile("product", "", "application/json",
                        "{\"id\":1,\"name\":\"Laptop\"}".getBytes());

        when(service.addOrUpdateProduct(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(product);

        mockMvc.perform(multipart("/product")
                        .file(jsonFile)
                        .file(imageFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");

        when(service.getProductById(1)).thenReturn(product);

        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted"));
    }

    @Test
    void testSearchProducts() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");

        when(service.searchProducts("Laptop")).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/products/search/").param("keyword", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

}
