package com.example.hotelsapi.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
