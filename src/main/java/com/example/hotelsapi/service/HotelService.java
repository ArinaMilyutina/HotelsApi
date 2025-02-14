package com.example.hotelsapi.service;

import com.example.hotelsapi.dto.address.AddressResponse;
import com.example.hotelsapi.dto.arrival_time.ArrivalTimeResponse;
import com.example.hotelsapi.dto.contacts.ContactsResponse;
import com.example.hotelsapi.dto.hotel.*;
import com.example.hotelsapi.entity.*;
import com.example.hotelsapi.exception.HotelAlreadyExistsException;
import com.example.hotelsapi.exception.HotelNotFoundException;
import com.example.hotelsapi.mapper.HotelMapper;
import com.example.hotelsapi.repository.HotelRepository;
import com.example.hotelsapi.specifications.HotelSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private static final String HOTEL_ALREADY_EXISTS = "The hotel with this telephone or email already exists!!!";
    private static final String HOTEL_NOT_FOUND = "Hotel not found!!!";


    @Autowired
    private HotelRepository hotelRepository;


    public HotelShortResponse createHotel(CreateHotelRequest hotelRequest) {
        Hotel hotel = HotelMapper.INSTANCE.createHotelDtoToHotel(hotelRequest);
        Optional<Hotel> hotelByPhone = hotelRepository.findHotelByContacts_Phone(hotel.getContacts().getPhone());
        Optional<Hotel> hotelByEmail = hotelRepository.findHotelByContacts_Email(hotel.getContacts().getEmail());
        if (hotelByPhone.isPresent() || hotelByEmail.isPresent()) {
            throw new HotelAlreadyExistsException(HOTEL_ALREADY_EXISTS);
        }
        hotelRepository.save(hotel);
        return createHotelResponse(hotel);
    }

    public ListHotelResponse findAll() {
        List<Hotel> hotelList = hotelRepository.findAll();
        if (hotelList.isEmpty()) {
            throw new HotelNotFoundException(HOTEL_NOT_FOUND);
        }
        return createListHotelResponse(hotelList);
    }

    public HotelDetailedResponse findById(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            throw new HotelNotFoundException(HOTEL_NOT_FOUND);
        }
        Hotel hotel = optionalHotel.get();
        Address address = hotel.getAddress();
        Contacts contacts = hotel.getContacts();
        ArrivalTime arrivalTime = hotel.getArrivalTime();
        AddressResponse addressResponse = AddressResponse.builder()
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .county(address.getCounty())
                .postCode(address.getPostCode())
                .build();
        ContactsResponse contactsResponse = ContactsResponse.builder()
                .phone(contacts.getPhone())
                .email(contacts.getEmail())
                .build();
        ArrivalTimeResponse arrivalTimeResponse = ArrivalTimeResponse.builder()
                .checkIn(arrivalTime.getCheckIn())
                .checkOut(arrivalTime.getCheckOut())
                .build();
        List<String> amenityStrings = hotel.getAmenities().stream()
                .map(Amenity::toString)
                .collect(Collectors.toList());
        return HotelDetailedResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .brand(hotel.getBrand())
                .address(addressResponse)
                .contacts(contactsResponse)
                .arrivalTime(arrivalTimeResponse)
                .amenities(amenityStrings)
                .build();
    }

    public ListHotelResponse searchByCriteria(HotelSearchCriteria searchCriteria) {
        Specification<Hotel> spec = HotelSpecifications.getHotelsByCriteria(searchCriteria);
        List<Hotel> hotelList = hotelRepository.findAll(spec);
        if (hotelList.isEmpty()) {
            throw new HotelNotFoundException(HOTEL_NOT_FOUND);
        }
        return createListHotelResponse(hotelList);
    }

    private HotelShortResponse createHotelResponse(Hotel hotel) {
        return HotelShortResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(hotel.getAddress().getHouseNumber() + " " + hotel.getAddress().getStreet() + ", " + hotel.getAddress().getCity() + ", " + hotel.getAddress().getPostCode() + ", " + hotel.getAddress().getCounty())
                .phone(hotel.getContacts().getPhone())
                .build();
    }

    private ListHotelResponse createListHotelResponse(List<Hotel> hotelList) {
        List<HotelShortResponse> hotelResponse = hotelList.stream()
                .map(this::createHotelResponse)
                .collect(Collectors.toList());
        return ListHotelResponse.builder().hotelResponseList(hotelResponse).build();
    }
}
