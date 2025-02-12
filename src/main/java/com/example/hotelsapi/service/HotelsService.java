package com.example.hotelsapi.service;

import com.example.hotelsapi.dto.CreateHotelRequest;
import com.example.hotelsapi.dto.HotelResponse;
import com.example.hotelsapi.entity.Hotel;
import com.example.hotelsapi.mapper.HotelMapper;
import com.example.hotelsapi.repository.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelsService {
    @Autowired
    private HotelsRepository hotelsRepository;

    public HotelResponse createHotel(CreateHotelRequest hotelRequest) {
        Hotel hotel = HotelMapper.INSTANCE.createHotelDtoToHotel(hotelRequest);
        hotelsRepository.save(hotel);
        return createdBookResponse(hotel);
    }

    private HotelResponse createdBookResponse(Hotel hotel) {
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.setId(hotel.getId());
        hotelResponse.setName(hotel.getName());
        hotelResponse.setDescription(hotel.getDescription());
        hotelResponse.setAddress(hotel.getAddress().getHouseNumber() + " " + hotel.getAddress().getStreet() + ", " + hotel.getAddress().getCity() + ", " + hotel.getAddress().getPostCode() + ", " + hotel.getAddress().getCounty());
        hotelResponse.setPhone(hotel.getContacts().getPhone());
        return hotelResponse;
    }
}
