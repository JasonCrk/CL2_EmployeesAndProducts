package com.cibertec.CL2.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int id;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "fec_registro", columnDefinition = "DATE", nullable = false)
    private LocalDate createdAt;

    @Column(name = "precio", columnDefinition = "DOUBLE", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private int stock;
}
