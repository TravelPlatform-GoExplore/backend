package org.anaservinovska.travelplatform.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserSubscriptionType {
    UNO("Uno"),
    DOS("Dos"),
    DIGITAL_NOMAD("Digital Nomad");

    private final String description;

    UserSubscriptionType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
