package com.temani.temani.common.enums;

import com.temani.temani.common.constants.RelationshipMessages;

public enum RelationshipStatus {

	ACCEPTED, PENDING;

	public static RelationshipStatus fromString(String value) {
		for (RelationshipStatus status : RelationshipStatus.values()) {
			if (status.name().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException(RelationshipMessages.INVALID_STATUS_PARAMETER);
	}

}
