package com.example.hotelsapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalTimeRequest {
    @NotBlank(message = "Check in cannot be blank")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
    private String checkIn;
    private String checkOut;
}
