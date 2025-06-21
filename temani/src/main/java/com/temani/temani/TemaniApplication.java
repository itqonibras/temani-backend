package com.temani.temani;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.temani.temani.features.authentication.usecase.HashPasswordUseCase;
import com.temani.temani.features.profile.infrastructure.persistence.CaregiverProfileEntity;
import com.temani.temani.features.profile.infrastructure.persistence.ClientProfileEntity;
import com.temani.temani.features.profile.infrastructure.persistence.PeerProfileEntity;
import com.temani.temani.features.profile.infrastructure.persistence.RoleEntity;
import com.temani.temani.features.profile.infrastructure.persistence.RoleJpaRepository;
import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class TemaniApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemaniApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner initData(RoleJpaRepository roleJpaRepository, UserJpaRepository userJpaRepository,
            HashPasswordUseCase hashPasswordUseCase) {
        return args -> {
            RoleEntity userRole = roleJpaRepository.findByName("USER")
                    .orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "USER")));
            RoleEntity clientRole = roleJpaRepository.findByName("CLIENT")
                    .orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "CLIENT")));
            RoleEntity caregiverRole = roleJpaRepository.findByName("CAREGIVER")
                    .orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "CAREGIVER")));
            RoleEntity peerRole = roleJpaRepository.findByName("PEER")
                    .orElseGet(() -> roleJpaRepository.save(new RoleEntity(null, "PEER")));

            if (!userJpaRepository.existsByUsername("dummy_penyandang")) {
                UserEntity user = new UserEntity();
                user.setId(UUID.fromString("fa657cba-5a10-4e1c-b85c-35cb43f66a10"));
                user.setName("Dummy Penyandang");
                user.setUsername("dummy_penyandang");
                user.setEmail("penyandang@email.com");
                user.setPhone("081280080001");
                user.setDateOfBirth(LocalDate.parse("2002-02-02"));
                user.setPassword(hashPasswordUseCase.hash("penyandang"));
                user.setRoles(Set.of(userRole, clientRole));
                user.setVerified(true);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());

                ClientProfileEntity profile = new ClientProfileEntity();
                profile.setUser(user);
                user.setClientProfileEntity(profile);

                userJpaRepository.save(user);
            }

            if (!userJpaRepository.existsByUsername("dummy_pendamping")) {
                UserEntity user = new UserEntity();
                user.setId(UUID.fromString("eb20ad35-00c6-46f4-bbc4-60e9948df6e0"));
                user.setName("Dummy Pendamping");
                user.setUsername("dummy_pendamping");
                user.setEmail("pendamping@email.com");
                user.setPhone("081280080002");
                user.setDateOfBirth(LocalDate.parse("2002-02-02"));
                user.setPassword(hashPasswordUseCase.hash("pendamping"));
                user.setRoles(Set.of(userRole, caregiverRole));
                user.setVerified(true);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());

                CaregiverProfileEntity profile = new CaregiverProfileEntity();
                profile.setUser(user);
                user.setCaregiverProfileEntity(profile);

                userJpaRepository.save(user);
            }

            if (!userJpaRepository.existsByUsername("dummy_pendamping2")) {
                UserEntity user = new UserEntity();
                user.setId(UUID.fromString("a5f19712-3f76-4c5d-93ff-6fd60df03b7a"));
                user.setName("Dummy Pendamping2");
                user.setUsername("dummy_pendamping2");
                user.setEmail("pendamping2@email.com");
                user.setPhone("081280080003");
                user.setDateOfBirth(LocalDate.parse("2002-02-02"));
                user.setPassword(hashPasswordUseCase.hash("pendamping"));
                user.setRoles(Set.of(userRole, caregiverRole));
                user.setVerified(true);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());

                CaregiverProfileEntity profile = new CaregiverProfileEntity();
                profile.setUser(user);
                user.setCaregiverProfileEntity(profile);

                userJpaRepository.save(user);
            }

            if (!userJpaRepository.existsByUsername("dummy_peer_konselor")) {
                UserEntity user = new UserEntity();
                user.setId(UUID.fromString("dc7dcb6d-fcf6-44ee-9b26-1071e90b65a8"));
                user.setName("Dummy Peer Konselor");
                user.setUsername("dummy_peer_konselor");
                user.setEmail("peer.konselor@email.com");
                user.setPhone("081280080004");
                user.setDateOfBirth(LocalDate.parse("2002-02-02"));
                user.setPassword(hashPasswordUseCase.hash("peer.konselor"));
                user.setRoles(Set.of(userRole, peerRole));
                user.setVerified(true);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());

                PeerProfileEntity profile = new PeerProfileEntity();
                profile.setUser(user);
                user.setPeerProfileEntity(profile);

                userJpaRepository.save(user);
            }
        };
    }

}
