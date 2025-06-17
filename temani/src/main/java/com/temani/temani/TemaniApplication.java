package com.temani.temani;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.repository.RoleRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class TemaniApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemaniApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner initData(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(new Role(null, "USER"));
            }
            if (roleRepository.findByName("CLIENT").isEmpty()) {
                roleRepository.save(new Role(null, "CLIENT"));
            }
            if (roleRepository.findByName("CAREGIVER").isEmpty()) {
                roleRepository.save(new Role(null, "CAREGIVER"));
            }
            if (roleRepository.findByName("PEER").isEmpty()) {
                roleRepository.save(new Role(null, "PEER"));
            }
        };
    }
}
