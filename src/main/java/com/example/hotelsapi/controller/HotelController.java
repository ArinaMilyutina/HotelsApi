package com.example.hotelsapi.controller;

import com.example.hotelsapi.dto.CreateHotelRequest;

import com.example.hotelsapi.dto.ListHotelResponse;
import com.example.hotelsapi.service.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    @Autowired
    private HotelsService hotelsService;

    @PostMapping("/hotels")
    public ResponseEntity<?> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelsService.createHotel(createHotelRequest));
    }

    @GetMapping("/hotels")
    public ResponseEntity<ListHotelResponse> findAll() {
        return ResponseEntity.ok(hotelsService.findAll());
    }
}
