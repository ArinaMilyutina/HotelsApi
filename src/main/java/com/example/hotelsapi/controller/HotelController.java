package com.example.hotelsapi.controller;

import com.example.hotelsapi.dto.hotel.*;


import com.example.hotelsapi.service.AmenityService;
import com.example.hotelsapi.service.HotelService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private AmenityService amenityService;

    @PostMapping("/hotels")
    public ResponseEntity<HotelShortResponse> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(createHotelRequest));
    }

    @GetMapping("/hotels")
    public ResponseEntity<ListHotelResponse> findAll() {
        return ResponseEntity.ok(hotelService.findAll());
    }


    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailedResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.findById(id));
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenitiesToHotel(@PathVariable Long id, @RequestBody List<String> amenityNames) {
        amenityService.addAmenitiesToHotel(id, amenityNames);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public ResponseEntity<ListHotelResponse> searchByCriteria(HotelSearchCriteria searchCriteria) {
        return ResponseEntity.ok(hotelService.searchByCriteria(searchCriteria));
    }
}
