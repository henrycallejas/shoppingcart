package com.shoppingcart.app.services;

import java.util.List;

import com.shoppingcart.app.dto.ProductDto;
import com.shoppingcart.app.entities.Product;

public interface IProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    Product createProduct(ProductDto product);
    Product updateProduct(ProductDto product);
    void deleteProduct(Long id);
    }
