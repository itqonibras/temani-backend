package com.temanmu.temanmu.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.constants.CommonMessages;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(CommonMessages.USER_NOT_FOUND));

		return new CustomUserDetails(user);
	}

}
