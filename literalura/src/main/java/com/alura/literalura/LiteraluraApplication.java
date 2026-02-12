package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase principal que inicia Spring Boot
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final Principal principal;

	// Inyección por constructor (mejor práctica)
	public LiteraluraApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	// Método que se ejecuta al iniciar la aplicación
	@Override
	public void run(String... args) {
		principal.muestraMenu(); // Delegamos la lógica a otra clase
	}
}

