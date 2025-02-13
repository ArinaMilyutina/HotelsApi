package com.example.hotelsapi.dto.arrival_time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArrivalTimeResponse {
    private String checkIn;
    private String checkOut;
}
