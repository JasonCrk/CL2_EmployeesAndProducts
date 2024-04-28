package com.cibertec.CL2;

import com.cibertec.CL2.models.EmployeeEntity;
import com.cibertec.CL2.repositories.EmployeeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Cl2EmerzonJavierKolkiMartinezApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cl2EmerzonJavierKolkiMartinezApplication.class, args);
	}

	@Bean
	public CommandLineRunner defaultEmployee(
			EmployeeRepository employeeRepository,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			employeeRepository.save(EmployeeEntity.builder()
					.name("Nombre del empleado") // Este es el username
					.username("Empleado 1")
					.key(passwordEncoder.encode("123456")) // Esta es la clave
					.lastName("Apellido paterno")
					.motherLastName("Apellido materno")
					.build());
		};
	}
}
