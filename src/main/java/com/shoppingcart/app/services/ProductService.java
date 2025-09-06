package com.shoppingcart.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shoppingcart.app.dto.ProductDto;
import com.shoppingcart.app.model.Product;
import com.shoppingcart.app.repositories.ProductRepository;

@Service
public class ProductService implements IProductService {

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        try {
            ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al llamar la API: " + e.getMessage(), e);
        }
    }

    @Override
    public ProductDto getProductById(Long id) {
        String findById = "";
        findById = apiUrl + "/" + id;
        try {
            ResponseEntity<ProductDto> response = restTemplate.getForEntity(findById, ProductDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al llamar la API: " + e.getMessage(), e);
        }
    }

    @Override
    public Product createProduct(ProductDto product) {
        Product newProduct = Product.builder()
                .name(product.getTitle())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .build();
        return this.productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(ProductDto order) {
        Optional<Product> foundProduct = this.productRepository.findById(order.getId());
        if(foundProduct.isPresent()){
            Product product = foundProduct.get();
            product = Product.builder()
                    .productId(product.getProductId())
                    .name(order.getTitle())
                    .description(order.getDescription())
                    .image(order.getImage())
                    .price(order.getPrice())
                    .build();
            return this.productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }
    
}
