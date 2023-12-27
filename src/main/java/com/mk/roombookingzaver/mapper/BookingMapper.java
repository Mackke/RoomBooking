package com.mk.roombookingzaver.mapper;

import java.util.List;

import com.mk.roombookingzaver.dto.BookingDto;
import com.mk.roombookingzaver.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto map(Booking booking);
    Booking map(BookingDto bookingDto);

    List<Booking> mapToBookings(List<BookingDto> bookingDtos);
    List<BookingDto> mapToBookingDtos(List<Booking> bookings);
}
