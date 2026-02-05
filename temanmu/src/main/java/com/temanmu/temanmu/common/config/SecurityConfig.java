package com.temanmu.temanmu.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.temanmu.temanmu.common.security.CustomAccessDeniedHandler;
import com.temanmu.temanmu.common.security.CustomAuthEntryPoint;
import com.temanmu.temanmu.common.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	private final CustomAuthEntryPoint authEntryPoint;

	private final CustomAccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/get-token")
						.permitAll()
						.requestMatchers("/auth/**")
						.permitAll()
						.requestMatchers("/debug/**")
						.authenticated()
						.requestMatchers("/chat/info/**", "/chat/**")
						.permitAll()
						.requestMatchers("/payments/webhook/**")
						.permitAll()
						.requestMatchers("/home/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER")
						.requestMatchers("/activity/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER", "ROLE_PEER")
					.requestMatchers("/journals/**")
					.hasAnyAuthority("ROLE_CLIENT")
					.requestMatchers("/mood-logs/**")
					.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER")
						.requestMatchers("/todo-lists/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER")
						.requestMatchers("/todo-items/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER")
						.requestMatchers("/counseling-schedules/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER", "ROLE_PEER")
						.requestMatchers("/relationships/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER")
						.requestMatchers("/interaction-logs/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER")
						.requestMatchers("/payments/**")
						.hasAnyAuthority("ROLE_CLIENT", "ROLE_CAREGIVER", "ROLE_PEER")
						.requestMatchers("/profiles/**")
						.authenticated()
						.anyRequest()
						.authenticated())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(
						ex -> ex.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(
			"https://temanmu-bersama.web.app",
			"https://temanmu-bersama.firebaseapp.com",
			"http://localhost:3000",
			"http://localhost:8080",
			"http://localhost:5000"
		));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("Authorization"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
