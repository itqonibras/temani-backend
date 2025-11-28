package com.temanmu.temanmu.common.enums;

import com.temanmu.temanmu.common.constants.RelationshipMessages;

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
