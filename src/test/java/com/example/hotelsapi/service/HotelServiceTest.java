package com.example.hotelsapi.service;

import com.example.hotelsapi.dto.address.AddressRequest;
import com.example.hotelsapi.dto.arrival_time.ArrivalTimeRequest;
import com.example.hotelsapi.dto.contacts.ContactsRequest;
import com.example.hotelsapi.dto.hotel.*;
import com.example.hotelsapi.entity.*;
import com.example.hotelsapi.exception.HotelAlreadyExistsException;
import com.example.hotelsapi.exception.HotelNotFoundException;
import com.example.hotelsapi.mapper.HotelMapper;
import com.example.hotelsapi.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;


import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HotelServiceTest {
    private static final String HOTEL_ALREADY_EXISTS = "The hotel with this telephone or email already exists!!!";
    private static final String HOTEL_NOT_FOUND = "Hotel wasn't  found!!!";
    private static final String INVALID_PARAMETER = "Invalid parameter: ";
    private static final String INFORMATION_NOT_FOUND = "Information about your parameters wasn't found!!!";

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelMapper hotelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createHotel_test() {
        HotelCreateRequest createHotelRequest = hotelCreateRequest();
        Hotel hotel = new Hotel();
        hotel.setName("DoubleTree by Hilton Minsk");
        when(hotelRepository.findHotelByContacts_Phone(any())).thenReturn(Optional.empty());
        when(hotelRepository.findHotelByContacts_Email(any())).thenReturn(Optional.empty());
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(hotelMapper.createHotelDtoToHotel(createHotelRequest)).thenReturn(hotel);
        HotelShortResponse response = hotelService.createHotel(createHotelRequest);
        assertEquals(hotel.getName(), response.getName());
    }

    @Test
    void createHotel_ThrowsHotelAlreadyExistsException_test() {
        HotelCreateRequest createHotelRequest = hotelCreateRequest();
        Hotel hotel = new Hotel();
        Contacts contacts = new Contacts();
        contacts.setPhone("+39977309-80-00");
        hotel.setContacts(contacts);
        when(hotelRepository.findHotelByContacts_Phone(any())).thenReturn(Optional.of(hotel));
        when(hotelRepository.findHotelByContacts_Email(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelAlreadyExistsException.class, () -> hotelService.createHotel(createHotelRequest));
        assertEquals(HOTEL_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void findAll_HotelNotFoundException_test() {
        when(hotelRepository.findAll()).thenReturn(new ArrayList<>());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.findAll();
        });
        assertEquals(HOTEL_NOT_FOUND, exception.getMessage());
    }

    @Test
    void findAll_test() {
        Hotel hotel = createHotel();
        List<Hotel> hotels = List.of(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);
        ListHotelResponse response = hotelService.findAll();
        assertNotNull(response);
        assertEquals(1, response.getHotelResponseList().size());
        assertEquals("Test Hotel", response.getHotelResponseList().get(0).getName());
    }

    @Test
    void findById_HotelNotFoundException_test() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.findById(1L);
        });
        assertEquals(HOTEL_NOT_FOUND, exception.getMessage());
    }

    @Test
    void findById_test() {
        Hotel hotel = createHotel();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        HotelDetailedResponse response = hotelService.findById(1L);
        assertEquals("Test Hotel", response.getName());
        assertEquals("Test Brand", response.getBrand());
    }

    @Test
    void searchByCriteria_HotelsNotFoundException_test() {
        HotelSearchCriteria criteria = new HotelSearchCriteria();
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.searchByCriteria(criteria);
        });

        assertEquals(HOTEL_NOT_FOUND, exception.getMessage());
    }

    @Test
    void searchByCriteria_test() {
        HotelSearchCriteria criteria = new HotelSearchCriteria();
        Hotel hotel = createHotel();
        List<Hotel> hotels = List.of(hotel);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(hotels);
        ListHotelResponse response = hotelService.searchByCriteria(criteria);
        assertNotNull(response);
    }


    @Test
    void getHistogram_IllegalArgumentException_test() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.getHistogram("invalidParam");
        });

        assertEquals(INVALID_PARAMETER + "invalidParam", exception.getMessage());
    }

    @Test
    void getHistogram_test() {
        List<HotelCountRequest> countRequests = List.of(new HotelCountRequest("Test Brand", 10L));
        when(hotelRepository.countHotelByBrand()).thenReturn(countRequests);

        Map<String, Long> histogram = hotelService.getHistogram("brand");

        assertEquals(1, histogram.size());
        assertTrue(histogram.containsKey("Test Brand"));
        assertEquals(10L, histogram.get("Test Brand"));
    }

    @Test
    void getHistogram_HotelNotFoundException_test() {
        when(hotelRepository.countHotelByBrand()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.getHistogram("brand");
        });

        assertEquals(INFORMATION_NOT_FOUND, exception.getMessage());
    }

    private Hotel createHotel() {
        Address address = new Address();
        address.setHouseNumber(9);
        address.setStreet("Main St");
        address.setCity("Anytown");
        address.setCounty("Anycounty");
        address.setPostCode("12345");
        Contacts contacts = new Contacts();
        contacts.setPhone("123-456-7890");
        contacts.setEmail("test@example.com");
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("14:00");
        arrivalTime.setCheckOut("12:00");
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setBrand("Test Brand");
        hotel.setAddress(address);
        hotel.setContacts(contacts);
        hotel.setArrivalTime(arrivalTime);
        hotel.setAmenities(Set.of(Amenity.CONCIERGE));
        return hotel;
    }

    private HotelCreateRequest hotelCreateRequest() {
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
        return createHotelRequest;
    }


}
