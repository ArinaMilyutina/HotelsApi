package com.example.hotelsapi.service;

import com.example.hotelsapi.entity.*;

import com.example.hotelsapi.exception.HotelNotFoundException;

import com.example.hotelsapi.exception.InvalidAmenityException;
import com.example.hotelsapi.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AmenityServiceTest {
    private static final String HOTEL_NOT_FOUND = "Hotel wasn't found!!!";
    private static final String INVALID_AMENITIES = "Invalid amenity value: ";
    @InjectMocks
    private AmenityService amenityService;

    @Mock
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addAmenitiesToHotel_test() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        List<String> amenityNames = Arrays.asList("Free WiFi", "Free parking");
        amenityService.addAmenitiesToHotel(hotelId, amenityNames);
        assertEquals(2, hotel.getAmenities().size());
        verify(hotelRepository).save(hotel);
    }

    @Test
    void addAmenitiesToHotel_hotelNotFound_test() {
        Long hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> {
            amenityService.addAmenitiesToHotel(hotelId, Arrays.asList("Free WiFi"));
        });
        assertEquals(HOTEL_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addAmenitiesToHotel_invalidAmenity_test() {
        Long hotelId = 1L;
        String invalidAmenity = "Valid Amenity";
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        List<String> amenityNames = Arrays.asList(invalidAmenity);
        Exception exception = assertThrows(InvalidAmenityException.class, () -> {
            amenityService.addAmenitiesToHotel(hotelId, amenityNames);
        });
        assertEquals(INVALID_AMENITIES + invalidAmenity, exception.getMessage());
        verify(hotelRepository, never()).save(any(Hotel.class));
    }
}
