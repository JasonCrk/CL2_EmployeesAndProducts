package com.cibertec.CL2.services.product;

import com.cibertec.CL2.models.responses.ProductItemResponse;

import java.util.List;

public interface ProductService {
    List<ProductItemResponse> getAll();
}
