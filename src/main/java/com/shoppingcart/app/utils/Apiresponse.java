package com.shoppingcart.app.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    // Private constructor to prevent instantiation
    private ApiResponse() {
    }

    public static ResponseEntity<Map<String, Object>> jsonResponse(HttpStatus code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        if (code == null) {
            throw new IllegalArgumentException("HttpStatus code must not be null");
        }

        boolean isSuccess = code.value() != 500;
        response.put("status", isSuccess ? "success" : "error");
        response.put("code", code.value());
        response.put("data", data != null ? data : null);
        response.put("message", message != null ? message : null);

        return new ResponseEntity<>(response, code);
    }

}
