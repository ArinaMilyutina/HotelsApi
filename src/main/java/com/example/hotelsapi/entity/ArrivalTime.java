package com.example.hotelsapi.entity;



import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "arrival_time")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String checkIn;
    private String checkOut;
}
