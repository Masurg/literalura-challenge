package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository; //

	@Autowired
	private AutorRepository autorRepository; //

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Esta línea es la que da error si el constructor de Principal no coincide
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}
}