package com.temani.temani.common.util;

import java.util.Set;

import com.temani.temani.features.profile.domain.model.Role;

public class RoleUtils {

	public static boolean hasRole(Set<Role> roles, String roleName) {
		return roles.stream().anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
	}

}
