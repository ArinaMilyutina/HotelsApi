package com.example.hotelsapi.repository;


import com.example.hotelsapi.dto.hotel.HotelCountRequest;
import com.example.hotelsapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    Optional<Hotel> findHotelByContacts_Phone(String phone);

    Optional<Hotel> findHotelByContacts_Email(String email);
    @Query("SELECT new com.example.hotelsapi.dto.hotel.HotelCountRequest(h.brand, COUNT(h)) FROM Hotel h GROUP BY h.brand")
    List<HotelCountRequest> countHotelByBrand();
    @Query("SELECT new com.example.hotelsapi.dto.hotel.HotelCountRequest(a.city, COUNT(h)) FROM Hotel h JOIN h.address a GROUP BY a.city")
    List<HotelCountRequest> countHotelByAddress_City();
    @Query("SELECT new com.example.hotelsapi.dto.hotel.HotelCountRequest(a.county, COUNT(h)) FROM Hotel h JOIN h.address a GROUP BY a.county")
    List<HotelCountRequest> countHotelByAddress_County();
    @Query("SELECT new com.example.hotelsapi.dto.hotel.HotelCountRequest(CAST(a as string), COUNT(h)) FROM Hotel h JOIN h.amenities a GROUP BY a")
    List<HotelCountRequest> countHotelByAmenities();

}
