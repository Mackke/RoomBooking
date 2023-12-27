package com.mk.roombookingzaver.service;

import java.io.IOError;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.exception.RoomNotFoundException;
import com.mk.roombookingzaver.exception.RoomOccupiedException;
import com.mk.roombookingzaver.api.dto.BookingDto;
import com.mk.roombookingzaver.api.dto.RoomDto;
import com.mk.roombookingzaver.data.entity.Booking;
import com.mk.roombookingzaver.data.entity.Room;
import com.mk.roombookingzaver.data.repository.BookingRepository;
import com.mk.roombookingzaver.data.repository.RoomRepository;
import com.mk.roombookingzaver.api.dto.BookingRequest;
import com.mk.roombookingzaver.api.dto.BookingResponse;
import com.mk.roombookingzaver.api.dto.BookingListResponse;
import com.mk.roombookingzaver.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final BookingMapper mapper;

    public BookingListResponse getCurrentBookings(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.currentBookings(startDate, endDate);

        List<BookingDto> listOfCurrentBookings = bookings
                .stream()
                .map(mapper::map)
                .toList();

        return new BookingListResponse(listOfCurrentBookings);
    }

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Room roomToBeBooked = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Booking not found"));

        List<Booking> bookingsForRoom = bookingRepository.getRoomsBookings(roomToBeBooked.getId());

        validateRoomAvailability(bookingsForRoom, bookingRequest.getStartDate(), bookingRequest.getEndDate());

        Booking booking = bookingRepository.save(new Booking(roomToBeBooked, bookingRequest.getStartDate(), bookingRequest.getEndDate()));


        return new BookingResponse(mapper.map(booking));
    }

    public BookingResponse cancelBookingById(UUID bookingId) {
        Booking persistedBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        persistedBooking.setArchived(LocalDate.now());
        bookingRepository.save(persistedBooking);

        return new BookingResponse(mapToDto(persistedBooking));
    }

    public void validateRoomAvailability(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        //TODO go through every booking and validate the room is in vacancy
        List<Booking> doubleBookings = bookings.stream()
                .filter(booking ->
                        (startDate.isEqual(booking.getStartDate()) && endDate.isEqual(booking.getEndDate())) ||
                        (startDate.isAfter(booking.getStartDate()) && startDate.isBefore(booking.getEndDate())) ||
                                (endDate.isAfter(booking.getStartDate()) && endDate.isBefore(booking.getEndDate()))
                )
                .toList();

        if (!doubleBookings.isEmpty()) {
            throw new RoomOccupiedException("Booking dates are over laps with already existing booking");
        }
    }



    private BookingDto mapToDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .room(RoomDto.builder().id(booking.getRoom().getId())
                        .name(booking.getRoom().getName())
                        .type(booking.getRoom().getType())
                        .description(booking.getRoom().getDescription())
                        .price(booking.getRoom().getPrice())
                        .beds(booking.getRoom().getBeds())
                        .build())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .archived(booking.getArchived())
                .build();
    }
}
