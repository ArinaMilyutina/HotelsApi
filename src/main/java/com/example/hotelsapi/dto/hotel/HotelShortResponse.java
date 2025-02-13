package com.example.hotelsapi.dto.hotel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelShortResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
