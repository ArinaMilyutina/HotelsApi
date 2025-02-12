package com.example.hotelsapi.service;

import com.example.hotelsapi.dto.CreateHotelRequest;
import com.example.hotelsapi.dto.HotelResponse;
import com.example.hotelsapi.dto.ListHotelResponse;
import com.example.hotelsapi.entity.Hotel;
import com.example.hotelsapi.mapper.HotelMapper;
import com.example.hotelsapi.repository.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelsService {
    @Autowired
    private HotelsRepository hotelsRepository;

    public HotelResponse createHotel(CreateHotelRequest hotelRequest) {
        Hotel hotel = HotelMapper.INSTANCE.createHotelDtoToHotel(hotelRequest);
        hotelsRepository.save(hotel);
        return createdBookResponse(hotel);
    }

    public ListHotelResponse findAll() {
        List<Hotel> hotelList = hotelsRepository.findAll();
        if (hotelList.isEmpty()) {

        }
        return createListHotelResponse(hotelList);
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

    private ListHotelResponse createListHotelResponse(List<Hotel> hotelList) {
        List<HotelResponse> hotelResponse = hotelList.stream()
                .map(this::createdBookResponse)
                .collect(Collectors.toList());
        return ListHotelResponse.builder().hotelResponseList(hotelResponse).build();
    }
}
