package com.salah.aircraftservice;

import com.salah.aircraftservice.model.Aircraft;
import com.salah.aircraftservice.repository.AircraftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class AircraftServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AircraftServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(AircraftRepository repository) {
		return args -> {
			if (repository.count() == 0) { // 🛑 Pour éviter les doublons à chaque redémarrage
				repository.save(Aircraft.builder()
						.code("A320")
						.type("court")
						.capacity(180)
						.airline("Air France")
						.status("actif")
						.build());

				repository.save(Aircraft.builder()
						.code("B737")
						.type("moyen")
						.capacity(160)
						.airline("Ryanair")
						.status("maintenance")
						.build());

				repository.save(Aircraft.builder()
						.code("A350")
						.type("long")
						.capacity(300)
						.airline("Emirates")
						.status("actif")
						.build());

				System.out.println("✅ Aircrafts préchargés en base.");
			}
		};
	}
}
