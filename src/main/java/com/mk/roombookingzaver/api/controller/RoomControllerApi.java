package com.mk.roombookingzaver.api.controller;

import java.time.LocalDate;

import com.mk.roombookingzaver.api.dto.RoomListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RoomControllerApi {
    @GetMapping("/availability")
    ResponseEntity<RoomListResponse> GetAvailableRooms(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int beds);
}