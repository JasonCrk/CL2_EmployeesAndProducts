package com.cibertec.CL2.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "session_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean revoked;

    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @PrePersist
    public void setDefaultValues() {
        this.revoked = false;
        this.expired = false;
    }
}
