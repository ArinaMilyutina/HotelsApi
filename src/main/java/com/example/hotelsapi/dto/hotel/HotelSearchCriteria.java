package com.example.hotelsapi.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelSearchCriteria {
    private String name;
    private String brand;
    private String city;
    private String county;
    private List<String> amenities;
}
