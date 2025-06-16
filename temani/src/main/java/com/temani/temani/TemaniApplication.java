package com.temani.temani;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.temani.temani.features.authentication.usecase.HashPasswordUseCase;
import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
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
            HashPasswordUseCase hashPassword) {
        return args -> {
            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "USER")));
            Role penyandangRole = roleRepository.findByName("PENYANDANG")
                    .orElseGet(() -> roleRepository.save(new Role(null, "PENYANDANG")));

            if (userRepository.findByUsername("dummy_penyandang").isEmpty()) {
                User user = new User(
                        null,
                        "Dummy Penyandang",
                        "dummy_penyandang",
                        "penyandang@email.com",
                        "081231214123",
                        "penyandang",
                        null,
                        null,
                        Set.of(userRole, penyandangRole));
                userRepository.save(user);
            }
        };
    }
}
