package com.mk.roombookingzaver.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.mk.roombookingzaver.request.BookingRequest;
import com.mk.roombookingzaver.response.BookingResponse;
import com.mk.roombookingzaver.response.CurrentBookingsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface BookingControllerApi {

    @GetMapping
    ResponseEntity<CurrentBookingsResponse> getCurrentBookings(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate);

    @PostMapping
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest);


    @PatchMapping("/{bookingId}")
    ResponseEntity<BookingResponse> cancelBookings(@PathVariable UUID bookingId);
}
