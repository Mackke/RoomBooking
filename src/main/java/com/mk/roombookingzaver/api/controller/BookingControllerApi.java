package com.mk.roombookingzaver.api.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.mk.roombookingzaver.api.dto.BookingRequest;
import com.mk.roombookingzaver.api.dto.BookingResponse;
import com.mk.roombookingzaver.api.dto.BookingListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface BookingControllerApi {

    @GetMapping
    ResponseEntity<BookingListResponse> getCurrentBookings(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate);

    @PostMapping
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest);


    @PatchMapping("/{bookingId}")
    ResponseEntity<BookingResponse> cancelBookings(@PathVariable UUID bookingId);
}
