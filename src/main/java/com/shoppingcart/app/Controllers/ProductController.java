package com.shoppingcart.app.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.app.dto.ProductDto;
import com.shoppingcart.app.entities.Product;
import com.shoppingcart.app.enums.ResponseMessage;
import com.shoppingcart.app.services.ProductService;
import com.shoppingcart.app.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        try {
            List<ProductDto> orders = this.productService.getAllProducts();
            if (!orders.isEmpty()) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), orders);
            }
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), orders);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(),
                    e.getMessage());
        }

    }

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@Parameter(description = "ID of the product to retrieve") @PathVariable Long id) {
        try {
            ProductDto product = this.productService.getProductById(id);
            if (product != null) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), product);
            }
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), product);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(),
                    e.getMessage());
        }

    }

    @Operation(summary = "Create a new product", description = "Create a new product with the provided information")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductDto product) {
        try {
            ProductDto productDto = this.productService.getProductById(product.getId());
            Product createdProduct = this.productService.createProduct(productDto);
            if (createdProduct != null) {
                return ApiResponse.jsonResponse(HttpStatus.CREATED, ResponseMessage.CREATED.getMessage(),
                        createdProduct);
            }
            return ApiResponse.jsonResponse(HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND.getMessage(),
                    createdProduct);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(),
                    e.getMessage());
        }
    }

    @Operation(summary = "Update an existing product", description = "Update an existing product with the provided information")
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody ProductDto productDto) {
        try {
            Product updatedProduct = this.productService.updateProduct(productDto);
            if (updatedProduct != null) {
                return ApiResponse.jsonResponse(HttpStatus.CREATED, ResponseMessage.CREATED.getMessage(),
                        updatedProduct);
            } else {
                return ApiResponse.jsonResponse(HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND.getMessage(),
                        updatedProduct);
            }
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(),
                    e.getMessage());
        }
    }

    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@Parameter(description = "ID of the product to delete") @PathVariable Long id) {
        try {
            this.productService.deleteProduct(id);
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), id);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), e.getMessage());
        }
    }
}
