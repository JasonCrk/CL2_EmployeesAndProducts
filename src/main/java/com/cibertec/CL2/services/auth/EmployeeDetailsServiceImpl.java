package com.cibertec.CL2.services.auth;

import com.cibertec.CL2.models.EmployeeEntity;
import com.cibertec.CL2.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeEntity employee = this.employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El empleado no existe"));

        return new User(
                employee.getUsername(),
                employee.getKey(),
                employee.getIsEnabled(),
                employee.getIsAccountNonExpired(),
                employee.getIsAccountNonLocked(),
                employee.getIsCredentialsNonExpired(),
                List.of(new SimpleGrantedAuthority(employee.getRole().name()))
        );
    }
}
