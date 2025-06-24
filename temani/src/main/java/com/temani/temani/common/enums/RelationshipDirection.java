package com.temani.temani.common.enums;

import com.temani.temani.common.constants.RelationshipMessages;

public enum RelationshipDirection {

	SENT, RECEIVED;

	public static RelationshipDirection fromString(String value) {
		for (RelationshipDirection dir : RelationshipDirection.values()) {
			if (dir.name().equalsIgnoreCase(value)) {
				return dir;
			}
		}
		throw new IllegalArgumentException(RelationshipMessages.INVALID_PENDING_DIRECTION);
	}

}
