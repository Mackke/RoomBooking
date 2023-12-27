package com.mk.roombookingzaver.controller;


import java.time.LocalDate;
import java.util.UUID;

import com.mk.roombookingzaver.request.BookingRequest;
import com.mk.roombookingzaver.response.BookingListResponse;
import com.mk.roombookingzaver.response.BookingResponse;
import com.mk.roombookingzaver.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<BookingListResponse> getCurrentBookings(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return new ResponseEntity<>(new BookingListResponse(bookingService.getCurrentBookings(startDate, endDate)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
        return new ResponseEntity<>(new BookingResponse(bookingService.createBooking(bookingRequest)), HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> cancelBookings(@PathVariable UUID bookingId) {
        return new ResponseEntity<>(new BookingResponse(bookingService.cancelBookingById(bookingId)), HttpStatus.OK);
    }
}
