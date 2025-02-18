package com.example.hotelsapi.dto.hotel;


import com.example.hotelsapi.dto.address.AddressRequest;
import com.example.hotelsapi.dto.arrival_time.ArrivalTimeRequest;
import com.example.hotelsapi.dto.contacts.ContactsRequest;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelCreateRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 70, message = "Name must be between 2 and 70 characters")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 2, max = 350, message = "Description must be between 2 and 350 characters")
    private String description;
    @NotBlank(message = "Brand cannot be blank")
    @Size(min = 2, max = 70, message = "Brand must be between 2 and 70 characters")
    private String brand;
    @Valid
    private AddressRequest address;
    @Valid
    private ContactsRequest contacts;
    @Valid
    private ArrivalTimeRequest arrivalTime;
}