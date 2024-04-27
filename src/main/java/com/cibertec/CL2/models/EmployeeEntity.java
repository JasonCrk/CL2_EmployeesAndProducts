package com.cibertec.CL2.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int id;

    @Column(name = "ape_materno", nullable = false)
    private String motherLastName;

    @Column(name = "ape_paterno", nullable = false)
    private String lastName;

    @Column(name = "nombres", nullable = false)
    private String name;

    @Column(name = "usuario", unique = true, nullable = false)
    private String username;

    @Column(name = "clave", unique = true, nullable = false)
    private String key;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<SessionTokenEntity> sessionTokens;
}
