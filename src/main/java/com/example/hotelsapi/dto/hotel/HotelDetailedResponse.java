package com.example.hotelsapi.dto.hotel;

import com.example.hotelsapi.dto.address.AddressResponse;
import com.example.hotelsapi.dto.arrival_time.ArrivalTimeResponse;
import com.example.hotelsapi.dto.contacts.ContactsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDetailedResponse {
    private Long id;
    private String name;
    private String brand;
    private AddressResponse address;
    private ContactsResponse contacts;
    private ArrivalTimeResponse arrivalTime;
    private List<String> amenities;
}
