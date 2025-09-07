package com.shoppingcart.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.app.dto.ProductDto;
import com.shoppingcart.app.entities.Product;
import com.shoppingcart.app.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testGetProductById() {
        Long productId = 1L;
        ProductDto mockProductDto = new ProductDto();
        mockProductDto.setId(productId);
        mockProductDto.setTitle("Test Product");

        ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(mockProductDto, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(ProductDto.class)))
                .thenReturn(responseEntity);

        ProductDto productDto = productService.getProductById(productId);

        assertNotNull(productDto);
        assertEquals(productId, productDto.getId());
    }

    @Test
    void testCreateProduct() {
        // Arrange
        ProductDto newProductDto = new ProductDto();
        newProductDto.setTitle("Test Product");
        newProductDto.setDescription("Test Desc");
        newProductDto.setPrice(BigDecimal.valueOf(100));
        newProductDto.setImage("img.jpg");

        Product savedProduct = new Product();
        savedProduct.setName("Test Product");
        savedProduct.setDescription("Test Desc");
        savedProduct.setPrice(BigDecimal.valueOf(100));
        savedProduct.setImage("img.jpg");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        Product createdProduct = productService.createProduct(newProductDto);
        ProductDto createdProductDto = new ProductDto();
        createdProductDto.setTitle(createdProduct.getName());
        createdProductDto.setDescription(createdProduct.getDescription());
        createdProductDto.setPrice(createdProduct.getPrice());
        createdProductDto.setImage(createdProduct.getImage());

        // Assert
        assertNotNull(createdProductDto);
        assertEquals("Test Product", createdProductDto.getTitle());
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Desc");
        existingProduct.setPrice(BigDecimal.valueOf(50));
        existingProduct.setImage("old.jpg");

        ProductDto updateDto = new ProductDto();
        updateDto.setId(productId);
        updateDto.setTitle("Updated Name");
        updateDto.setDescription("Updated Desc");
        updateDto.setPrice(BigDecimal.valueOf(150));
        updateDto.setImage("updated.jpg");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            existingProduct.setName(p.getName());
            existingProduct.setDescription(p.getDescription());
            existingProduct.setPrice(p.getPrice());
            existingProduct.setImage(p.getImage());
            return existingProduct;
        });

        Product updatedProduct = productService.updateProduct(updateDto);
        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setTitle(updatedProduct.getName());
        updatedProductDto.setDescription(updatedProduct.getDescription());
        updatedProductDto.setPrice(updatedProduct.getPrice());
        updatedProductDto.setImage(updatedProduct.getImage());

        assertNotNull(updatedProductDto);
        assertEquals("Updated Name", updatedProductDto.getTitle());
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository).deleteById(productId);
    }
}
