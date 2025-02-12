package com.example.hotelsapi.entity;



import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phone;

}
