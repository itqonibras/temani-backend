package com.temani.temani;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.temani.temani.features.authentication.presentation.dto.request.RegisterRequest;
import com.temani.temani.features.authentication.usecase.RegisterUseCase;
import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.repository.RoleRepository;
import com.temani.temani.features.profile.domain.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class TemaniApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemaniApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository,
            RegisterUseCase registerUseCase) {
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

            if (!userRepository.existsByUsername("dummy_penyandang")) {
                registerUseCase.execute(new RegisterRequest(
                        "Dummy Penyandang",
                        "dummy_penyandang",
                        "2002-02-02",
                        "penyandang@email.com",
                        "081280080001",
                        "penyandang",
                        Set.of("USER", "CLIENT")
                ));
            }
            if (!userRepository.existsByUsername("dummy_pendamping")) {
                registerUseCase.execute(new RegisterRequest(
                        "Dummy Pendamping",
                        "dummy_pendamping",
                        "2002-02-02",
                        "pendamping@email.com",
                        "081280080002",
                        "pendamping",
                        Set.of("USER", "CAREGIVER")
                ));
            }
            if (!userRepository.existsByUsername("dummy_pendamping2")) {
                registerUseCase.execute(new RegisterRequest(
                        "Dummy Pendamping2",
                        "dummy_pendamping2",
                        "2002-02-02",
                        "pendamping2@email.com",
                        "081280080003",
                        "pendamping",
                        Set.of("USER", "CAREGIVER")
                ));
            }
            if (!userRepository.existsByUsername("dummy_peer")) {
                registerUseCase.execute(new RegisterRequest(
                        "Dummy Peer",
                        "dummy_peer",
                        "2002-02-02",
                        "peer@email.com",
                        "081280080004",
                        "peer",
                        Set.of("USER", "PEER")
                ));
            }
        };
    }
}
