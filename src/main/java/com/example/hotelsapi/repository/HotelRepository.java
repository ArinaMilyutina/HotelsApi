package com.example.hotelsapi.repository;


import com.example.hotelsapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    Optional<Hotel> findHotelByContacts_Phone(String phone);

    Optional<Hotel> findHotelByContacts_Email(String email);

}
