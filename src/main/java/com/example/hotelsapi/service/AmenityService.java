package com.example.hotelsapi.service;

import com.example.hotelsapi.entity.Amenity;
import com.example.hotelsapi.entity.Hotel;
import com.example.hotelsapi.exception.HotelNotFoundException;
import com.example.hotelsapi.exception.InvalidAmenityException;
import com.example.hotelsapi.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AmenityService {
    private static final String HOTEL_NOT_FOUND = "Hotel not found!!!";
    private static final String INVALID_AMENITIES = "Invalid amenity value: ";

    @Autowired
    private HotelRepository hotelRepository;

    @Transactional
    public void addAmenitiesToHotel(Long hotelId, List<String> amenityNames) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            Set<Amenity> amenities = amenityNames.stream()
                    .map(amenityName -> {
                        if (Amenity.isValidAmenity(amenityName)) {
                            return Amenity.fromDisplayName(amenityName);
                        } else {
                            throw new InvalidAmenityException(INVALID_AMENITIES + amenityName);
                        }
                    })
                    .collect(Collectors.toSet());

            hotel.setAmenities(amenities);
            hotelRepository.save(hotel);
        } else {
            throw new HotelNotFoundException(HOTEL_NOT_FOUND);
        }
    }

}

