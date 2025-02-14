package com.example.hotelsapi.entity;

import lombok.Getter;

import java.util.Arrays;

public enum Amenity {
    FREE_PARKING("Free parking"),
    FREE_WIFI("Free WiFi"),
    NON_SMOKING_ROOMS("Non-smoking rooms"),
    CONCIERGE("Concierge"),
    ON_SITE_RESTAURANT("On-site restaurant"),
    FITNESS_CENTER("Fitness center"),
    PET_FRIENDLY_ROOMS("Pet-friendly rooms"),
    ROOM_SERVICE("Room service"),
    BUSINESS_CENTER("Business center"),
    MEETING_ROOMS("Meeting rooms");
    @Getter
    private final String displayName;

    Amenity(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Amenity fromDisplayName(String displayName) {
        return Arrays.stream(Amenity.values())
                .filter(amenity -> amenity.getDisplayName().equalsIgnoreCase(displayName))
                .findFirst()
                .orElse(null);
    }

    public static boolean isValidAmenity(String amenityName) {
        for (Amenity amenity : Amenity.values()) {
            if (amenity.displayName.equalsIgnoreCase(amenityName)) {
                return true;
            }
        }
        return false;
    }
}