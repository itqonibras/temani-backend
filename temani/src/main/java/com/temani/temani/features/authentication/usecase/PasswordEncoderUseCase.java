package com.temani.temani.features.authentication.usecase;

public interface PasswordEncoderUseCase {

	String hash(String password);

	boolean matches(String rawPassword, String hashedPassword);

}
