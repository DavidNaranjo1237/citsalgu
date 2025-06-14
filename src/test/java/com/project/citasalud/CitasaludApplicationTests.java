package com.project.citasalud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import com.project.citasalud.auth.AuthController;
import com.project.citasalud.auth.AuthResponse;
import com.project.citasalud.auth.LoginRequest;
import com.project.citasalud.auth.RegisterRequest;

@SpringBootTest
@ComponentScan(basePackages = "com.project.citasalud")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CitasaludApplicationTests {

	@Autowired
	private AuthController authController;

	@Test
	@Order(1)
	void contextLoads() {
		assertNotNull(authController, "El controlador AuthController no debería ser null");
	}

	@Test
	@Order(2)
	void shouldRegisterSuccessfully() {
		// Provide all required arguments for RegisterRequest constructor
		RegisterRequest request = new RegisterRequest(
				"104054354", // cedula
				"Password123*", // contraseña
				"David", // nombre
				"Naranjo", // apellido
				"Antioquia", // departamento
				"Medellin", // ciudad
				"carrera 41 # 34-56", // direccion
				"dno@gmail.com", // email
				"3215643267" // telefono celular
		);
		ResponseEntity<AuthResponse> response = authController.register(request);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		AuthResponse responseBody = response.getBody();
		if (responseBody != null) {
			assertNotNull(responseBody.getToken());
		}
	}

	@Test
	@Order(3)
	void shouldLoginSuccessfully() {
		LoginRequest request = new LoginRequest("104054354", "Password123*");
		ResponseEntity<AuthResponse> response = authController.login(request);
		assertEquals(200, response.getStatusCode().value());
		AuthResponse body = response.getBody();
		assertNotNull(body);
		assertNotNull(body.getToken());

	}
}
