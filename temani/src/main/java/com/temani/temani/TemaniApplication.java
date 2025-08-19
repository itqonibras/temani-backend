package com.temani.temani;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.temani.temani.features.profile.infrastructure.persistence.RoleEntity;
import com.temani.temani.features.profile.infrastructure.persistence.RoleJpaRepository;

@SpringBootApplication
public class TemaniApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemaniApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(RoleJpaRepository roleJpaRepository) {
		return args -> {
			RoleEntity userRole = roleJpaRepository.findByName("USER")
					.orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "USER")));
			RoleEntity clientRole = roleJpaRepository.findByName("CLIENT")
					.orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "CLIENT")));
			RoleEntity caregiverRole = roleJpaRepository.findByName("CAREGIVER")
					.orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "CAREGIVER")));
			RoleEntity peerRole = roleJpaRepository.findByName("PEER")
					.orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "PEER")));
			RoleEntity adminRole = roleJpaRepository.findByName("ADMIN")
					.orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "ADMIN")));

		};
	}

}
