package com.example.hotelsapi.service;

import com.example.hotelsapi.entity.Amenity;
import com.example.hotelsapi.entity.Hotel;
import com.example.hotelsapi.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AmenityService {

    @Autowired
    private HotelRepository hotelRepository;

    @Transactional
    public void addAmenitiesToHotel(Long hotelId, List<String> amenityNames) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + hotelId));

        Set<Amenity> amenities = amenityNames.stream()
                .map(Amenity::fromDisplayName)
                .collect(Collectors.toSet());

        hotel.setAmenities(amenities);
        hotelRepository.save(hotel);
    }
}

