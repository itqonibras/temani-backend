package com.temanmu.temanmu.common.util;

import java.util.Set;

import com.temanmu.temanmu.features.profile.domain.model.Role;

public class RoleUtils {

	public static boolean hasRole(Set<Role> roles, String roleName) {
		return roles.stream().anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
	}

}
