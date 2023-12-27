package com.mk.roombookingzaver.controller;


import java.time.LocalDate;

import com.mk.roombookingzaver.dto.RoomListResponse;
import com.mk.roombookingzaver.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/availability")
    public ResponseEntity<RoomListResponse> GetAvailableRooms(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int beds) { //TODO leave this one
        return new ResponseEntity<>(new RoomListResponse(roomService.getAvailableBookings(startDate, endDate, beds)), HttpStatus.OK);
    }
}
