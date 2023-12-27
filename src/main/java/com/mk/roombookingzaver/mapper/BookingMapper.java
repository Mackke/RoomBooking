package com.mk.roombookingzaver.mapper;

import com.mk.roombookingzaver.api.dto.BookingDto;
import com.mk.roombookingzaver.data.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDto map(Booking booking);

    Booking map(BookingDto bookingDto);
}
