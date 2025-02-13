package com.example.hotelsapi.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private int houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;
}
