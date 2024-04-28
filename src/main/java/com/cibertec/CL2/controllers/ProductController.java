package com.cibertec.CL2.controllers;

import com.cibertec.CL2.models.responses.ProductItemResponse;
import com.cibertec.CL2.services.product.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductItemResponse>> getAllProducts() {
        return ResponseEntity.ok(this.service.getAll());
    }
}
