package com.mk.roombookingzaver.mapper;

import com.mk.roombookingzaver.dto.BookingDto;
import com.mk.roombookingzaver.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto map(Booking booking);
    Booking map(BookingDto bookingDto);
}
