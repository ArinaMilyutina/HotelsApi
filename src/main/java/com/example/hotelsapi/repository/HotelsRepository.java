package com.example.hotelsapi.repository;


import com.example.hotelsapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepository extends JpaRepository<Hotel, Long> {
}
