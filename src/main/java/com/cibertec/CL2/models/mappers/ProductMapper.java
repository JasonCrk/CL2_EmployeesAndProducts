package com.cibertec.CL2.models.mappers;

import com.cibertec.CL2.models.ProductEntity;
import com.cibertec.CL2.models.responses.ProductItemResponse;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductItemResponse toItem(ProductEntity product) {
        return ProductItemResponse.builder()
                .id(product.getId())
                .createdAt(product.getCreatedAt())
                .stock(product.getStock())
                .description(product.getDescription())
                .precio(product.getPrice())
                .build();
    }
}
