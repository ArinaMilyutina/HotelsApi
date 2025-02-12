package com.example.hotelsapi.controller;

import com.example.hotelsapi.dto.CreateHotelRequest;
import com.example.hotelsapi.dto.HotelResponse;
import com.example.hotelsapi.service.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    @Autowired
    private HotelsService hotelsService;

    @PostMapping("/hotels")
    public ResponseEntity<?> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelsService.createHotel(createHotelRequest));
    }
}
