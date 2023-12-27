package com.mk.roombookingzaver.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import com.mk.roombookingzaver.dto.BookingDto;
import com.mk.roombookingzaver.entity.Booking;
import com.mk.roombookingzaver.entity.Room;
import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.exception.RoomNotFoundException;
import com.mk.roombookingzaver.exception.RoomOccupiedException;
import com.mk.roombookingzaver.mapper.BookingMapper;
import com.mk.roombookingzaver.repository.BookingRepository;
import com.mk.roombookingzaver.repository.RoomRepository;
import com.mk.roombookingzaver.request.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final BookingMapper bookingMapper;

    public List<BookingDto> getCurrentBookings(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.currentBookings(startDate, endDate);

        return bookingMapper.mapToBookingDtos(bookings);
    }

    public BookingDto createBooking(BookingRequest bookingRequest) {
        Room roomToBeBooked = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Booking not found"));

        List<Booking> bookingsForRoom = bookingRepository.getRoomsBookings(roomToBeBooked.getId());

        validateRoomAvailability(bookingsForRoom, bookingRequest.getStartDate(), bookingRequest.getEndDate());

        Booking booking = bookingRepository.save(new Booking(roomToBeBooked, bookingRequest.getStartDate(), bookingRequest.getEndDate()));

        return bookingMapper.map(booking);
    }

    public BookingDto cancelBookingById(UUID bookingId) {
        Booking persistedBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        persistedBooking.setArchived(LocalDate.now());
        bookingRepository.save(persistedBooking);

        return bookingMapper.map(persistedBooking);
    }

    public void validateRoomAvailability(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        List<Booking> doubleBookings = bookings.stream()
                .filter(isRoomOccupied(startDate, endDate))
                .toList();

        if (!doubleBookings.isEmpty()) {
            throw new RoomOccupiedException("Booking dates are overlapping with already existing booking");
        }
    }

    private Predicate<Booking> isRoomOccupied(LocalDate startDate, LocalDate endDate) {
        return booking -> (startDate.isEqual(booking.getStartDate()) && endDate.isEqual(booking.getEndDate())) ||
                (startDate.isAfter(booking.getStartDate()) && startDate.isBefore(booking.getEndDate())) ||
                (endDate.isAfter(booking.getStartDate()) && endDate.isBefore(booking.getEndDate()));
    }

}
