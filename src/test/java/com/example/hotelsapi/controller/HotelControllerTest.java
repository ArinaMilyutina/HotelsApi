package com.example.hotelsapi.controller;

import com.example.hotelsapi.dto.address.AddressRequest;
import com.example.hotelsapi.dto.arrival_time.ArrivalTimeRequest;
import com.example.hotelsapi.dto.contacts.ContactsRequest;
import com.example.hotelsapi.dto.hotel.*;
import com.example.hotelsapi.service.AmenityService;
import com.example.hotelsapi.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class HotelControllerTest {

    @InjectMocks
    private HotelController hotelController;


    @Mock
    private HotelService hotelService;
    @Mock
    private AmenityService amenityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createHotel_test() {
        HotelCreateRequest createHotelRequest = new HotelCreateRequest();
        createHotelRequest.setName("DoubleTree by Hilton Minsk");
        createHotelRequest.setDescription("The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ..");
        createHotelRequest.setBrand("Hilton");
        AddressRequest addressCreateRequest = new AddressRequest();
        addressCreateRequest.setHouseNumber(9);
        addressCreateRequest.setStreet("Pobediteley Avenue");
        addressCreateRequest.setCity("Minsk");
        addressCreateRequest.setCounty("Belarus");
        addressCreateRequest.setPostCode("220004");
        createHotelRequest.setAddress(addressCreateRequest);
        ContactsRequest contactsCreateRequest = new ContactsRequest();
        contactsCreateRequest.setPhone("+39977309-80-00");
        contactsCreateRequest.setEmail("dnetrrrmpnsk.info@hilton.com");
        createHotelRequest.setContacts(contactsCreateRequest);
        ArrivalTimeRequest arrivalTimeCreateRequest = new ArrivalTimeRequest();
        arrivalTimeCreateRequest.setCheckIn("14:00");
        arrivalTimeCreateRequest.setCheckOut("12:00");
        createHotelRequest.setArrivalTime(arrivalTimeCreateRequest);
        HotelShortResponse hotelShortResponse = new HotelShortResponse();
        hotelShortResponse.setId(1L);
        hotelShortResponse.setName("DoubleTree by Hilton Minsk");
        createHotelRequest.setDescription("The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ..");
        hotelShortResponse.setAddress("9 Pobediteley Avenue, Minsk, 220004, Belarus");
        hotelShortResponse.setPhone("+39977309-80-00");
        when(hotelService.createHotel(any(HotelCreateRequest.class))).thenReturn(hotelShortResponse);
        ResponseEntity<HotelShortResponse> response = hotelController.createHotel(createHotelRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(hotelShortResponse, response.getBody());
    }

    @Test
    void findAll_test() {
        ListHotelResponse listHotelResponse = new ListHotelResponse();
        when(hotelService.findAll()).thenReturn(listHotelResponse);
        ResponseEntity<ListHotelResponse> response = hotelController.findAll();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(listHotelResponse, response.getBody());
    }

    @Test
    void findById_test() {
        HotelDetailedResponse hotelDetailedResponse = new HotelDetailedResponse();
        when(hotelService.findById(anyLong())).thenReturn(hotelDetailedResponse);
        ResponseEntity<HotelDetailedResponse> response = hotelController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotelDetailedResponse, response.getBody());
    }

    @Test
    void addAmenitiesToHotel_test() {
        Long hotelId = 1L;
        List<String> amenityNames = Arrays.asList("Free WiFi", "Free Parking");
        doNothing().when(amenityService).addAmenitiesToHotel(hotelId, amenityNames);
        ResponseEntity<Void> response = hotelController.addAmenitiesToHotel(hotelId, amenityNames);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(amenityService).addAmenitiesToHotel(hotelId, amenityNames);
    }

    @Test
    void searchHotel_test() {
        HotelSearchCriteria searchCriteria = new HotelSearchCriteria();
        searchCriteria.setCity("Minsk");
        ListHotelResponse expectedResponse = new ListHotelResponse();
        when(hotelService.searchByCriteria(searchCriteria)).thenReturn(expectedResponse);
        ResponseEntity<ListHotelResponse> response = hotelController.searchByCriteria(searchCriteria);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getHistogram_test() {
        String param = "city";
        Map<String, Long> expectedHistogram = new HashMap<>();
        expectedHistogram.put("Minsk", 10L);
        expectedHistogram.put("Moscow", 5L);
        when(hotelService.getHistogram(param)).thenReturn(expectedHistogram);
        ResponseEntity<Map<String, Long>> response = hotelController.getHistogram(param);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHistogram, response.getBody());
    }


}