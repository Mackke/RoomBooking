package com.mk.roombookingzaver.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.exception.RoomNotFoundException;
import com.mk.roombookingzaver.exception.RoomOccupiedException;
import com.mk.roombookingzaver.dto.BookingDto;
import com.mk.roombookingzaver.dto.RoomDto;
import com.mk.roombookingzaver.entity.Booking;
import com.mk.roombookingzaver.entity.Room;
import com.mk.roombookingzaver.repository.BookingRepository;
import com.mk.roombookingzaver.repository.RoomRepository;
import com.mk.roombookingzaver.request.BookingRequest;
import com.mk.roombookingzaver.response.BookingResponse;
import com.mk.roombookingzaver.response.CurrentBookingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    public CurrentBookingsResponse getCurrentBookings(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.currentBookings(startDate, endDate);

        List<BookingDto> listOfCurrentBookings = bookings.stream().map(booking -> BookingDto.builder()
                .id(booking.getId())
                .room(RoomDto.builder()
                        .id(booking.getRoom().getId())
                        .name(booking.getRoom().getName())
                        .type(booking.getRoom().getType())
                        .description(booking.getRoom().getDescription())
                        .price(booking.getRoom().getPrice())
                        .beds(booking.getRoom().getBeds())
                        .build())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .archived(booking.getArchived())
                .build()).toList();

        return new CurrentBookingsResponse(listOfCurrentBookings);
    }

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Room roomToBeBooked = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Booking not found"));

        List<Booking> bookingsForRoom = bookingRepository.getRoomsBookings(roomToBeBooked.getId());

        validateRoomAvailability(bookingsForRoom, bookingRequest.getStartDate(), bookingRequest.getEndDate());

        Booking booking = bookingRepository.save(new Booking(roomToBeBooked, bookingRequest.getStartDate(), bookingRequest.getEndDate()));

        BookingDto bookingDto = BookingDto.builder()
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

        return new BookingResponse(bookingDto);
    }

    public BookingResponse cancelBookingById(UUID bookingId) {
        Booking persistedBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        persistedBooking.setArchived(LocalDate.now());
        bookingRepository.save(persistedBooking);

        BookingDto canceledBooking = BookingDto.builder()
                .id(persistedBooking.getId())
                .room(RoomDto.builder().id(persistedBooking.getRoom().getId())
                        .name(persistedBooking.getRoom().getName())
                        .type(persistedBooking.getRoom().getType())
                        .description(persistedBooking.getRoom().getDescription())
                        .price(persistedBooking.getRoom().getPrice())
                        .beds(persistedBooking.getRoom().getBeds())
                        .build())
                .startDate(persistedBooking.getStartDate())
                .endDate(persistedBooking.getEndDate())
                .archived(persistedBooking.getArchived())
                .build();

        return new BookingResponse(canceledBooking);
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
}
