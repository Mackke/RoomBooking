package com.mk.roombookingzaver.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mk.roombookingzaver.dto.BookingDto;
import com.mk.roombookingzaver.entity.Booking;
import com.mk.roombookingzaver.entity.Room;
import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.exception.RoomOccupiedException;
import com.mk.roombookingzaver.mapper.BookingMapper;
import com.mk.roombookingzaver.repository.BookingRepository;
import com.mk.roombookingzaver.repository.RoomRepository;
import com.mk.roombookingzaver.request.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class BookingServiceTest {

    private BookingService bookingService;

    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository, roomRepository, bookingMapper); }

    private static List<BookingRequest> unSuccessfulBookings() {
        return List.of(new BookingRequest(
                        UUID.fromString("4f0a3a35-06a4-4a6f-9207-c8fe00a0e819"),
                        LocalDate.of(2023,12,20),
                        LocalDate.of(2023,12,29)),
                new BookingRequest(
                        UUID.fromString("4f0a3a35-06a4-4a6f-9207-c8fe00a0e819"),
                        LocalDate.of(2023,12,22),
                        LocalDate.of(2023,12,27)),
                new BookingRequest(
                        UUID.fromString("4f0a3a35-06a4-4a6f-9207-c8fe00a0e819"),
                        LocalDate.of(2023,12,15),
                        LocalDate.of(2023,12, 21)),
                new BookingRequest(
                        UUID.fromString("4f0a3a35-06a4-4a6f-9207-c8fe00a0e819"),
                        LocalDate.of(2023,12,25),
                        LocalDate.of(2024,1,4)));
    }

    @ParameterizedTest
    @MethodSource("unSuccessfulBookings")
    public void givenDoubleBooking_whenCreateBooking_returnRoomOccupiedException(BookingRequest bookingRequest) {

        Room room1 = new Room(UUID.randomUUID(), "Deluxe Suite", "Luxurious suite with a view", "Deluxe", 3, 250.0);

        List<Booking> bookings = List.of(
                new Booking(UUID.randomUUID(),
                        room1,
                        LocalDate.of(2023, 12, 20),
                        LocalDate.of(2023, 12, 29)));

        when(roomRepository.findById(any())).thenReturn(Optional.of(room1));
        when(bookingRepository.getRoomsBookings(room1.getId())).thenReturn(bookings);

        assertThrows(RoomOccupiedException.class, () -> bookingService.createBooking(bookingRequest));
    }

    @Test
    public void givenCancelBookingForWrongBookingId_whenCancelBookingById_ReturnBookingNotFoundException() {
        UUID randomBookingId = UUID.randomUUID();

        when(bookingRepository.findById(randomBookingId)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.cancelBookingById(randomBookingId));
    }

    @Test
    public void givenCancelBookingWithCorrectBookingId_whenCancelBookingById_ReturnBooking() {
        UUID bookingId = UUID.randomUUID();

        Room room1 = new Room(UUID.randomUUID(), "Deluxe Suite", "Luxurious suite with a view", "Deluxe", 3, 250.0);

        Booking booking = new Booking(UUID.randomUUID(),
                room1,
                LocalDate.of(2023, 12, 20),
                LocalDate.of(2023, 12, 29));

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        BookingDto bookingDto = bookingService.cancelBookingById(bookingId);

        assertNotNull(bookingDto.getArchived());
    }
}
