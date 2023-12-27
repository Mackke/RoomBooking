package com.mk.roombookingzaver.controller;


import java.time.LocalDate;
import java.util.UUID;

import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.request.BookingRequest;
import com.mk.roombookingzaver.response.BookingResponse;
import com.mk.roombookingzaver.response.BookingListResponse;
import com.mk.roombookingzaver.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(new BookingListResponse(bookingService.getCurrentBookings(startDate, endDate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookingResponse> createBooking(BookingRequest bookingRequest) {
        return new ResponseEntity<>(new BookingResponse(bookingService.createBooking(bookingRequest)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BookingResponse> cancelBookings(UUID bookingId) {
        return new ResponseEntity<>(new BookingResponse(bookingService.cancelBookingById(bookingId)), HttpStatus.OK);
    }
}
