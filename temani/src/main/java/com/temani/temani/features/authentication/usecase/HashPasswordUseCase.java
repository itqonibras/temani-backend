package com.temani.temani.features.authentication.usecase;

public interface HashPasswordUseCase {
    String hash(String password);
    boolean matches(String rawPassword, String hashedPassword);
}
