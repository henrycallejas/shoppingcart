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
import com.shoppingcart.app.enums.ResponseMessage;
import com.shoppingcart.app.model.Product;
import com.shoppingcart.app.services.ProductService;
import com.shoppingcart.app.utils.ApiResponse;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
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

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody ProductDto productDto) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id) {
        try {
            this.productService.deleteProduct(id);
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), id);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), e.getMessage());
        }
    }
}
