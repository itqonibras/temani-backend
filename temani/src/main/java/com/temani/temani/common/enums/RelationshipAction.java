package com.temani.temani.common.enums;

import com.temani.temani.common.constants.RelationshipMessages;

public enum RelationshipAction {

    ACCEPT, CANCEL, REJECT;

    public static RelationshipAction fromString(String value) {
        for (RelationshipAction status : RelationshipAction.values()) {
			if (status.name().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException(RelationshipMessages.INVALID_STATUS_REQUEST);
    }
    
}
