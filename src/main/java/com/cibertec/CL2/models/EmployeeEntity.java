package com.cibertec.CL2.models;

import com.cibertec.CL2.models.enums.Role;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleado")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer id;

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

    @Enumerated
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_enabled", columnDefinition = "BOOLEAN", nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_account_non_expired", columnDefinition = "BOOLEAN", nullable = false)
    private Boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked", columnDefinition = "BOOLEAN", nullable = false)
    private Boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired", columnDefinition = "BOOLEAN", nullable = false)
    private Boolean isCredentialsNonExpired;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<SessionTokenEntity> sessionTokens = new ArrayList<>();

    @PrePersist
    public void setDefaultValues() {
        this.role = Role.EMPLOYEE;
        this.isEnabled = true;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
    }
}
