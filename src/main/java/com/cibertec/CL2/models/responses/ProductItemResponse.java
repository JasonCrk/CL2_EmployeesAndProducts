package com.cibertec.CL2.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemResponse {
    private Integer id;
    private String description;
    private LocalDate createdAt;
    private BigDecimal precio;
    private int stock;
}
