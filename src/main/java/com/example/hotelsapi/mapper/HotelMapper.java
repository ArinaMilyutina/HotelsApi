package com.example.hotelsapi.mapper;

import com.example.hotelsapi.dto.CreateHotelRequest;
import com.example.hotelsapi.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HotelMapper {
    HotelMapper INSTANCE= Mappers.getMapper(HotelMapper.class);
    Hotel createHotelDtoToHotel(CreateHotelRequest regUserDto);
}
