package com.cibertec.CL2.services.product;

import com.cibertec.CL2.models.mappers.ProductMapper;
import com.cibertec.CL2.models.responses.ProductItemResponse;
import com.cibertec.CL2.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public List<ProductItemResponse> getAll() {
        return this.productRepository.findAll().stream()
                .map(productMapper::toItem)
                .toList();
    }
}
