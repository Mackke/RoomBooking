package com.mk.roombookingzaver.api.controller;


import java.time.LocalDate;
import java.util.UUID;

import com.mk.roombookingzaver.api.dto.ErrorResponse;
import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.api.dto.BookingRequest;
import com.mk.roombookingzaver.api.dto.BookingResponse;
import com.mk.roombookingzaver.api.dto.BookingListResponse;
import com.mk.roombookingzaver.exception.RoomNotFoundException;
import com.mk.roombookingzaver.exception.RoomOccupiedException;
import com.mk.roombookingzaver.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Used for checking bookingss on CRUD operations
@RestController
@RequestMapping("/app/v1/bookings")
@RequiredArgsConstructor
public class BookingController implements BookingControllerApi {


    private final BookingService bookingService;

    @Override
    public ResponseEntity<BookingListResponse> getCurrentBookings(LocalDate startDate, LocalDate endDate) {
        return new ResponseEntity<>(bookingService.getCurrentBookings(startDate, endDate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookingResponse> createBooking(BookingRequest bookingRequest) {
        return new ResponseEntity<>(bookingService.createBooking(bookingRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BookingResponse> cancelBookings(UUID bookingId) {
        try {
            return new ResponseEntity<>(bookingService.cancelBookingById(bookingId), HttpStatus.OK);
        } catch (BookingNotFoundException exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
